package com.example.aleksandr.myapplication.ui.eachCentersResult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.model.City
import com.example.aleksandr.myapplication.ui.commonResult.adapter.CommonResultAdapter
import com.example.aleksandr.myapplication.ui.eachCentersResult.adapter.EachCenterAdapter
import com.example.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_each_senter.view.*




class EachCenterFragment : Fragment(), CommonResultAdapter.FragmentCommunication {

    override fun respond(position: Int) {

           }

    var position: Int = 0
    var initInt: CommonResultAdapter.FragmentCommunication? = null



    var toolbar: Toolbar? = null
    private lateinit var adapter: EachCenterAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.example.aleksandr.myapplication.R.layout.fragment_each_senter, container, false)

        val name = arguments?.getInt("pas", 0)

        val query = firestoreInstance.collection("NewCity")
                .whereEqualTo("centers", when (name) {
                    0 -> "Kyiv"
                    1 -> "Kharkiv"
                    else -> null
                })
                .orderBy("time", Query.Direction.ASCENDING)
        //
        val options = FirestoreRecyclerOptions.Builder<City>()
                .setQuery(query, City::class.java)
                .build()
        adapter = EachCenterAdapter(options)
        rootView?.list_each_center_res_adapter?.layoutManager = LinearLayoutManager(context)
        rootView?.list_each_center_res_adapter?.adapter = adapter

        toolbar = view?.findViewById(com.example.aleksandr.myapplication.R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)


        return rootView
    }

    private fun anapterInit(rootView: View) {

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
