package com.zaleksandr.aleksandr.myapplication.ui.updateResult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.zaleksandr.aleksandr.myapplication.DialogCompositeDisposable
import com.zaleksandr.aleksandr.myapplication.addTo
import com.zaleksandr.aleksandr.myapplication.model.City
import com.zaleksandr.aleksandr.myapplication.showMaterialDialogCancelDelete
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.zaleksandr.aleksandr.myapplication.ui.updateResult.adapter.UpdateResultAdapter
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_update_my_result.*
import kotlinx.android.synthetic.main.fragment_update_my_result.view.*


class See7DaysMyResultFragment : Fragment(), UpdateResultAdapter.FragmentCommunication {

    override fun respond(city: City) {

        context?.showMaterialDialogCancelDelete(
                title = resources.getText(com.zaleksandr.aleksandr.myapplication.R.string.delete_item_card_title).toString(),
                message = resources.getText(com.zaleksandr.aleksandr.myapplication.R.string.delete_select_item).toString(),
                onNoClick = {},
                onYesClick = {
                    deleteNote(city)
                }
        )?.addTo(dialogDisposable)

    }

    override fun respondUpdate(city: City) {

        val bundle = Bundle()
        bundle.putString("getId", city.getId)

                Navigation.findNavController(this.view!!).navigate(com.zaleksandr.aleksandr.myapplication.R.id.action_see_7_day_my_resultFragment_to_changeMyResultFragment, bundle)
        items.clear()

    }


    var toolbar: Toolbar? = null
    var adapter: UpdateResultAdapter? = null
    var city: City? = null
    private val items: ArrayList<City> = ArrayList()
    private var mLastQueriedDocument: DocumentSnapshot? = null
    private val dialogDisposable = DialogCompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.zaleksandr.aleksandr.myapplication.R.layout.fragment_update_my_result, container, false)
        val actions = listOf("Update", "Delete")

        setUpRecyclerView(rootView)

        return rootView
    }





    override fun onResume() {
        super.onResume()
        (this.activity!!.toolbar as Toolbar).visibility = View.VISIBLE
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

    private fun makeSnackBarMessage(message: String) {
        Snackbar.make(this.view!!, message, Snackbar.LENGTH_SHORT).show()
    }

    fun updateNote(city: City?) {

        val db = FirebaseFirestore.getInstance()

        val noteRef = db
                .collection("NewCity")
                .document(city?.id.toString())

        noteRef.update(
                "intro", city?.intro
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                makeSnackBarMessage("Updated note")
//                adapter?.updateNote(city!!)
            } else {
                makeSnackBarMessage("Failed. Check log.")
            }
        }
    }

    private fun setUpRecyclerView(rootView: View) {

        rootView.list_update_my_result_adapter.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)
        rootView.list_update_my_result_adapter.setHasFixedSize(false)
        rootView.list_update_my_result_adapter.layoutManager = LinearLayoutManager(context)

        adapter = UpdateResultAdapter(this.context!!, items, this)
        rootView.list_update_my_result_adapter.adapter = adapter

        val notesCollectionRef = firestoreInstance.collection("NewCity")

        notesCollectionRef
                .whereEqualTo("id", if ("${FirebaseAuth.getInstance().uid}" == FirebaseAuth.getInstance().currentUser!!.uid) {
                    FirebaseAuth.getInstance().uid
                } else null)
                .orderBy("time", Query.Direction.DESCENDING)
                .limit(7)

                .get().addOnCompleteListener { querydocumentSnapshot ->
                    if (querydocumentSnapshot.isSuccessful) {
                        for (documentSnapshot in querydocumentSnapshot.result!!) {
                            val note = documentSnapshot.toObject<City>(City::class.java)
                            items.add(note)
                        }

                        if (querydocumentSnapshot.result!!.size() != 0) {
                            empty_individual_result_fragment.visibility = View.GONE
                            mLastQueriedDocument = querydocumentSnapshot.result!!.documents[querydocumentSnapshot.result!!.size()-1]
                            adapter?.notifyDataSetChanged()
                        } else {
                            empty_individual_result_fragment.visibility = View.VISIBLE
                            adapter?.notifyDataSetChanged()
                        }
                    } else {
                    }
                }
    }
}