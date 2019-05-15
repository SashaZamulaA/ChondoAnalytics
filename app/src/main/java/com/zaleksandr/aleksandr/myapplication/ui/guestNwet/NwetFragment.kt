package com.zaleksandr.aleksandr.myapplication.ui.guestNwet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.zaleksandr.aleksandr.myapplication.R
import com.zaleksandr.aleksandr.myapplication.model.Guest
import com.zaleksandr.aleksandr.myapplication.ui.guestNwet.NwetAdapter.NwetAdapter
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_nwet_guests.view.*


class NwetFragment : Fragment() {

    var toolbar: Toolbar? = null
    var adapter: NwetAdapter? = null
    var peopleCategory: Int = 0
    var city: Guest? = null
    private val items: ArrayList<Guest> = ArrayList()
    private var mLastQueriedDocument: DocumentSnapshot? = null
    var numberOfCity = 0
    var name = 0
    val notesCollectionRef = firestoreInstance.collection("Guest")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.zaleksandr.aleksandr.myapplication.R.layout.fragment_nwet_guests, container, false)
        rootView.list_nwet_adapter.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)
        rootView.list_nwet_adapter.setHasFixedSize(false)
        rootView.list_nwet_adapter.layoutManager = LinearLayoutManager(context)

        adapter = NwetAdapter(this.context!!, items, this)

        rootView.button_back_guest.setOnClickListener {

            Navigation.findNavController(this.view!!).navigate(R.id.action_addNwetFragment_to_commonResultFragment)

        }

        rootView.list_nwet_adapter.adapter = adapter
        items.clear()
        setUpRecyclerView()

        rootView.popup.setOnClickListener {
            val popupMenu = PopupMenu(context, it)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_kyiv -> {
                        items.clear()
                        numberOfCity = 1; setUpRecyclerView()
                        true
                    }
                    R.id.menu_kharkiv -> {
                        items.clear()
                        numberOfCity = 2; setUpRecyclerView()
                        true
                    }
                    R.id.menu_dnepr -> {
                        items.clear()
                        numberOfCity = 3; setUpRecyclerView()
                        true
                    }
                    R.id.menu_zhytomyr -> {
                        items.clear()
                        numberOfCity = 4; setUpRecyclerView()
                        true
                    }
                    R.id.menu_lviv -> {
                        items.clear()
                        numberOfCity = 5; setUpRecyclerView()
                        true
                    }
                    R.id.menu_odessa -> {
                        items.clear()
                        numberOfCity = 6; setUpRecyclerView()
                        true
                    }
                    R.id.menu_chernigov -> {
                        items.clear()
                        numberOfCity = 7; setUpRecyclerView()
                        true
                    }
                    else -> false
                }
            }

            popupMenu.inflate(R.menu.option_menu)

            try {
                val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldMPopup.isAccessible = true
                val mPopup = fieldMPopup.get(popupMenu)
                mPopup.javaClass
                        .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                        .invoke(mPopup, true)
            } catch (e: Exception) {
                Log.e("Main", "Error showing menu icons.", e)
            } finally {
                popupMenu.show()
            }
        }

        return rootView
    }

    override fun onResume() {
        super.onResume()
//        (this.activity!!.toolbar as Toolbar).title = "The guests"
        (this.activity!!.toolbar as Toolbar).visibility = View.GONE
    }

    private fun setUpRecyclerView() {

        name = arguments?.getInt("people", 0)!!

        adapter?.addTypeGruest(name)

        if (numberOfCity == 0) {

            notesCollectionRef
                    .whereEqualTo(when (name) {
                        1 -> "intro"
                        2 -> "onedayWS"
                        3 -> "twoDayWS"
                        4 -> "actionaiser"
                        5 -> "twOneDay"
                        6 -> "nwet"
                        else -> ""
                    }, true)
                    .orderBy(when (name) {
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

                                if (note.currentUserId == "75zm78KzceXAvHT7NlOeYmYqbyV2"
                                        || note.currentUserId == "mwgvrSjiXmgA1wjSEDiu6lgkScs2"
                                        || note.currentUserId == "AgQUDOJ23Ie0YlRhn4ThxPhSW6q2"
                                        || note.currentUserId == "UwK9No4ZlYb4bpnMXbhGukPPg4t2"
                                        || note.currentUserId == "J8n8rpR3DBTXLQK7MseiF346L7f1"
                                        || note.currentUserId == "jlkJA9DdE9cKE4qDL7ru18Sfdis1")
                                items.add(note)


                                adapter?.notifyDataSetChanged()
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
        } else {
            notesCollectionRef
                    .whereEqualTo(when (name) {
                        1 -> "intro"
                        2 -> "onedayWS"
                        3 -> "twoDayWS"
                        4 -> "actionaiser"
                        5 -> "twOneDay"
                        6 -> "nwet"
                        else -> ""
                    }, true)
                    .whereEqualTo("centers", when (numberOfCity) {
                        1 -> "Kyiv"
                        2 -> "Kharkiv"
                        3 -> "Dnepr"
                        4 -> "Zhytomyr"
                        5 -> "Lviv"
                        6 -> "Odessa"
                        7 -> "Chernigov"
                        else -> ""
                    })
                    .orderBy(when (name) {
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

                                if (note.currentUserId == "75zm78KzceXAvHT7NlOeYmYqbyV2"
                                        || note.currentUserId == "mwgvrSjiXmgA1wjSEDiu6lgkScs2"
                                        || note.currentUserId == "AgQUDOJ23Ie0YlRhn4ThxPhSW6q2"
                                        || note.currentUserId == "UwK9No4ZlYb4bpnMXbhGukPPg4t2"
                                        || note.currentUserId == "J8n8rpR3DBTXLQK7MseiF346L7f1"
                                        || note.currentUserId == "jlkJA9DdE9cKE4qDL7ru18Sfdis1")
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
}