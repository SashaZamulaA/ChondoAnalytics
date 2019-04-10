package com.zaleksandr.aleksandr.myapplication.ui.guestNwet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.zaleksandr.aleksandr.myapplication.DialogCompositeDisposable
import com.zaleksandr.aleksandr.myapplication.model.Guest
import com.zaleksandr.aleksandr.myapplication.ui.guestNwet.NwetAdapter.NwetAdapter
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_nwet_guests.view.*


class NwetFragment : Fragment() {

    var toolbar: Toolbar? = null
    var adapter: NwetAdapter? = null
    private val noteRefCollection = firestoreInstance.collection("Guest")
    var peopleCategory: Int = 0
    var city: Guest? = null
    private val items: ArrayList<Guest> = ArrayList()
    private var mLastQueriedDocument: DocumentSnapshot? = null
    private val dialogDisposable = DialogCompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.zaleksandr.aleksandr.myapplication.R.layout.fragment_nwet_guests, container, false)
        val actions = listOf("Update", "Delete")

        setUpRecyclerView(rootView)

//        bottomMenuInit(rootView)

        return rootView
    }


    fun getPeopleCategory(peopleCategory: Int): Int {
        this.peopleCategory = peopleCategory
        return peopleCategory
    }


    override fun onResume() {
        super.onResume()
        (this.activity!!.toolbar as Toolbar).title = "The guests"
    }

    private fun makeSnackBarMessage(message: String) {
        Snackbar.make(this.view!!, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun setUpRecyclerView(rootView: View) {

        rootView.list_nwet_adapter.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)
        rootView.list_nwet_adapter.setHasFixedSize(false)
        rootView.list_nwet_adapter.layoutManager = LinearLayoutManager(context)


        adapter = NwetAdapter(this.context!!, items, this)

        rootView.list_nwet_adapter.adapter = adapter

        val notesCollectionRef = firestoreInstance.collection("Guest")


        val name = arguments?.getInt("people", 0)

        if (name != null) {
            getPeopleCategory(name)
            adapter?.addTypeGruest(name)

        }


        notesCollectionRef
                .whereEqualTo( when (name) {
                    1 -> "intro"
                    2 -> "onedayWS"
                    3 -> "twoDayWS"
                    4 -> "actionaiser"
                    5 -> "twOneDay"
                    6 -> "nwet"
                    else -> ""
                }, true)
                .orderBy( when (name){
                    1 -> "timeIntro"
                    2 -> "timeOneDay"
                    3 -> "timeTwoDay"
                    4 -> "timeAct"
                    5 -> "time21Day"
                    6 -> "timeNwet"
                    else -> "time"
                }, Query.Direction.DESCENDING)
                .get().addOnCompleteListener { querydocumentSnapshot ->
                    if (querydocumentSnapshot.isSuccessful) {
                        for (documentSnapshot in querydocumentSnapshot.result!!) {
                            val note = documentSnapshot.toObject<Guest>(Guest::class.java)
                            items.add(note)

                            adapter?.addTypeGruest(name!!)
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