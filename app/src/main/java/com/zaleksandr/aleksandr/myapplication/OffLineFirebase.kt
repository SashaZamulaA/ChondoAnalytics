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
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.FirebaseDatabase
import com.zaleksandr.aleksandr.myapplication.MainActivity.Companion.AUTHOR_KEY
import com.zaleksandr.aleksandr.myapplication.MainActivity.Companion.PASSWORD_ALL_CENTERS
import com.zaleksandr.aleksandr.myapplication.MainActivity.Companion.QUOTE_KEY
import com.zaleksandr.aleksandr.myapplication.ui.settings.model.User
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil
import com.zaleksandr.aleksandr.myapplication.util.StorageUtil
import com.zaleksandr.aleksandr.tmbook.glade.GlideApp
import kotlinx.android.synthetic.main.header_layout.*
import kotlinx.android.synthetic.main.header_layout.view.*


class OffLineFirebase : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

    }


}
