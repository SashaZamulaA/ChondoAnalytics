package com.example.aleksandr.myapplication.ui.individualResult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aleksandr.myapplication.*
import com.example.aleksandr.myapplication.model.City
import com.example.aleksandr.myapplication.ui.commonResult.adapter.IndividualAdapter
import com.example.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_individual_result.*
import kotlinx.android.synthetic.main.fragment_individual_result.view.*
import android.provider.DocumentsContract.getDocumentId
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.android.gms.tasks.OnSuccessListener




class IndividualResultFragment : Fragment(), IndividualAdapter.FragmentCommunication {
    override fun respond(city: City) {

            context?.showMaterialDialogCancelDelete(
                    title = resources.getText(R.string.delete).toString(),
                    message = resources.getText(R.string.delete).toString(),
                    onNoClick = {},
                    onYesClick = {
deleteNote(city)
//                        presenter.onRemoveButtonClick()
//                        adapter.selectNone()
                    }
            )?.addTo(dialogDisposable)

    }

    var toolbar: Toolbar? = null
    var adapter: IndividualAdapter? = null
    private val noteRefCollection = firestoreInstance.collection("NewCity")
    var city: City? =null
    private val items: ArrayList<City> = ArrayList()
    private var mLastQueriedDocument: DocumentSnapshot? = null
    private val dialogDisposable = DialogCompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.example.aleksandr.myapplication.R.layout.fragment_individual_result, container, false)

        button_back2
        setUpRecyclerView(rootView)

        rootView.button_individual_result_ind_res.setOnClickListener {
            Navigation.findNavController(it).navigate(com.example.aleksandr.myapplication.R.id.action_individualResultFragment_to_commonResultFragment3)
        }
        rootView.button_back2.setOnClickListener {
            Navigation.findNavController(it).navigate(com.example.aleksandr.myapplication.R.id.commonResultFragment)
        }

        toolbar = view?.findViewById(com.example.aleksandr.myapplication.R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        return rootView
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

        adapter = IndividualAdapter(items, this)
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
                    //                        Log.d(TAG, "onComplete: got a new note. Position: " + (mNotes.size() - 1));
                }

                if (querydocumentSnapshot.result!!.size() != 0) {
                    empty_kitchen_categories.visibility = View.GONE
                    mLastQueriedDocument = querydocumentSnapshot.result!!.documents[querydocumentSnapshot.result!!.size() - 1]
                    adapter?.notifyDataSetChanged()
            } else{
                empty_kitchen_categories.visibility = View.VISIBLE
                adapter?.notifyDataSetChanged()}
            } else {
//

// makeSnackBarMessage("Query Failed. Check Logs.")
            }
        }
//        val query = firestoreInstance.collection("NewCity")
//                .whereEqualTo("id", if ("${FirebaseAuth.getInstance().uid}" == FirebaseAuth.getInstance().currentUser!!.uid) {
//                    FirebaseAuth.getInstance().uid
//                } else null)
//                .orderBy("time", Query.Direction.DESCENDING)

//        query.addSnapshotListener{ querySnapshot, _ ->
//            if(querySnapshot?.isEmpty!!){
//                empty_kitchen_categories.visibility = isVisible.toAndroidVisibility()
//            }
//        }
//        val options = FirestoreRecyclerOptions.Builder<City>()
//                .setQuery(query, City::class.java)
//                .build()
        //        when(id){ id ->  FirebaseAuth.getInstance().currentUser.toString() else -> null}





//        val swipeHandler = object : SwipeToDeleteCallback(this.context!!) {
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                viewHolder.adapterPosition.let { adapter.deleteItem(it) }
//
//            }
//        }
//        ItemTouchHelper(swipeHandler).attachToRecyclerView(rootView.list_individual_result_adapter)


//        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
//                ItemTouchHelper.LEFT) {
//            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
//                return false
//            }
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                adapter.(viewHolder.adapterPosition)
//                refreshItems()
//            }
//        }).attachToRecyclerView(rootView.list_individual_result_adapter)

    }



    fun refreshItems() {
        fragmentManager?.beginTransaction()
                ?.detach(this)
                ?.attach(this)
                ?.commit()
    }
//    private fun deleteItem(position: Int) {
//        val document = adapter.snapshots.getSnapshot(position).reference
//        document.delete()
//    }

}
