package com.example.aleksandr.myapplication.ui.commonResult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aleksandr.myapplication.model.City
import com.example.aleksandr.myapplication.ui.commonResult.adapter.CommonResultAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_common_result.*
import kotlinx.android.synthetic.main.fragment_common_result.view.*
import java.util.*
import kotlin.collections.ArrayList
import android.R
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.navigation.ActivityNavigator
import androidx.navigation.findNavController
import com.example.aleksandr.myapplication.ui.commonResult.adapter.CommonResultAdapter.FragmentCommunication
import com.example.aleksandr.myapplication.ui.eachCentersResult.EachCenterFragment
import com.example.aleksandr.myapplication.ui.individualResult.IndividualResultFragment
import kotlinx.android.synthetic.main.spinner_drop_down.*
import java.io.Serializable
import androidx.navigation.fragment.FragmentNavigator
import kotlinx.android.synthetic.main.spinner_simple_item.*
import org.jetbrains.anko.bundleOf


class CommonResultFragment : Fragment(), CommonResultAdapter.FragmentCommunication, Serializable {

    override fun respond(position: Int) {
//        items[position]
        Toast.makeText(context,
                "Verification email sent to $position",
                Toast.LENGTH_SHORT).show()
        val bundle = Bundle()
        bundle.putInt("pas", position)
        Navigation.findNavController(this.view!!).navigate(com.example.aleksandr.myapplication.R.id.action_commonResultFragment_to_eachCenterFragment, bundle)



    }

    var toolbar: Toolbar? = null
    var adapter: CommonResultAdapter? = null
    var city: City? = null
    private val items: ArrayList<City> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.example.aleksandr.myapplication.R.layout.fragment_common_result, container, false)



        rootView.button_individual_result.setOnClickListener {
            Navigation.findNavController(it).navigate(com.example.aleksandr.myapplication.R.id.action_commonResultFragment_to_individualResultFragment2)
        }

        adapterInit(rootView)
        bottomMenuInit(rootView)

        toolbar = view?.findViewById(com.example.aleksandr.myapplication.R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        adapter?.perioSelected(CommonResultAdapter.ClickByFilter.YEAR)


//        update(items)
        return rootView
    }


    var communication: FragmentCommunication = object : FragmentCommunication {
        override fun respond(position: Int) {
            val fragmentB = IndividualResultFragment()
            val manager = fragmentManager
            val transaction = manager!!.beginTransaction()
            transaction.replace(R.id.list_container, fragmentB).commit()
        }

    }


    private fun showLoading() {
        pb_loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        pb_loading.visibility = View.GONE
    }

//    fun update(items: ArrayList<City>) {
//        adapter?.updateList(items)
//
//    }


    private fun bottomMenuInit(rootView: View) {
        val layoutParams = rootView.bottom_navigation.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.behavior = BottomNavigationViewBehavior()
        rootView.bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                com.example.aleksandr.myapplication.R.id.menu_year -> {
//                    showLoading()
                    adapter?.perioSelected(CommonResultAdapter.ClickByFilter.YEAR)
//                    hideLoading()
                }
                com.example.aleksandr.myapplication.R.id.menu_month -> {
                    adapter?.perioSelected(CommonResultAdapter.ClickByFilter.MONTH)
                }
                com.example.aleksandr.myapplication.R.id.menu_week -> {
                    adapter?.perioSelected(CommonResultAdapter.ClickByFilter.WEEK)
                }
                com.example.aleksandr.myapplication.R.id.menu_day -> {
                    adapter?.perioSelected(CommonResultAdapter.ClickByFilter.DAY)
                }
                else -> {
                }
            }
            true
        }
    }

    private fun adapterInit(rootView: View) {
        rootView.list_common_res_adapter.setHasFixedSize(true)
        rootView.list_common_res_adapter.layoutManager = LinearLayoutManager(context)

        items.add(City("","0", "0", "0", "0", "Kyiv", "0", "0", "0", "0", Date()))
        items.add(City("","0", "0", "0", "0", "Kharkiv", "0", "0", "0", "0", Date()))
        items.add(City("","0", "0", "0", "0", "Dnepr", "0", "0", "0", "0", Date()))
        items.add(City("","0", "0", "0", "0", "Zhytomyr", "0", "0", "0", "0", Date()))
        items.add(City("","0", "0", "0", "0", "Lviv", "0", "0", "0", "0", Date()))
        items.add(City("","0", "0", "0", "0", "Odessa", "0", "0", "0", "0", Date()))
        items.add(City("","0", "0", "0", "0", "Chernigov", "0", "0", "0", "0", Date()))

        adapter = CommonResultAdapter(items, this)
        rootView.list_common_res_adapter.adapter = adapter
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