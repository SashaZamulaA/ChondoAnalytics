package com.zaleksandr.aleksandr.myapplication.ui.eachCentersResult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaleksandr.aleksandr.myapplication.model.City
import com.zaleksandr.aleksandr.myapplication.ui.eachCentersResult.adapter.EachCenterAdapter
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.zaleksandr.aleksandr.myapplication.ui.eachCentersResult.adapter.EachCenterAdapter2
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_each_senter.view.*
import kotlinx.android.synthetic.main.fragment_individual_result.view.*


class EachCenterFragment : Fragment() {

    var position: Int = 0
    var toolbar: Toolbar? = null
    private lateinit var adapter: EachCenterAdapter2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.zaleksandr.aleksandr.myapplication.R.layout.fragment_each_senter, container, false)

//        rootView.button_back3.setOnClickListener {
//            Navigation.findNavController(it).navigate(com.zamulaaleksandr.aleksandr.myapplication.R.id.commonResultFragment)
//        }


        val name = arguments?.getInt("pas", 0)

        val query = firestoreInstance.collection("NewCity")
                .whereEqualTo("centers", when (name) {
                    1 -> "Kyiv"
                    2 -> "Kharkiv"
                    3 -> "Dnepr"
                    4 -> "Zhytomyr"
                    5 -> "Lviv"
                    6 -> "Odessa"
                    7-> "Chernigov"
                    else -> null
                })
                .orderBy("time", Query.Direction.DESCENDING)

        val options = FirestoreRecyclerOptions.Builder<City>()
                .setQuery(query, City::class.java)
                .build()

        rootView.list_each_center_res_adapter.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)
        rootView.list_each_center_res_adapter.setHasFixedSize(false)
        rootView?.list_each_center_res_adapter?.layoutManager = LinearLayoutManager(context)
        adapter = EachCenterAdapter2(this.context!!, options)
        rootView?.list_each_center_res_adapter?.adapter = adapter

        toolbar = view?.findViewById(com.zaleksandr.aleksandr.myapplication.R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

//        rootView.button_back2.setOnClickListener {
//            Navigation.findNavController(it).navigate(R.id.commonResultFragment)
//        }

        return rootView
    }

    override fun onResume() {
        super.onResume()
        (this.activity!!.toolbar as Toolbar).title = "Each center result"
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}
