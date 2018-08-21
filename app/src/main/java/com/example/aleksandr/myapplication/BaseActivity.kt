package com.example.aleksandr.myapplication

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.example.aleksandr.myapplication.ui.hdh.HDHView
import com.example.aleksandr.myapplication.ui.info.InfoView
import com.example.aleksandr.myapplication.ui.main.MainActivity

abstract class BaseActivity : AppCompatActivity(), IView, NavigationView.OnNavigationItemSelectedListener {

    internal lateinit var toolbar: Toolbar
    lateinit var drawer: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle

    override fun setContentView(layoutResID: Int) {
        val fullView = layoutInflater.inflate(R.layout.activity_nav_drawer, null) as DrawerLayout
        val activityContainer = fullView.findViewById<View>(R.id.content_base) as FrameLayout

        layoutInflater.inflate(layoutResID, activityContainer, true)
        super.setContentView(fullView)

        toolbar = findViewById<View>(R.id.toolbar) as Toolbar

        drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white))
        toggle.syncState()

        navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
         if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val id = menuItem.itemId
        when (id) {
            R.id.nav_main -> startActivity(Intent(this, MainActivity::class.java))

//            R.id.nav_hdh -> {
//                startActivity(Intent(this, HDHView::class.java))
//                Toast.makeText(applicationContext, "You Clicked Options A", Toast.LENGTH_SHORT).show()
//                drawer.closeDrawer(GravityCompat.START)
//            }
            R.id.nav_settings -> {
                Toast.makeText(applicationContext, "You Clicked Options B", Toast.LENGTH_SHORT).show()
                drawer.closeDrawer(GravityCompat.START)
            }
            R.id.nav_share -> {
                val sharingIntent = Intent(Intent.ACTION_SEND)
                val shareBody = getString(R.string.share_body)
                sharingIntent.apply {
                    type = "text/plain"
                    putExtra(android.content.Intent.EXTRA_SUBJECT, "True Father Prayers")
                    putExtra(android.content.Intent.EXTRA_TEXT, shareBody)
                }
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_by)))
            }

                R.id.nav_info -> startActivity(Intent(this, InfoView::class.java))

                R.id.nav_hdh -> startActivity(Intent(this, HDHView::class.java))

        }

        drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    fun setNavigationItemClicked(position: Int) {
        navigationView.menu.getItem(position).isChecked = true
    }

    private var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initProgressDialog()
        init(savedInstanceState)
    }

        fun initProgressDialog() {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog(this)
                mProgressDialog!!.isIndeterminate = true
                mProgressDialog!!.setCancelable(false)
            }
        }

        override fun onPostCreate(savedInstanceState: Bundle?) {
            super.onPostCreate(savedInstanceState)
        }

        override fun onResume() {
            super.onResume()
        }

        override fun onDestroy() {
            System.gc()
            System.runFinalization()
            dismissProgress()
            mProgressDialog = null
            super.onDestroy()
        }
    abstract fun init(savedInstanceState: Bundle?)

    fun showProgress(msgResId: Int, keyListener: DialogInterface.OnKeyListener?) {
        if (isFinishing) return

        if (mProgressDialog!!.isShowing) return

        if (msgResId != 0) {
            mProgressDialog?.setMessage(resources.getString(msgResId))
        }

        if (keyListener != null) {
            mProgressDialog?.setOnKeyListener(keyListener)
        } else {
            mProgressDialog?.setCancelable(false)
        }
        mProgressDialog?.show()
    }

    /**
     * @param isCancel
     */
    fun setCancelableProgress(isCancel: Boolean) {
        if (mProgressDialog != null) {
            mProgressDialog?.setCancelable(true)
        }
    }
    /**
     * cancel progress dialog.
     */
    fun dismissProgress() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog?.dismiss()
        }
    }
    override fun showLoading() {
        showProgress(R.string.loading, null)
    }

    override fun hideLoading() {
        dismissProgress()
    }

    override fun loadError(e: Throwable) {
        showHttpError(e)
    }

    override fun loadError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun showHttpError(e: Throwable) {
        loadError(e.localizedMessage)
    }
}