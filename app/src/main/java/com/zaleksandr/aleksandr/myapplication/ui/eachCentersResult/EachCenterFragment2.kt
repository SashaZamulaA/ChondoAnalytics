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
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.zaleksandr.aleksandr.myapplication.R
import com.zaleksandr.aleksandr.myapplication.ui.eachCentersResult.adapter.EachCenterAdapter3
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_each_senter.view.*
import kotlinx.android.synthetic.main.fragment_individual_result.*
import kotlinx.android.synthetic.main.fragment_individual_result.view.*


class EachCenterFragment2 : Fragment() {


    var toolbar: Toolbar? = null
    private val items: ArrayList<City> = ArrayList()
    var adapter: EachCenterAdapter3? = null
    private var mLastQueriedDocument: DocumentSnapshot? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.zaleksandr.aleksandr.myapplication.R.layout.fragment_each_senter, container, false)

        adapterInit(rootView)

        return rootView
    }

    private fun adapterInit(rootView: View) {
        val notesCollectionRef = firestoreInstance.collection("NewCity")

        val name = arguments?.getInt("pas", 0)

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
                            val note = documentSnapshot.toObject<City>(City::class.java)
                            items.add(note)
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

        rootView.list_each_center_res_adapter.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)
        rootView.list_each_center_res_adapter.setHasFixedSize(false)
        rootView.list_each_center_res_adapter?.layoutManager = LinearLayoutManager(context)

        adapter = EachCenterAdapter3(this.context!!, items)

        rootView.list_each_center_res_adapter?.adapter = adapter

        toolbar = view?.findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

    }

    override fun onResume() {
        super.onResume()
        (this.activity!!.toolbar as Toolbar).title = "Each center result"
    }

}
