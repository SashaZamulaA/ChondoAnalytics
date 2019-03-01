package com.zaleksandr.aleksandr.myapplication.ui.updateGuest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.zaleksandr.aleksandr.myapplication.DialogCompositeDisposable
import com.zaleksandr.aleksandr.myapplication.model.City
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import com.google.android.material.snackbar.Snackbar
import com.zaleksandr.aleksandr.myapplication.model.Guest
import com.zaleksandr.aleksandr.myapplication.ui.commonResult.adapter.MyGuestAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_my_guests.view.*


class GuestFragment : Fragment(), MyGuestAdapter.FragmentCommunication {

    override fun respond(city: Guest) {

        val bundle = Bundle()
        bundle.putString("name", city.name)
        bundle.putBoolean("intro", city.intro!!)
        Navigation.findNavController(this.view!!).navigate(com.zaleksandr.aleksandr.myapplication.R.id.action_showMyGuestFragment_to_updateMyGuestFragment, bundle)
        items.clear()

    }



    var toolbar: Toolbar? = null
    var adapter: MyGuestAdapter? = null
    private val noteRefCollection = firestoreInstance.collection("Guest")
    var city: Guest? = null
    private val items: ArrayList<Guest> = ArrayList()
    private var mLastQueriedDocument: DocumentSnapshot? = null
    private val dialogDisposable = DialogCompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.zaleksandr.aleksandr.myapplication.R.layout.fragment_my_guests, container, false)
        val actions = listOf("Update", "Delete")

        setUpRecyclerView(rootView)

//        bottomMenuInit(rootView)

        return rootView
    }


    override fun onResume() {
        super.onResume()
        (this.activity!!.toolbar as Toolbar).title = "My guests"
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

        rootView.list_my_guest_adapter.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)
        rootView.list_my_guest_adapter.setHasFixedSize(false)
        rootView.list_my_guest_adapter.layoutManager = LinearLayoutManager(context)


        adapter = MyGuestAdapter(this.context!!, items, this)





        rootView.list_my_guest_adapter.adapter = adapter

        val notesCollectionRef = firestoreInstance.collection("Guest")

        notesCollectionRef
                .whereEqualTo("currentUserId", if ("${FirebaseAuth.getInstance().uid}" == FirebaseAuth.getInstance().currentUser!!.uid) {
                    FirebaseAuth.getInstance().uid
                } else null)
                .orderBy("time", Query.Direction.DESCENDING)

                .get().addOnCompleteListener { querydocumentSnapshot ->
                    if (querydocumentSnapshot.isSuccessful) {
                        for (documentSnapshot in querydocumentSnapshot.result!!) {
                            val note = documentSnapshot.toObject<Guest>(Guest::class.java)
                            items.add(note)
                        }

                        if (querydocumentSnapshot.result!!.size() != 0) {
//                            empty_individual_result_fragment.visibility = View.GONE
                            mLastQueriedDocument = querydocumentSnapshot.result!!.documents[querydocumentSnapshot.result!!.size() - 1]
                            adapter?.notifyDataSetChanged()
                        } else {
//                            empty_individual_result_fragment.visibility = View.VISIBLE
                            adapter?.notifyDataSetChanged()
                        }
                    } else {
                    }
                }
    }
}

//    private fun bottomMenuInit(rootView: View) {
//        val layoutParams = rootView.bottom_navigation_person.layoutParams as CoordinatorLayout.LayoutParams
//        layoutParams.behavior = BottomNavigationViewBehavior()
//        rootView.bottom_navigation_person.setOnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                com.zaleksandr.aleksandr.myapplication.R.currentUserId.menu_year -> {
//                    adapter?.perioSelected(IndividualAdapter.ClickByFilter.YEAR)
//                }
//                com.zaleksandr.aleksandr.myapplication.R.currentUserId.menu_month -> {
//                    adapter?.perioSelected(IndividualAdapter.ClickByFilter.MONTH)
//                }
//                com.zaleksandr.aleksandr.myapplication.R.currentUserId.menu_week -> {
//                    adapter?.perioSelected(IndividualAdapter.ClickByFilter.WEEK)
//                }
//
//                else -> {
//                }
//            }
//            true
//        }
//    }
//
//}