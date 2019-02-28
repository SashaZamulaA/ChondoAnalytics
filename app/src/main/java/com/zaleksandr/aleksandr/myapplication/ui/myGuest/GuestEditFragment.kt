package com.zaleksandr.aleksandr.myapplication.ui.myGuest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.zaleksandr.aleksandr.myapplication.DialogCompositeDisposable
import com.zaleksandr.aleksandr.myapplication.MainActivity.Companion.AUTHOR_KEY
import com.zaleksandr.aleksandr.myapplication.addTo
import com.zaleksandr.aleksandr.myapplication.model.City
import com.zaleksandr.aleksandr.myapplication.model.Guest
import com.zaleksandr.aleksandr.myapplication.showMaterialDialogCancelDelete
import com.zaleksandr.aleksandr.myapplication.ui.commonResult.adapter.MyGuestAdapter
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_update_guest.*
import kotlinx.android.synthetic.main.fragment_update_guest.view.*


class GuestEditFragment : Fragment(), MyGuestAdapter.FragmentCommunication {

    override fun respond(city: Guest) {
        context?.showMaterialDialogCancelDelete(
                title = resources.getText(com.zaleksandr.aleksandr.myapplication.R.string.delete_item_card_title).toString(),
                message = resources.getText(com.zaleksandr.aleksandr.myapplication.R.string.delete_select_item).toString(),
                onNoClick = {},
                onYesClick = {

                }
        )?.addTo(dialogDisposable)
    }

    companion object {
        val NAME = "name"
    }

    var path = ""
    var name = ""
    var toolbar: Toolbar? = null
    val notesUpdateGuestRef = firestoreInstance.collection("Guest")
    var city: Guest? = null
    private val dialogDisposable = DialogCompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.zaleksandr.aleksandr.myapplication.R.layout.fragment_update_guest, container, false)

        rootView.fab_update_guest.setOnClickListener {

            updateCurrentUser(

                    guest_name_edit.text.toString(),
                    guest_intro.isChecked,
                    guest_twoDay.isChecked,
                    guest_act.isChecked,
                    guest_21.isChecked,
                    guest_nwet.isChecked
            )
        }

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

//    fun updateNote(city: City?) {
//
//        val db = FirebaseFirestore.getInstance()
//
//        val noteRef = db
//                .collection("NewCity")
//                .document(city?.id.toString())
//
//        noteRef.update(
//                "intro", city?.intro
//        ).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                makeSnackBarMessage("Updated note")
////                adapter?.updateNote(city!!)
//            } else {
//                makeSnackBarMessage("Failed. Check log.")
//            }
//        }
//    }

    private fun updateCurrentUser(name: String = "", intro: Boolean, oneDay: Boolean, twoDay: Boolean, twOneDay: Boolean, nwet: Boolean) {

        val userFieldMap = mutableMapOf<String, Any>()

        notesUpdateGuestRef.document(path).update(userFieldMap)
        notesUpdateGuestRef.document(path).update("name", name,
                "intro", intro,
                "onedayWS", oneDay,
                "twoDayWS", twoDay,
                "twOneDay", twOneDay,
                "nwet", nwet)


//        notesUpdateGuestRef.document(path).update("onedayWS", oneDay)
//        notesUpdateGuestRef.document(path).update("twoDayWS", twoDay)
//        notesUpdateGuestRef.document(path).update("twOneDay", twOneDay)
//        notesUpdateGuestRef.document(path).update("onedayWS", oneDay)
//        notesUpdateGuestRef.document(path).update("twoDayWS", twoDay)
    }

    private fun setUpRecyclerView(rootView: View) {

        val name = arguments?.getString("name")
        val intro = arguments?.getBoolean("intro", false)

        notesUpdateGuestRef
                .whereEqualTo("currentUserId", if ("${FirebaseAuth.getInstance().uid}" == FirebaseAuth.getInstance().currentUser!!.uid) {
                    FirebaseAuth.getInstance().uid
                } else null).orderBy("time", Query.Direction.DESCENDING)
                .whereEqualTo("name", name)

                .get().addOnCompleteListener { querydocumentSnapshot ->
                    if (querydocumentSnapshot.isSuccessful) {
                        for (documentSnapshot in querydocumentSnapshot.result!!) {
                            val note = documentSnapshot.toObject<Guest>(Guest::class.java)
                            path = note.id.toString()
                            guest_intro.isChecked = intro!!
                            guest_name_edit.setText(name.toString())

                            val userFieldMap = mutableMapOf<String, Any>()
                            if (name != null) {
                                if (name.isNotBlank()) userFieldMap[AUTHOR_KEY] = name


//                            notesUpdateGuestRef.document(path
//                                    ?: throw NullPointerException("UID is null.")).update(userFieldMap)
//                        }

                            } else {
                            }
                        }
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