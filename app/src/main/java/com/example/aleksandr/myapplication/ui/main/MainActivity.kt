package com.example.aleksandr.myapplication.ui.main

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.AlertDialog
import com.example.aleksandr.myapplication.BaseActivity
import com.example.aleksandr.myapplication.R


class MainActivity : BaseActivity() {
    private lateinit var presenter: MainPresenter

    override fun init(savedInstanceState: Bundle?) {
        super.setContentView(R.layout.activity_main)
        presenter = MainPresenter(this, application)
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
                     .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                         val intent = Intent(this, LoginActivity::class.java)
                         startActivity(intent)
                         finish()

                     })
                     .setNegativeButton("No", null)
                     .show()
         }
     }
}