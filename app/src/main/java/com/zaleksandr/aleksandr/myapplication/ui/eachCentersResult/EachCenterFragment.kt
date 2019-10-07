package com.zaleksandr.aleksandr.myapplication.ui.eachCentersResult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.zaleksandr.aleksandr.myapplication.BottomNavigationViewBehavior
import com.zaleksandr.aleksandr.myapplication.R
import com.zaleksandr.aleksandr.myapplication.model.City
import com.zaleksandr.aleksandr.myapplication.model.GoalCenter
import com.zaleksandr.aleksandr.myapplication.ui.eachCentersResult.adapter.EachCenterAdapter
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_each_senter.view.*

class EachCenterFragment : Fragment() {

    var toolbar: Toolbar? = null
    private val items: ArrayList<City> = ArrayList()
    private val itemsCenter: ArrayList<GoalCenter> = ArrayList()
    var adapter: EachCenterAdapter? = null
    var num = 1
    private var mLastQueriedDocument: DocumentSnapshot? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_each_senter, container, false)

        adapterInit(rootView)
        bottomMenuInit(rootView)

        adapter?.perioSelected(EachCenterAdapter.ClickByFilter.YEAR)
        return rootView
    }

    private fun adapterInit(rootView: View) {
        rootView.list_each_center_res_adapter.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)
        rootView.list_each_center_res_adapter.setHasFixedSize(false)
        rootView.list_each_center_res_adapter?.layoutManager = LinearLayoutManager(context)
        adapter = EachCenterAdapter(this.context!!, items)
        rootView.list_each_center_res_adapter?.adapter = adapter

        toolbar = view?.findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        val noteRefCommonCollectionForEachCenter = firestoreInstance.collection("EachCenter")
        val notesCollectionRef = firestoreInstance.collection("NewCity")
        val goleCenterRefCollection = firestoreInstance.collection("GoalCenters")
        val name = arguments?.getInt("pas", 0)

        if (name != null) {
            adapter?.getNumber(name)
        }

        notesCollectionRef.whereEqualTo("centers", when (name) {
            1 -> "Kyiv"
            2 -> "Kharkiv"
            3 -> "Dnepr"
            4 -> "Zhytomyr"
            5 -> "Lviv"
            6 -> "Odessa"
            7 -> "Chernigov"
            else -> null
        })
                .orderBy("time", Query.Direction.DESCENDING)
                .get().addOnCompleteListener { querydocumentSnapshot ->
                    if (querydocumentSnapshot.isSuccessful) {

                        for (documentSnapshot in querydocumentSnapshot.result!!) {
                            val note = documentSnapshot.toObject(City::class.java)
                            num++
                            if (num < 12) items.add(note)
                        }

                        if (querydocumentSnapshot.result!!.size() != 0) {
                            mLastQueriedDocument = querydocumentSnapshot.result!!.documents[querydocumentSnapshot.result!!.size() - 1]
                            adapter?.notifyDataSetChanged()
                        } else {
                            adapter?.notifyDataSetChanged()
                        }
                    } else {
                    }
                }
    }

    override fun onResume() {
        super.onResume()
        (this.activity!!.toolbar as Toolbar).title = "Each center result"
    }

    private fun bottomMenuInit(rootView: View) {
        val layoutParams = rootView.bottom_navigation_center.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.behavior = BottomNavigationViewBehavior()
        rootView.bottom_navigation_center.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_year -> {
                    adapter?.perioSelected(EachCenterAdapter.ClickByFilter.YEAR)
                }
                R.id.menu_month -> {
                    adapter?.perioSelected(EachCenterAdapter.ClickByFilter.MONTH)
                }
                R.id.menu_week -> {
                    adapter?.perioSelected(EachCenterAdapter.ClickByFilter.WEEK)
                }
                else -> {
                }
            }
            true
        }
    }

}
