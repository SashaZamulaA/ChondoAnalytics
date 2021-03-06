package com.zaleksandr.aleksandr.myapplication

import android.app.ProgressDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import android.widget.Toast

abstract class BaseActivity : AppCompatActivity(), IView{

    internal lateinit var toolbar: Toolbar
    lateinit var drawer: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle

    override fun setContentView(layoutResID: Int) {


//        toolbar = findViewById<View>(R.currentUserId.toolbar) as Toolbar
//
//        drawer = findViewById<View>(R.currentUserId.drawer_layout) as DrawerLayout
//        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close)
//        drawer.addDrawerListener(toggle)
//        toggle.drawerArrowDrawable.color = resources.getColor(R.color.white)
//        toggle.syncState()
//
//        navigationView = findViewById<View>(R.currentUserId.nav_view) as NavigationView
//        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
         if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
//    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
//        val currentUserId = menuItem.itemId
//        when (currentUserId) {
//            R.currentUserId.nav_main -> startActivity(Intent(this, MainActivity::class.java))
//
////            R.currentUserId.chondo_result -> {
////                startActivity(Intent(this, ResultM3AView::class.java))
////                Toast.makeText(applicationContext, "You Clicked Options A", Toast.LENGTH_SHORT).show()
////                drawer.closeDrawer(GravityCompat.START)
////            }
//
//
//            R.currentUserId.nav_settings -> {
//                startActivity(Intent(this, SettingsView::class.java))
//                drawer.closeDrawer(GravityCompat.START)
//            }
//
//            R.currentUserId.nav_share -> {
//                val sharingIntent = Intent(Intent.ACTION_SEND)
//                val shareBody = getString(R.string.share_body)
//                sharingIntent.apply {
//                    type = "text/plain"
//                    putExtra(android.content.Intent.EXTRA_SUBJECT, "True Father Prayers")
//                    putExtra(android.content.Intent.EXTRA_TEXT, shareBody)
//                }
//                startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_by)))
//            }
//        }
//
//        drawer = findViewById<View>(R.currentUserId.drawer_layout) as DrawerLayout
//        drawer.closeDrawer(GravityCompat.START)
//        return true
//    }

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
//    override fun showLoading() {
//        showProgress(R.string.loading, null)
//    }
//
//    override fun hideLoading() {
//        dismissProgress()
//    }
//
//    override fun loadError(e: Throwable) {
//        showHttpError(e)
//    }
//
//    override fun loadError(msg: String) {
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
//    }

//    private fun showHttpError(e: Throwable) {
//        loadError(e.localizedMessage)
//    }
}