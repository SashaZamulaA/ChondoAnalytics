package com.zaleksandr.aleksandr.myapplication.ui.addGoal

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.zaleksandr.aleksandr.myapplication.DialogCompositeDisposable
import com.zaleksandr.aleksandr.myapplication.MainActivity
import com.zaleksandr.aleksandr.myapplication.model.Goal
import kotlinx.android.synthetic.main.fragment_add_goal.*
import kotlinx.android.synthetic.main.fragment_add_goal.view.*
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class GoalFragment : Fragment() {
    private val dialogDisposable = DialogCompositeDisposable()
    private var pictureJustChange = false
    val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    val firestoreInstanceGoal: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("City/${FirebaseAuth.getInstance().uid
                ?: throw NullPointerException("UID is null.")}")

    var result: String? = null
    private val noteRefCollection = firestoreInstance.collection("NewCity").document()
    private val goleRefCollection = firestoreInstance.collection("Goal").document()
    private val goalRefDocumentCurrent = firestoreInstanceGoal.collection("CurrentGoal").document("goal")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.zaleksandr.aleksandr.myapplication.R.layout.fragment_add_goal, container, false)

        rootView.button_save.setOnClickListener {
            addNote()
        }

        rootView.button_back_goal.setOnClickListener {
            Navigation.findNavController(it).navigate(com.zaleksandr.aleksandr.myapplication.R.id.commonResultFragment)
        }
        return rootView
    }

    private fun addNote() {

        val yearIntro = goal_year_introduction_edittext.text.toString()
        val yearOneDay = goal_year_one_day_seminar_edittext.text.toString()
        val yearTwoDay = goal_year_two_day_seminar_edit_text.text.toString()
        val yearAct = goal_act_year_edit_text.text.toString()
        val yearTWOne = goal_year_21day_edit_text.text.toString()
        val tearNWET = goal_year_two_day_seminar_edittext.text.toString()

        val monthIntro = goal_month_introduction_edittext.text.toString()
        val monthOneDay = goal_month_one_day_seminar_edittext.text.toString()
        val monthTwoDay = goal_month_two_day_seminar_edit_text.text.toString()
        val monthAct = goal_month_act_edit_text.text.toString()
        val monthTWOne = goal_month_21day_edit_text.text.toString()
        val monthNWET = goal_month_team_edittext.text.toString()

        val weekIntro = goal_week_introduction_edittext.text.toString()
        val weekOneDay = goal_week_one_day_seminar_edittext.text.toString()
        val weekTwoDay = goal_week_two_day_seminar_edit_text.text.toString()
        val weekAct = goal_week_act_edit_text.text.toString()


        val dayIntro = goal_day_introduction_edittext.text.toString()
        val dayOneDay = goal_day_two_day_seminar_edit_text.text.toString()
        val dayTwoDay = goal_day_two_day_seminar_edit_text.text.toString()

        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val dataToSave = HashMap<String, Any>()
        val timestamp = System.currentTimeMillis()

        val id = goleRefCollection.id

        goalRefDocumentCurrent.set(Goal(currentUserId, yearIntro, yearOneDay, yearTwoDay, yearAct, yearTWOne, tearNWET, monthIntro, monthOneDay, monthTwoDay, monthAct, monthTWOne, monthNWET, weekIntro, weekOneDay, weekTwoDay, weekAct, dayIntro, dayOneDay, dayTwoDay))
        startActivity(Intent(context, MainActivity::class.java))

        goleRefCollection.set(Goal(currentUserId, yearIntro, yearOneDay, yearTwoDay, yearAct, yearTWOne, tearNWET, monthIntro, monthOneDay, monthTwoDay, monthAct, monthTWOne, monthNWET, weekIntro, weekOneDay, weekTwoDay, weekAct, dayIntro, dayOneDay, dayTwoDay))
        startActivity(Intent(context, MainActivity::class.java))
    }
}
