package com.zaleksandr.aleksandr.myapplication

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import com.google.android.material.navigation.NavigationView
import com.zaleksandr.aleksandr.myapplication.ui.settings.model.User
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil
import com.zaleksandr.aleksandr.myapplication.util.StorageUtil
import com.zaleksandr.aleksandr.tmbook.glade.GlideApp
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header_layout.view.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    companion object {
        val AUTHOR_KEY = "name"
        val QUOTE_KEY = "e_mail"
        val SPINNER = "spinner"
        val PASSWORD = "0000"
        val PASSWORDACCESS = "m3a"
        val PASSWORD_ALL_CENTERS = "1111"
        val INTRO = false
    }


    private var result: String? = null

    val context: Context? = null
    lateinit var drawerLayout: DrawerLayout
    lateinit var navController: NavController
    private var pictureJustChange = false
    private lateinit var presenter: MainPresenter
    private lateinit var navigationView: NavigationView

    private lateinit var toggle: ActionBarDrawerToggle
    internal lateinit var toolbar: Toolbar
    private val dialogDisposable = DialogCompositeDisposable()
    private var menuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this, application)

        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        drawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.drawerArrowDrawable.color = resources.getColor(R.color.white)
        toggle.syncState()



        drawerLayout = findViewById(R.id.drawer_layout)

        navigationView = findViewById(R.id.navigationView)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

//        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
//
//        NavigationUI.setupWithNavController(navigationView, navController)

        navigationView.setNavigationItemSelectedListener(this)

        drawer_layout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerOpened(drawerView: View) {
                hideKeyboard()
            }

            fun passwordToAccess() {}

            override fun onDrawerClosed(drawerView: View) {}
            override fun onDrawerStateChanged(newState: Int) {
                if (newState == DrawerLayout.STATE_IDLE) {
                    menuItem?.let {
                        drawerLayout.post {

                            it.isChecked = true
                            drawerLayout.closeDrawers()
                            val id = it.itemId
                            when (id) {
                                R.id.commonResult -> {
                                    navController.navigate(R.id.commonResultFragment)
                                    (it.title)
                                }
                                R.id.add_result -> navController.navigate(R.id.addResultFragment)
                                R.id.best_result -> navController.navigate(R.id.addBestResultsFragment)
                                R.id.chart -> navController.navigate(R.id.addChart)
                                R.id.add_guest -> navController.navigate(R.id.addMyGuestFragment)
                                R.id.my_guests -> navController.navigate(R.id.showMyGuestFragment)
                                R.id.individualResult -> navController.navigate(R.id.addIndividualFragment)
                                R.id.add_individual_goals -> navController.navigate(R.id.addIndividualGoalsFragment)
                                R.id.nav_settings -> navController.navigate(R.id.settingsFragment)
                                R.id.add_goal -> {
                                    val b = AlertDialog.Builder(this@MainActivity)
                                    b.setTitle("Please enter a password")
                                    val input = EditText(this@MainActivity)
                                    b.setView(input)
                                    b.setPositiveButton("OK") { _, _ ->
                                        result = input.text.toString()
                                        if (result == PASSWORD) {
                                            navController.navigate(R.id.goalFragment)
                                        } else {
                                        }
                                    }
                                    b.setNegativeButton("CANCEL", null)
                                    b.show()
                                }

                                R.id.add_each_centers_goal -> {
                                    val b = AlertDialog.Builder(this@MainActivity)
                                    b.setTitle("Please enter a password")
                                    val input = EditText(this@MainActivity)
                                    b.setView(input)
                                    b.setPositiveButton("OK") { _, _ ->
                                        result = input.text.toString()
                                        if (result == PASSWORD_ALL_CENTERS) {
                                            navController.navigate(R.id.addGoalEachCenterFragment)
                                        } else {
                                        }
                                    }
                                    b.setNegativeButton("CANCEL", null)
                                    b.show()
                                }
                                else -> {
                                }
                            }
                        }
                        menuItem = null
                    }
                }
            }
        })


        navigationView.getHeaderView(0).apply {
            FirestoreUtil.currentUserDocRef.addSnapshotListener { documentSnapshot, _ ->
                FirestoreUtil.getCurrentUser { user ->
                    if (documentSnapshot?.exists()!!) {

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


//    private fun setNavigationViewListner(menuItem: MenuItem): Boolean {
//          navigationView.setNavigationItemSelectedListener(this)
//        menuItem.isChecked = true
//        drawerLayout.closeDrawers()
//        val currentUserId = menuItem.itemId
//        when (currentUserId) {
//            R.currentUserId.commonResult -> {
//                navController.navigate(R.currentUserId.commonResultFragment)
//                (menuItem.title)
//            }
//            R.currentUserId.add_result -> navController.navigate(R.currentUserId.addResultFragment)
//            R.currentUserId.individualResult -> navController.navigate(R.currentUserId.addIndividualFragment)
//            R.currentUserId.add_individual_goals -> navController.navigate(R.currentUserId.addIndividualGoalsFragment)
//            R.currentUserId.nav_settings -> navController.navigate(R.currentUserId.settingsFragment)
//            R.currentUserId.add_goal -> {
//                val b = AlertDialog.Builder(this)
//                b.setTitle("Please enter a password")
//                val input = EditText(this)
//                b.setView(input)
//                b.setPositiveButton("OK") { _, _ ->
//                    result = input.text.toString()
//                    if (result == PASSWORD) {
//                        navController.navigate(R.currentUserId.goalFragment)
//                    } else {
//                    }
//                }
//                b.setNegativeButton("CANCEL", null)
//                b.show()
//            }
//
//            R.currentUserId.add_each_centers_goal -> {
//                val b = AlertDialog.Builder(this)
//                b.setTitle("Please enter a password")
//                val input = EditText(this)
//                b.setView(input)
//                b.setPositiveButton("OK") { _, _ ->
//                    result = input.text.toString()
//                    if (result == PASSWORD_ALL_CENTERS) {
//                        navController.navigate(R.currentUserId.addGoalEachCenterFragment)
//                    } else {
//                    }
//                }
//                b.setNegativeButton("CANCEL", null)
//                b.show()
//            }
//        }
//        return true
//    }


    internal interface CustomDrawerListener : DrawerLayout.DrawerListener {
        override fun onDrawerSlide(drawerView: View, slideOffset: Float)

        override fun onDrawerOpened(drawerView: View) {}

        override fun onDrawerClosed(drawerView: View) {

        }

        override fun onDrawerStateChanged(newState: Int) {

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

        this.menuItem = menuItem
        drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }
}
