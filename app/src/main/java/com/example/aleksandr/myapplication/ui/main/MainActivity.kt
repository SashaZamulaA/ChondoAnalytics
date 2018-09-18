package com.example.aleksandr.myapplication.ui.main

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.AlertDialog
import com.example.aleksandr.myapplication.BaseActivity
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.R.string.email
import com.example.aleksandr.myapplication.ui.login.LoginActivity
import com.example.aleksandr.myapplication.ui.login.model.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_is_word_list.*


class MainActivity : BaseActivity() {
    private lateinit var presenter: MainPresenter
    lateinit var databaseReference: DatabaseReference


    override fun init(savedInstanceState: Bundle?) {
        super.setContentView(R.layout.activity_main)
        presenter = MainPresenter(this, application)
        databaseReference = FirebaseDatabase.getInstance().getReference("Word")
    }
    override fun onStart() {
        super.onStart()
        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val value = dataSnapshot.value.toString()
                text_on_screen.text = "Name: $value"

            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter.bindView(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.unbindView(this)
    }

     override fun onBackPressed() {
         if (drawer.isDrawerOpen(GravityCompat.START)) {
             drawer.closeDrawer(GravityCompat.START)
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
}