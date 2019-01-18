package com.example.aleksandr.myapplication.ui.eachCentersResult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.model.City
import com.example.aleksandr.myapplication.ui.eachCentersResult.adapter.EachCenterAdapter
import com.example.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import com.example.aleksandr.myapplication.util.IFormatTime
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_each_senter.view.*
import javax.inject.Inject


class EachCenterFragment : Fragment() {

    var position: Int = 0
    var toolbar: Toolbar? = null

    private lateinit var adapter: EachCenterAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.example.aleksandr.myapplication.R.layout.fragment_each_senter, container, false)

        val name = arguments?.getInt("pas", 0)

        val query = firestoreInstance.collection("NewCity")
                .whereEqualTo("centers", when (name) {
                    0 -> "Kyiv"
                    1 -> "Kharkiv"
                    2 -> "Dnepr"
                    3 -> "Zhytomyr"
                    4 -> "Lviv"
                    5 -> "Odessa"
                    6-> "Chernigov"
                    else -> null
                })
                .orderBy("time", Query.Direction.ASCENDING)
        //
        val options = FirestoreRecyclerOptions.Builder<City>()
                .setQuery(query, City::class.java)
                .build()
        adapter = EachCenterAdapter(this.context!!, options)
        rootView?.list_each_center_res_adapter?.layoutManager = LinearLayoutManager(context)
        rootView?.list_each_center_res_adapter?.adapter = adapter

        toolbar = view?.findViewById(com.example.aleksandr.myapplication.R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        rootView.button_back.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.commonResultFragment)
        }

        return rootView
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
