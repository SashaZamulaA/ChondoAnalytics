package com.example.aleksandr.myapplication.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.model.City
import com.example.aleksandr.myapplication.ui.main.adapter.MainAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.default_fragment.*
import kotlinx.android.synthetic.main.default_fragment.view.*
import java.util.*

class DefaultFragment : Fragment() {

    var adapter: MainAdapter? = null

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.example.aleksandr.myapplication.R.layout.default_fragment, container, false)
        adapterInit(rootView)
        bottomMenuInit(rootView)
        return rootView


    }

    private fun bottomMenuInit(rootView: View) {
        val layoutParams = rootView.bottom_navigation.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.behavior = BottomNavigationViewBehavior()
        rootView.bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_year -> {
                    adapter?.perioSelected(MainAdapter.ClickByFilter.YEAR)

                }
                R.id.menu_month -> {
                    adapter?.perioSelected(MainAdapter.ClickByFilter.MONTH)
                }
                R.id.menu_week -> {
                    adapter?.perioSelected(MainAdapter.ClickByFilter.WEEK)
                }
                R.id.menu_day -> {
                    adapter?.perioSelected(MainAdapter.ClickByFilter.DAY)

                }
                else -> {
                }
            }
            true
        }
    }

    private fun adapterInit(rootView: View) {
        rootView.list_main_adapter.setHasFixedSize(true)
        rootView.list_main_adapter.layoutManager = LinearLayoutManager(context)
        val items: ArrayList<City> = ArrayList()


        items.add(City("", "", "", "", "KYIV", "", "", "", "", Date()))
        items.add(City("", "", "", "", "KHARKIV", "", "", "", "", Date()))
        items.add(City("", "", "", "", "DNEPR", "", "", "", "", Date()))
        items.add(City("", "", "", "", "ZHYTOMYR", "", "", "", "", Date()))
        items.add(City("", "", "", "", "LVIV", "", "", "", "", Date()))
        items.add(City("", "", "", "", "ODESSA", "", "", "", "", Date()))
        items.add(City("", "", "", "", "CHERNIGOV", "", "", "", "", Date()))

        adapter = MainAdapter(items)
        rootView.list_main_adapter.adapter = adapter
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
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
            child.animate().translationY(0f).duration = 200
        }

        private fun slideDown(child: BottomNavigationView) {
            child.clearAnimation()
            child.animate().translationY(height.toFloat()).duration = 200
        }
    }

}
