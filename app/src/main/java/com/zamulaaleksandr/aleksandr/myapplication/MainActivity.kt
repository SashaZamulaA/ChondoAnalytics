package com.zamulaaleksandr.aleksandr.myapplication


import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView
import com.zamulaaleksandr.aleksandr.myapplication.ui.settings.model.User
import com.zamulaaleksandr.aleksandr.myapplication.util.FirestoreUtil
import com.zamulaaleksandr.aleksandr.myapplication.util.StorageUtil
import com.zamulaaleksandr.aleksandr.tmbook.glade.GlideApp
import kotlinx.android.synthetic.main.header_layout.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        val AUTHOR_KEY = "name"
        val QUOTE_KEY = "e_mail"
        val SPINNER = "spinner"
    }


    val context: Context? = null
    lateinit var drawerLayout: DrawerLayout
    lateinit var navController: NavController
    private var pictureJustChange = false

    private lateinit var navigationView: NavigationView

    private lateinit var toggle: ActionBarDrawerToggle
    internal lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val finalHost = NavHostFragment.create(R.navigation.nav_graph)


        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        drawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.drawerArrowDrawable.color = resources.getColor(R.color.white)
        toggle.syncState()

//        toggle = object : ActionBarDrawerToggle(
//                this,
//                drawerLayout,
//                R.string.navigation_drawer_open,
//                R.string.navigation_drawer_close
//        ) {
//        }
//        drawerLayout.setDrawerListener(toggle)
//        toggle?.syncState()
//
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeButtonEnabled(true)

        drawerLayout = findViewById(R.id.drawer_layout)

        navigationView = findViewById(R.id.navigationView)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

//        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
//
//        NavigationUI.setupWithNavController(navigationView, navController)

        navigationView.setNavigationItemSelectedListener(this)

        navigationView.getHeaderView(0).apply {
            FirestoreUtil.currentUserDocRef.addSnapshotListener { documentSnapshot, _ ->
                FirestoreUtil.getCurrentUser { user ->
                    if (documentSnapshot?.exists()!!) {
                        val myQuote = documentSnapshot.toObject(User::class.java)

                        val quoteText = documentSnapshot.getString(QUOTE_KEY)
                        val authorText = documentSnapshot.getString(AUTHOR_KEY)

                        if (!authorText.isNullOrBlank())
                            header_name.text = authorText
                        if (!quoteText.isNullOrBlank())
                            header_email.text = quoteText

                        if (!pictureJustChange && user.profilePicturePath != null)
                            GlideApp.with(this)
                                    .load(StorageUtil.pathToReference(user.profilePicturePath))
                                    .placeholder(R.drawable.ic_account_circle_black_24dp)
                                    .circleCrop()
                                    .into(header_profile_picture)
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp() = findNavController(this, R.id.nav_host_fragment).navigateUp()

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        menuItem.isChecked = true
        drawerLayout.closeDrawers()
        val id = menuItem.itemId
        when (id) {
            R.id.commonResult -> {
                navController.navigate(R.id.commonResultFragment)
                (menuItem.title)
            }
            R.id.add_result -> navController.navigate(R.id.addResultFragment)
            R.id.individualResult -> navController.navigate(R.id.addIndividualFragment)
            R.id.nav_settings -> navController.navigate(R.id.settingsFragment)
            R.id.add_goal -> navController.navigate(R.id.goalFragment)
        }
        return true
    }
}
