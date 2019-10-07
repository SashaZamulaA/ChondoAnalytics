package com.zaleksandr.aleksandr.myapplication.ui.commonResult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaleksandr.aleksandr.myapplication.BottomNavigationViewBehavior
import com.zaleksandr.aleksandr.myapplication.model.City
import com.zaleksandr.aleksandr.myapplication.ui.commonResult.adapter.CommonResultAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_common_result.view.*
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList


class CommonResultFragment : Fragment(), CommonResultAdapter.FragmentCommunication, Serializable {

    override fun respond(position: Int) {
//        items[position]

        val bundle = Bundle()
        bundle.putInt("pas", position)

        Navigation.findNavController(this.view!!).navigate(com.zaleksandr.aleksandr.myapplication.R.id.action_commonResultFragment_to_eachCenterFragment, bundle)
        items.clear()
    }

    //    var toolbar: Toolbar? = null
    var adapter: CommonResultAdapter? = null
    var city: City? = null
    private val items: ArrayList<City> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.zaleksandr.aleksandr.myapplication.R.layout.fragment_common_result, container, false)
        items.clear()

        adapterInit(rootView)
        bottomMenuInit(rootView)


        adapter?.perioSelected(CommonResultAdapter.ClickByFilter.YEAR)

        return rootView
    }



    override fun onResume() {
        super.onResume()
        (this.activity!!.toolbar as Toolbar).visibility = View.VISIBLE
        (this.activity!!.toolbar as Toolbar).title = "Common result"
    }


    private fun bottomMenuInit(rootView: View) {
        val layoutParams = rootView.bottom_navigation.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.behavior = BottomNavigationViewBehavior()

        rootView.bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                com.zaleksandr.aleksandr.myapplication.R.id.menu_year -> {
//                    showLoading()
                    adapter?.perioSelected(CommonResultAdapter.ClickByFilter.YEAR)
//                    hideLoading()
                }
                com.zaleksandr.aleksandr.myapplication.R.id.menu_month -> {
                    adapter?.perioSelected(CommonResultAdapter.ClickByFilter.MONTH)
                }
                com.zaleksandr.aleksandr.myapplication.R.id.menu_week -> {
                    adapter?.perioSelected(CommonResultAdapter.ClickByFilter.WEEK)
                }
                com.zaleksandr.aleksandr.myapplication.R.id.menu_day -> {
                    adapter?.perioSelected(CommonResultAdapter.ClickByFilter.DAY)
                }
//                com.zaleksandr.aleksandr.myapplication.R.currentUserId.menu_ind_res -> {
//                  Navigation.findNavController(this.view!!).navigate(com.zaleksandr.aleksandr.myapplication.R.currentUserId.action_commonResultFragment_to_individualResultFragment2)
//                    adapter?.perioSelected(CommonResultAdapter.ClickByFilter.YEAR)
////                    hideLoading()
//                }
                else -> {
                }
            }
            true
        }
    }

    private fun adapterInit(rootView: View) {
        rootView.list_common_res_adapter.setHasFixedSize(true)
        rootView.list_common_res_adapter.layoutManager = LinearLayoutManager(context)

        items.add(City("", "", "0", "0","", "0", "0", "Kyiv", "0", "0","", "","", "0", "0", Date(), 0, "", "", "", "", "","","",""))
        items.add(City("", "", "0", "0", "0","", "0", "Kharkiv", "0", "0","", "","", "0", "0", Date(), 0, "", "", "", "", "", "", "",""))
        items.add(City("", "", "0", "0", "0","", "0", "Dnepr", "0", "0","", "0","", "0", "0", Date(), 0, "", "", "", "", "", "", "",""))
        items.add(City("", "", "0", "0", "0","", "0", "Zhytomyr", "0", "0","","", "0", "0", "0", Date(), 0, "", "", "", "", "", "", "",""))
        items.add(City("", "", "0", "0", "0","", "0", "Lviv", "0", "0","", "0","", "0", "0", Date(), 0, "", "", "", "", "", "", "",""))
        items.add(City("", "", "0", "0", "0","", "0", "Odessa", "0", "0","", "0","", "0", "0", Date(), 0, "", "", "", "", "", "", "",""))
        items.add(City("", "", "0", "0", "0","", "0", "Chernigov", "0", "0","", "0","", "0", "0", Date(), 0, "", "", "", "", "", "", "",""))

        adapter = CommonResultAdapter(items, this)
        rootView.list_common_res_adapter.adapter = adapter
    }
}
