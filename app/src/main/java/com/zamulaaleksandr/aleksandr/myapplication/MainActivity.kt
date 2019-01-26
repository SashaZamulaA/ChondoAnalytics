package com.zamulaaleksandr.aleksandr.myapplication


import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.zamulaaleksandr.aleksandr.myapplication.ui.commonResult.CommonResultFragment
import com.zamulaaleksandr.aleksandr.myapplication.ui.settings.model.User
import com.zamulaaleksandr.aleksandr.myapplication.util.FirestoreUtil
import com.zamulaaleksandr.aleksandr.myapplication.util.StorageUtil
import com.zamulaaleksandr.aleksandr.tmbook.glade.GlideApp
import com.google.android.material.navigation.NavigationView
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
    lateinit var navigationView: NavigationView
    private var pictureJustChange = false
    val commonFragment: CommonResultFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val finalHost = NavHostFragment.create(R.navigation.nav_graph)
//        supportFragmentManager.beginTransaction()
//                .replace(R.id.nav_host_fragment, finalHost)
//                .setPrimaryNavigationFragment(finalHost)
//                .commit()

//        toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)

//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar!!.setDisplayShowHomeEnabled(true)
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
                        header_name.text = authorText
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

//        setupNavigation()

    }

    private fun navigation() {
        val navController = findNavController(this, R.id.nav_host_fragment)

        // Update action bar to reflect navigation

        // Handle nav drawer item clicks
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout.closeDrawers()
            true
        }

        // Tie nav graph to items in nav drawer

    }

    // Setting Up One Time Navigation
    private fun setupNavigation() {


        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        drawerLayout = findViewById(R.id.drawer_layout)

        navigationView = findViewById(R.id.navigationView)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        navigationView.setNavigationItemSelectedListener(this)

    }

    override fun onSupportNavigateUp() = findNavController(this, R.id.nav_host_fragment).navigateUp()

//    override fun onSupportNavigateUp(): Boolean {
//        return NavigationUI.navigateUp(drawerLayout, Navigation.findNavController(this, R.id.nav_host_fragment))
//    }

//    override fun onBackPressed() {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START)
//        } else {
//            AlertDialog.Builder(this)
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .setTitle("Closing Activity")
//                    .setMessage("Are you sure you want to close this activity?")
//                    .setPositiveButton("Yes") { _, _ ->
//                        val intent = Intent(this, LoginActivity::class.java)
//                        startActivity(intent)
//                        finish()
//                    }
//                    .setNegativeButton("No", null)
//                    .show()
//        }
//    }

//    override fun onBackPressed() {
//
//        val count = fragmentManager.backStackEntryCount
//
//        if (count == 0) {
//            super.onBackPressed()
//            //additional code
//        } else {
//            fragmentManager.popBackStack()
//        }
//    }

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
            R.id.commonResult -> navController.navigate(R.id.commonResultFragment)
            R.id.add_result -> navController.navigate(R.id.addResultFragment)
            R.id.nav_settings -> navController.navigate(R.id.settingsFragment)
        }
        return true
    }
}
