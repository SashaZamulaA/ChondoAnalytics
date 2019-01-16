package com.example.aleksandr.myapplication.ui.eachCentersResult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.model.City
import com.example.aleksandr.myapplication.ui.individualResult.adapter.IndividualResultAdapter
import com.example.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_individual_result.view.*


class EachCenterFragment : Fragment() {

    var toolbar: Toolbar? = null
    private lateinit var adapter: IndividualResultAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.example.aleksandr.myapplication.R.layout.fragment_each_senter, container, false)


//        val query = firestoreInstance.collection("NewCity")
//                .whereEqualTo("id", if ("${FirebaseAuth.getInstance().uid}" == FirebaseAuth.getInstance().currentUser!!.uid){ FirebaseAuth.getInstance().uid } else null )
//                .orderBy("time", Query.Direction.ASCENDING)
//
//        val options = FirestoreRecyclerOptions.Builder<City>()
//                .setQuery(query, City::class.java)
//                .build()
////        when(id){ id ->  FirebaseAuth.getInstance().currentUser.toString() else -> null}
//        adapter = IndividualResultAdapter(options)
////        rootView.list_individual_result_adapter.setHasFixedSize(true)
//        rootView.list_individual_result_adapter.layoutManager = LinearLayoutManager(context)
//        rootView.list_individual_result_adapter.adapter = adapter
//
////            Toast.makeText(activity, "This is my Toast message!",
////                    Toast.LENGTH_LONG).show()
//
//        rootView.button_common_result_from_ind.setOnClickListener {
//            Navigation.findNavController(it).navigate(R.id.action_individualResultFragment_to_commonResultFragment3)
//        }
//
//        toolbar = view?.findViewById(com.example.aleksandr.myapplication.R.id.toolbar)
//        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        return rootView
    }

    override fun onStart() {
        super.onStart()
//        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
//        adapter.stopListening()
    }
}
