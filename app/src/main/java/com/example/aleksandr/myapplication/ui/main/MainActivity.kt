package com.example.aleksandr.myapplication.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.aleksandr.myapplication.BaseActivity
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.model.City
import com.example.aleksandr.myapplication.ui.login.LoginActivity
import com.example.aleksandr.myapplication.ui.main.adapter.MainAdapter
import com.google.firebase.firestore.FieldValue
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MainActivity : BaseActivity() {

    private lateinit var presenter: MainPresenter
    private lateinit var adapter: MainAdapter

    override fun init(savedInstanceState: Bundle?) {
        super.setContentView(com.example.aleksandr.myapplication.R.layout.activity_main)
        presenter = MainPresenter(this, application)

        list_main_adapter.setHasFixedSize(true)
        list_main_adapter.layoutManager = LinearLayoutManager(this)
        val items: ArrayList<City> = ArrayList()

        items.add(City("", "", "", "", "KYIV", "", "", "", "", Date()))
        items.add(City("", "", "", "", "KHARKIV", "", "", "", "", Date()))
        items.add(City("", "", "", "", "DNEPR", "", "", "", "", Date()))
        items.add(City("", "", "", "", "ZHYTOMYR", "", "", "", "", Date()))
        items.add(City("", "", "", "", "LVIV", "", "", "", "", Date()))
        items.add(City("", "", "", "", "ODESSA", "", "", "", "", Date()))
        items.add(City("", "", "", "", "CHERNIGOV", "", "", "", "", Date()))

        adapter = MainAdapter(items)
        list_main_adapter.adapter = adapter

        val doc = HashMap<String, Any>()
        doc["timestamp"] = FieldValue.serverTimestamp()

        bottomMenu()
    }

    private fun bottomMenu() {
        val layoutParams = bottom_navigation.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.behavior = BottomNavigationViewBehavior()
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_year -> {
                    adapter.perioSelected(MainAdapter.ClickByFilter.YEAR)

                }
                R.id.menu_month -> {
                    adapter.perioSelected(MainAdapter.ClickByFilter.MONTH)
                }
                R.id.menu_week -> {
                    adapter.perioSelected(MainAdapter.ClickByFilter.WEEK)
                }
                R.id.menu_day -> {
                    adapter.perioSelected(MainAdapter.ClickByFilter.DAY)

                }
                else -> {
                }
            }
            true
        }
    }

    inner class BottomNavigationViewBehavior : CoordinatorLayout.Behavior<BottomNavigationView>() {

        private var height: Int = 0

        override fun onLayoutChild(parent: CoordinatorLayout, child: BottomNavigationView, layoutDirection: Int): Boolean {
            height = child.height
            return super.onLayoutChild(parent, child, layoutDirection)
        }

        override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout,
                                         child: BottomNavigationView, directTargetChild: View, target: View,
                                         axes: Int, type: Int): Boolean {
            return axes == ViewCompat.SCROLL_AXIS_VERTICAL
        }

        override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: BottomNavigationView,
                                    target: View, dxConsumed: Int, dyConsumed: Int,
                                    dxUnconsumed: Int, dyUnconsumed: Int,
                                    @ViewCompat.NestedScrollType type: Int) {
            if (dyConsumed > 0) {
                slideDown(child)
            } else if (dyConsumed < 0) {
                slideUp(child)
            }
        }

        private fun slideUp(child: BottomNavigationView) {
            child.clearAnimation()
            child.animate().translationY(0f).duration = 4000
        }

        private fun slideDown(child: BottomNavigationView) {
            child.clearAnimation()
            child.animate().translationY(height.toFloat()).duration = 4000
        }
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