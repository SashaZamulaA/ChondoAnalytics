package com.zaleksandr.aleksandr.myapplication.ui.myResult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.zaleksandr.aleksandr.myapplication.BottomNavigationViewBehavior
import com.zaleksandr.aleksandr.myapplication.DialogCompositeDisposable
import com.zaleksandr.aleksandr.myapplication.addTo
import com.zaleksandr.aleksandr.myapplication.model.City
import com.zaleksandr.aleksandr.myapplication.showMaterialDialogCancelDelete
import com.zaleksandr.aleksandr.myapplication.ui.commonResult.adapter.IndividualAdapter
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_individual_result.*
import kotlinx.android.synthetic.main.fragment_individual_result.view.*


class MyResultFragment : Fragment(), IndividualAdapter.FragmentCommunication {
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

    var toolbar: Toolbar? = null
    var adapter: IndividualAdapter? = null
    var city: City? = null
    private val items: ArrayList<City> = ArrayList()
    private var mLastQueriedDocument: DocumentSnapshot? = null
    private val dialogDisposable = DialogCompositeDisposable()

    enum class ClickByFilter {
        MONTH, WEEK, YEAR
    }

    var period = 3
    fun perioSelected(periodSelected: IndividualAdapter.ClickByFilter) {

        when (periodSelected) {

            IndividualAdapter.ClickByFilter.WEEK -> {
                period = 1;
            }
            IndividualAdapter.ClickByFilter.MONTH -> {
                period = 2;
            }
            IndividualAdapter.ClickByFilter.YEAR -> {
                period = 3;
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.zaleksandr.aleksandr.myapplication.R.layout.fragment_individual_result, container, false)
        val actions = listOf("Update", "Delete")

        setUpRecyclerView(rootView)

        bottomMenuInit(rootView)
//        rootView.button_individual_result_ind_res.setOnClickListener {
//            Navigation.findNavController(it).navigate(com.zamulaaleksandr.aleksandr.myapplication.R.currentUserId.action_individualResultFragment_to_commonResultFragment3)
//        }
//        rootView.individual_button_back.setOnClickListener {
//            Navigation.findNavController(it).navigate(com.zamulaaleksandr.aleksandr.myapplication.R.currentUserId.commonResultFragment)
//        }
//        toolbar = view?.findViewById(com.zamulaaleksandr.aleksandr.myapplication.R.currentUserId.toolbar)
//        (activity as AppCompatActivity).setSupportActionBar(toolbar)
//        (activity as AppCompatActivity).supportActionBar!!.hide()
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

//        rootView.list_individual_result_adapter.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)
//        rootView.list_individual_result_adapter.setHasFixedSize(false)
//        rootView.list_individual_result_adapter.layoutManager = LinearLayoutManager(context)
//
//        adapter = IndividualAdapter(this.context!!, items, this)
//        rootView.list_individual_result_adapter.adapter = adapter
        var sumMmbk = 0
        var sumAppr = 0
        var sumCont = 0
        var sumStr = 0
        var sumIntro = 0
        var sumOneD1 = 0
        var sumTwoD1 = 0
        var sumAct = 0
        var sumTwent1 = 0
        var sumNwet = 0
        var sumCenterCh = 0
        var sumLectInCenter = 0
        var supLectOnStr = 0
        var sumEduMat = 0
        var sumDpKor = 0
        var sumDpUkr = 0
        var sumMob = 0
        var sumHDH = 0

        val notesCollectionRef = firestoreInstance.collection("NewCity")

        notesCollectionRef
                .whereEqualTo("id", if ("${FirebaseAuth.getInstance().uid}" == FirebaseAuth.getInstance().currentUser!!.uid) {
                    FirebaseAuth.getInstance().uid
                } else null)
                .orderBy("time", Query.Direction.DESCENDING)

                .get().addOnCompleteListener { querydocumentSnapshot ->
                    if (querydocumentSnapshot.isSuccessful) {
                        for (documentSnapshot in querydocumentSnapshot.result!!) {

                            val resultNote = documentSnapshot.toObject(City::class.java)

                            if (resultNote.descriptionGoal!!.isNotEmpty()) {
                                goal_description.setText(resultNote.descriptionGoal)
                            }
                            if (!resultNote.mmbk.isNullOrEmpty()) {
                                val mmbk = (Integer.parseInt(resultNote.mmbk))
                                sumMmbk += mmbk
                            }

                            if (!resultNote.timeStr.isNullOrEmpty()) {
                                val timestr = (Integer.parseInt(resultNote.timeStr))
                                sumStr += timestr
                            }

                            if (!resultNote.approach.isNullOrEmpty()) {
                                val approachTime = (Integer.parseInt(resultNote.approach))
                                sumAppr += approachTime
                            }

                            if (!resultNote.contact.isNullOrEmpty()) {
                                val contact = (Integer.parseInt(resultNote.contact))
                                sumCont += contact
                            }

                            if (resultNote.intro.isNotEmpty()) {
                                val intro = (Integer.parseInt(resultNote.intro))
                                sumIntro += intro
                            }

                            if (!resultNote.onedayWS.isNullOrEmpty()) {
                                val onaDay = Integer.parseInt(resultNote.onedayWS)
                                sumOneD1 += onaDay
                            }
                            if (!resultNote.twoDayWS.isNullOrEmpty()) {
                                val twoDay = Integer.parseInt(resultNote.twoDayWS)
                                sumTwoD1 += twoDay
                            }
                            if (!resultNote.actionaiser.isNullOrEmpty()) {
                                val act = Integer.parseInt(resultNote.actionaiser)
                                sumAct += act
                            }
                            if (!resultNote.twOneDay.isNullOrEmpty()) {
                                val twOneDay = Integer.parseInt(resultNote.twOneDay)
                                sumTwent1 += twOneDay
                            }
                            if (!resultNote.nwet.isNullOrEmpty()) {
                                val nwet = Integer.parseInt(resultNote.nwet)
                                sumNwet += nwet
                            }
                            if (!resultNote.timeCenter.isNullOrEmpty()) {
                                val timeCen = Integer.parseInt(resultNote.timeCenter)
                                sumCenterCh += timeCen
                            }
                            if (!resultNote.lectCentr.isNullOrEmpty()) {
                                val lectC = Integer.parseInt(resultNote.lectCentr)
                                sumLectInCenter += lectC
                            }
                            if (!resultNote.lectOnStr.isNullOrEmpty()) {
                                val lectStr = Integer.parseInt(resultNote.lectOnStr)
                                supLectOnStr += lectStr
                            }
                            if (!resultNote.eduMat.isNullOrEmpty()) {
                                val edu = Integer.parseInt(resultNote.eduMat)
                                sumEduMat += edu
                            }
                            if (!resultNote.dpKor.isNullOrEmpty()) {
                                val dpK = Integer.parseInt(resultNote.dpKor)
                                sumDpKor += dpK
                            }
                            if (!resultNote.dp.isNullOrEmpty()) {
                                val dpU = Integer.parseInt(resultNote.dp)
                                sumDpUkr += dpU
                            }
                            if (!resultNote.mobilis.isNullOrEmpty()) {
                                val mob = Integer.parseInt(resultNote.mobilis)
                                sumMob += mob
                            }

                        }

                        my_res_mmbk.text = sumMmbk.toString()
                        my_res_time_str.text = sumStr.toString()
                        my_res_appr.text = sumAppr.toString()
                        my_res_cont.text = sumCont.toString()
                        my_res_intr.text = sumIntro.toString()
                        my_res_1_day.text = sumOneD1.toString()
                        my_res_2_day.text = sumTwoD1.toString()
                        my_res_act.text = sumAct.toString()
                        my_res_21_day.text = sumTwent1.toString()
                        my_res_nwet.text = sumNwet.toString()
                        my_res_center_ch.text = sumCenterCh.toString()
                        my_res_center_lect.text = sumLectInCenter.toString()
                        my_res_str_lect.text = supLectOnStr.toString()
                        my_res_edu_mat.text = sumEduMat.toString()
                        my_res_dp_kor.text = sumDpKor.toString()
                        my_res_dp_ukr.text = sumDpUkr.toString()
                        my_res_mob.text = sumMob.toString()
                    }
                }
    }


    private fun bottomMenuInit(rootView: View) {
        val layoutParams = rootView.bottom_navigation_person.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.behavior = BottomNavigationViewBehavior()
        rootView.bottom_navigation_person.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                com.zaleksandr.aleksandr.myapplication.R.id.menu_year -> {
//                adapter?.perioSelected(IndividualAdapter.ClickByFilter.YEAR)
                }
                com.zaleksandr.aleksandr.myapplication.R.id.menu_month -> {
//                adapter?.perioSelected(IndividualAdapter.ClickByFilter.MONTH)
                }
                com.zaleksandr.aleksandr.myapplication.R.id.menu_week -> {
//                adapter?.perioSelected(IndividualAdapter.ClickByFilter.WEEK)
                }

                else -> {
                }
            }
            true
        }
    }

}