package com.zaleksandr.aleksandr.myapplication.ui.individualGoal

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.zaleksandr.aleksandr.myapplication.MainActivity
import com.zaleksandr.aleksandr.myapplication.ui.individualGoal.model.AdditionalGoalsModel
import com.zaleksandr.aleksandr.myapplication.ui.individualGoal.model.IndividualMonthGoalModel
import com.zaleksandr.aleksandr.myapplication.ui.individualGoal.model.IndividualWeekGoalModel
import com.zaleksandr.aleksandr.myapplication.ui.individualGoal.model.IndividualYearGoalModel
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_individual_goal.*
import kotlinx.android.synthetic.main.fragment_add_result2.view.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AddIndividualGoalFragment : Fragment() {

    private var pictureJustChange = false
    val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("City/${FirebaseAuth.getInstance().uid
                ?: throw NullPointerException("UID is null.")}")

    private val refCollectionYearGoal = firestoreInstance.collection("EachMemberYearGoal").document(FirebaseAuth.getInstance().uid!!)
    private val refCollectionMonthGoal = firestoreInstance.collection("EachMemberMonthGoal").document(FirebaseAuth.getInstance().uid!!)
    private val refCollectionWeekGoal = firestoreInstance.collection("EachMemberWeekGoal").document(FirebaseAuth.getInstance().uid!!)
    private val refCollectionAdditionalGoal = firestoreInstance.collection("EachMemberAdditionalGoal").document(FirebaseAuth.getInstance().uid!!)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.zaleksandr.aleksandr.myapplication.R.layout.fragment_add_individual_goal, container, false)

        rootView.result_fab_confirm_goal.setOnClickListener {
            addIndividualYearGoal()
            addIndividualMonthGoal()
            addIndividualWeekGoal()
            addIndividualAdditionGoal()
        }
        return rootView
    }

    override fun onResume() {
        super.onResume()
        (this.activity!!.toolbar as Toolbar).title = "Add result"
    }

    private fun addIndividualYearGoal() {

        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid

        val yearIntro = introduction_year_goal_edittext.text.toString()
        val yearOneDay = one_day_year_goal_edittext.text.toString()
        val yearTwoDay = two_day_year_goal_edittext.text.toString()
        val yearActionaiser = indivitual_action_goal.text.toString()
        val yearTWOne = individual_goal_day21_seminar_edittext.text.toString()
        val yearNWET = individual_goal_result_nwet_edittext.text.toString()

        FirestoreUtil.currentUserDocRef.addSnapshotListener { documentSnapshot, _ ->
            FirestoreUtil.getCurrentUser { user ->
                if (documentSnapshot?.exists()!!) {
                    refCollectionYearGoal.set(IndividualYearGoalModel(currentUserId, yearIntro, yearOneDay, yearTwoDay, yearTWOne, yearActionaiser, yearNWET))
                }
            }
        }
        startActivity(Intent(context, MainActivity::class.java))
    }

    private fun addIndividualMonthGoal() {

        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid

        val monthIntro = introduction_month_goal.text.toString()
        val monthOneDay = one_day_month_goal.text.toString()
        val monthTwoDay = two_day_month_goal.text.toString()
        val monthActioniser = month_action_goal.text.toString()
        val monthTWOne = individ_goal_21_month.text.toString()
        val monthNWET = ind_goal_nwet_month_edittext.text.toString()

        FirestoreUtil.currentUserDocRef.addSnapshotListener { documentSnapshot, _ ->
            FirestoreUtil.getCurrentUser { user ->
                if (documentSnapshot?.exists()!!) {
                    refCollectionMonthGoal.set(IndividualMonthGoalModel(currentUserId, monthIntro, monthOneDay, monthTwoDay, monthActioniser, monthTWOne, monthNWET))
                }
            }
        }
        startActivity(Intent(context, MainActivity::class.java))
    }

    private fun addIndividualWeekGoal() {

        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid

        val weekMMBK = ind_mmbk_week_goal.text.toString()
        val weekLectTraining = indiv_lect_tr_week.text.toString()
        val weekContacts = indiv_contact_week_doal.text.toString()
        val weekIntro = ind_week_goal_intro.text.toString()
        val weekOneDay = ind_1_day_week_goal.text.toString()
        val weekTwoDay = ind_2_day_week_goal.text.toString()
        val weekAct = ind_act_week_goal.text.toString()

        FirestoreUtil.currentUserDocRef.addSnapshotListener { documentSnapshot, _ ->
            FirestoreUtil.getCurrentUser { user ->
                if (documentSnapshot?.exists()!!) {
                    refCollectionWeekGoal.set(IndividualWeekGoalModel(currentUserId, weekMMBK, weekLectTraining, weekContacts, weekIntro, weekOneDay, weekTwoDay, weekAct))
                }
            }
        }
        startActivity(Intent(context, MainActivity::class.java))
    }

    private fun addIndividualAdditionGoal() {

        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid

        val yearDPKor = ind_goal_dp_kor.text.toString()
        val yearDPUkr = ind_goal_dp_ukr.text.toString()
        val yearHDH = ind_goal_hdh.text.toString()
        val yearMobilisation = ind_goal_mobilisation.text.toString()


        FirestoreUtil.currentUserDocRef.addSnapshotListener { documentSnapshot, _ ->
            FirestoreUtil.getCurrentUser { user ->
                if (documentSnapshot?.exists()!!) {
                    refCollectionAdditionalGoal.set(AdditionalGoalsModel(currentUserId, yearDPKor, yearDPUkr, yearHDH, yearMobilisation))
                }
            }
        }
        startActivity(Intent(context, MainActivity::class.java))
    }

}
