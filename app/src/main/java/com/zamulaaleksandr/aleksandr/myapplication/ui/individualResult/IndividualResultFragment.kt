package com.zamulaaleksandr.aleksandr.myapplication.ui.individualResult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.zamulaaleksandr.aleksandr.myapplication.DialogCompositeDisposable
import com.zamulaaleksandr.aleksandr.myapplication.addTo
import com.zamulaaleksandr.aleksandr.myapplication.model.City
import com.zamulaaleksandr.aleksandr.myapplication.showMaterialDialogCancelDelete
import com.zamulaaleksandr.aleksandr.myapplication.showMaterialDialogNoYes
import com.zamulaaleksandr.aleksandr.myapplication.ui.commonResult.adapter.IndividualAdapter
import com.zamulaaleksandr.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_individual_result.*
import kotlinx.android.synthetic.main.fragment_individual_result.view.*


class IndividualResultFragment : Fragment(), IndividualAdapter.FragmentCommunication {
    override fun respond(city: City) {

        context?.showMaterialDialogCancelDelete(
                title = resources.getText(com.zamulaaleksandr.aleksandr.myapplication.R.string.delete_item_card_title).toString(),
                message = resources.getText(com.zamulaaleksandr.aleksandr.myapplication.R.string.delete_select_item).toString(),
                onNoClick = {},
                onYesClick = {
                    deleteNote(city)
                }
        )?.addTo(dialogDisposable)

    }

    var toolbar: Toolbar? = null
    var adapter: IndividualAdapter? = null
    private val noteRefCollection = firestoreInstance.collection("NewCity")
    var city: City? = null
    private val items: ArrayList<City> = ArrayList()
    private var mLastQueriedDocument: DocumentSnapshot? = null
    private val dialogDisposable = DialogCompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.zamulaaleksandr.aleksandr.myapplication.R.layout.fragment_individual_result, container, false)

        setUpRecyclerView(rootView)

//        rootView.button_individual_result_ind_res.setOnClickListener {
//            Navigation.findNavController(it).navigate(com.zamulaaleksandr.aleksandr.myapplication.R.id.action_individualResultFragment_to_commonResultFragment3)
//        }
//        rootView.individual_button_back.setOnClickListener {
//            Navigation.findNavController(it).navigate(com.zamulaaleksandr.aleksandr.myapplication.R.id.commonResultFragment)
//        }

//        toolbar = view?.findViewById(com.zamulaaleksandr.aleksandr.myapplication.R.id.toolbar)
//        (activity as AppCompatActivity).setSupportActionBar(toolbar)
//        (activity as AppCompatActivity).supportActionBar!!.hide()
        return rootView
    }

    override fun onResume() {
        super.onResume()
        (this.activity!!.toolbar as Toolbar).title = "Individual result"
    }

    private fun deleteNote(city: City?) {
        val db = FirebaseFirestore.getInstance()

        val noteRef = db
                .collection("NewCity")
                .document(city?.getId.toString())

        noteRef.delete().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (city != null) {
                    adapter?.removeNote(city)
                }
            } else {
//                makeSnackBarMessage("Failed. Check log.")
            }
        }
    }

    private fun setUpRecyclerView(rootView: View) {

        rootView.list_individual_result_adapter.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)
        rootView.list_individual_result_adapter.setHasFixedSize(false)
        rootView.list_individual_result_adapter.layoutManager = LinearLayoutManager(context)

        adapter = IndividualAdapter(this.context!!, items, this)
        rootView.list_individual_result_adapter.adapter = adapter

        val notesCollectionRef = firestoreInstance.collection("NewCity")
//        val notesQuery: Query?
//        notesQuery = if (mLastQueriedDocument!=null) {
//            notesCollectionRef
//                    .whereEqualTo("id", if ("${FirebaseAuth.getInstance().uid}" == FirebaseAuth.getInstance().currentUser!!.uid) {
//                        FirebaseAuth.getInstance().uid
//                    } else null)
//                    .orderBy("time", Query.Direction.DESCENDING)
//                    .startAfter(mLastQueriedDocument!!)
//        } else{
        notesCollectionRef
                .whereEqualTo("id", if ("${FirebaseAuth.getInstance().uid}" == FirebaseAuth.getInstance().currentUser!!.uid) {
                    FirebaseAuth.getInstance().uid
                } else null)
                .orderBy("time", Query.Direction.DESCENDING)

                .get().addOnCompleteListener { querydocumentSnapshot ->
                    if (querydocumentSnapshot.isSuccessful) {
                        for (documentSnapshot in querydocumentSnapshot.result!!) {
                            val note = documentSnapshot.toObject<City>(City::class.java)
                            items.add(note)
                        }

                        if (querydocumentSnapshot.result!!.size() != 0) {
                            empty_kitchen_categories.visibility = View.GONE
                            mLastQueriedDocument = querydocumentSnapshot.result!!.documents[querydocumentSnapshot.result!!.size() - 1]
                            adapter?.notifyDataSetChanged()
                        } else {
                            empty_kitchen_categories.visibility = View.VISIBLE
                            adapter?.notifyDataSetChanged()
                        }
                    } else {
                    }
                }
    }
}
