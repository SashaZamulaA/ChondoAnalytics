package com.example.aleksandr.myapplication.ui.main


import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.model.User
import com.example.aleksandr.myapplication.ui.login.LoginActivity
import com.example.aleksandr.myapplication.ui.settings.SettingsView.Companion.AUTHOR_KEY
import com.example.aleksandr.myapplication.ui.settings.SettingsView.Companion.QUOTE_KEY
import com.example.aleksandr.myapplication.util.FirestoreUtil
import com.example.aleksandr.myapplication.util.StorageUtil
import com.example.aleksandr.tmbook.glade.GlideApp
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header_layout.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navController: NavController
    lateinit var navigationView: NavigationView
    private var pictureJustChange = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val finalHost = NavHostFragment.create(R.navigation.nav_graph)
//        supportFragmentManager.beginTransaction()
//                .replace(R.id.nav_host_fragment, finalHost)
//                .setPrimaryNavigationFragment(finalHost)
//                .commit()


        setSupportActionBar(toolbar2)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

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
                        header_name.setText(quoteText)
                        header_email.text = authorText

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
        setupActionBarWithNavController(this, navController, drawerLayout)

        // Handle nav drawer item clicks
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout.closeDrawers()
            true
        }

        // Tie nav graph to items in nav drawer
        setupWithNavController(navigationView, navController)
    }

    // Setting Up One Time Navigation
    private fun setupNavigation() {

        toolbar = findViewById(R.id.toolbar2)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        drawerLayout = findViewById(R.id.drawer_layout)

        navigationView = findViewById(R.id.navigationView)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        NavigationUI.setupWithNavController(navigationView, navController)

        navigationView.setNavigationItemSelectedListener(this)

    }

    override fun onSupportNavigateUp() = findNavController(this, R.id.nav_host_fragment).navigateUp()

//    override fun onSupportNavigateUp(): Boolean {
//        return NavigationUI.navigateUp(drawerLayout, Navigation.findNavController(this, R.id.nav_host_fragment))
//    }

    override fun onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Closing Activity")
                    .setMessage("Are you sure you want to close this activity?")
                    .setPositiveButton("Yes") { _, _ ->
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .setNegativeButton("No", null)
                    .show()
        }
    }

//    override fun onBackPressed() {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START)
//        } else {
//            super.onBackPressed()
//        }
//    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        menuItem.isChecked = true
        drawerLayout.closeDrawers()
        val id = menuItem.itemId
        when (id) {
            R.id.first -> navController.navigate(R.id.defaultFragment)
            R.id.chondo_result -> navController.navigate(R.id.chondoFragment)
            R.id.nav_settings -> navController.navigate(R.id.settingsFragment)
        }
        return true

    }
}
