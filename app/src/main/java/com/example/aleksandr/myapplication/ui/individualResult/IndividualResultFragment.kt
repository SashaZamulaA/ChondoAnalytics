package com.example.aleksandr.myapplication.ui.individualResult

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aleksandr.myapplication.model.City
import com.example.aleksandr.myapplication.ui.individualResult.adapter.IndividualResultAdapter
import com.example.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_individual_result.*
import kotlinx.android.synthetic.main.fragment_individual_result.view.*
import com.google.firebase.firestore.DocumentReference
import com.bumptech.glide.util.Util.getSnapshot
import com.google.firebase.firestore.DocumentSnapshot




class IndividualResultFragment : Fragment() {

    var toolbar: Toolbar? = null
    private lateinit var adapter: IndividualResultAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.example.aleksandr.myapplication.R.layout.fragment_individual_result, container, false)

        button_back
        setUpRecyclerView(rootView)


//            Toast.makeText(activity, "This is my Toast message!",
//                    Toast.LENGTH_LONG).show()

        rootView.button_individual_result_ind_res.setOnClickListener {
            Navigation.findNavController(it).navigate(com.example.aleksandr.myapplication.R.id.action_individualResultFragment_to_commonResultFragment3)
        }
        rootView.button_back.setOnClickListener {
            Navigation.findNavController(it).navigate(com.example.aleksandr.myapplication.R.id.commonResultFragment)
        }

        toolbar = view?.findViewById(com.example.aleksandr.myapplication.R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        return rootView
    }

    @SuppressLint("WrongConstant")
    private fun setUpRecyclerView(rootView: View) {
        val query = firestoreInstance.collection("NewCity")
                .whereEqualTo("id", if ("${FirebaseAuth.getInstance().uid}" == FirebaseAuth.getInstance().currentUser!!.uid) {
                    FirebaseAuth.getInstance().uid
                } else null)
                .orderBy("time", Query.Direction.DESCENDING)

        val options = FirestoreRecyclerOptions.Builder<City>()
                .setQuery(query, City::class.java)
                .build()
        //        when(id){ id ->  FirebaseAuth.getInstance().currentUser.toString() else -> null}

        adapter = IndividualResultAdapter(this.context!!, options)
        rootView.list_individual_result_adapter.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)
        rootView.list_individual_result_adapter.setHasFixedSize(true)
        rootView.list_individual_result_adapter.layoutManager = LinearLayoutManager(this.context)
        rootView.list_individual_result_adapter.adapter = adapter


//        val swipeHandler = object : SwipeToDeleteCallback(this.context!!) {
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                viewHolder.adapterPosition.let { adapter.deleteItem(it) }
//
//            }
//        }
//        ItemTouchHelper(swipeHandler).attachToRecyclerView(rootView.list_individual_result_adapter)


        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteItem(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(rootView.list_individual_result_adapter)

    }

    private fun deleteItem(position: Int) {
        val document = adapter.snapshots.getSnapshot(position).reference
        document.delete()



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
