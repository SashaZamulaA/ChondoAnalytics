package com.zamulaaleksandr.aleksandr.myapplication.ui.addGoal

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
import com.zamulaaleksandr.aleksandr.myapplication.*
import com.zamulaaleksandr.aleksandr.myapplication.MainActivity.Companion.AUTHOR_KEY
import com.zamulaaleksandr.aleksandr.myapplication.MainActivity.Companion.SPINNER
import com.zamulaaleksandr.aleksandr.myapplication.model.City
import com.zamulaaleksandr.aleksandr.myapplication.model.Goal
import com.zamulaaleksandr.aleksandr.myapplication.util.FirestoreUtil
import kotlinx.android.synthetic.main.fragment_add_goal.*
import kotlinx.android.synthetic.main.fragment_add_goal.view.*
import kotlinx.android.synthetic.main.fragment_add_result.*
import kotlinx.android.synthetic.main.fragment_add_result.view.*
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class GoalFragment : Fragment() {
    private val dialogDisposable = DialogCompositeDisposable()
    private var pictureJustChange = false
    val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("City/${FirebaseAuth.getInstance().uid
                ?: throw NullPointerException("UID is null.")}")

    private val noteRefCollection = firestoreInstance.collection("NewCity").document()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.zamulaaleksandr.aleksandr.myapplication.R.layout.fragment_add_goal, container, false)

        context?.showMaterialDialogNoYes(
                title = resources.getText(com.zamulaaleksandr.aleksandr.myapplication.R.string.change_goal).toString(),
                message = resources.getText(com.zamulaaleksandr.aleksandr.myapplication.R.string.change_team_goals).toString(),
                onNoClick = {
                    Navigation.findNavController(this.view!!).navigate(com.zamulaaleksandr.aleksandr.myapplication.R.id.commonResultFragment)
                },
                onYesClick = {

                }
        )?.addTo(dialogDisposable)



        rootView.button_save.setOnClickListener {
            addNote()
        }


        rootView.button_back_goal.setOnClickListener {
            Navigation.findNavController(it).navigate(com.zamulaaleksandr.aleksandr.myapplication.R.id.commonResultFragment)
        }
        return rootView
    }

    private fun addNote() {

        val yearIntro = goal_year_introduction_edittext.text.toString()
        val yearOneDay = goal_year_one_day_seminar_edittext.text.toString()
        val yearTwoDay = goal_year_two_day_seminar_edit_text.text.toString()
        val yearTWOne = goal_year_21day_edit_text.text.toString()
        val tearNWET = goal_year_two_day_seminar_edittext.text.toString()

        val monthIntro = goal_month_introduction_edittext.text.toString()
        val monthOneDay = goal_month_one_day_seminar_edittext.text.toString()
        val monthTwoDay = goal_month_two_day_seminar_edit_text.text.toString()
        val monthTWOne = goal_month_21day_edit_text.text.toString()
        val monthNWET = goal_month_team_edittext.text.toString()

        val weekIntro = goal_week_introduction_edittext.text.toString()
        val weekOneDay = goal_week_one_day_seminar_edittext.text.toString()
        val weekTwoDay = goal_week_two_day_seminar_edit_text.text.toString()
        val weekTWOne = goal_week_two_day_seminar_edit_text.text.toString()
        val weekNWET = goal_weak_team_edittext.text.toString()

        val dayIntro = goal_day_introduction_edittext.text.toString()
        val dayOneDay = goal_day_two_day_seminar_edit_text.text.toString()
        val dayTwoDay = goal_day_two_day_seminar_edit_text.text.toString()

        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val dataToSave = HashMap<String, Any>()
        val timestamp = System.currentTimeMillis()

        val id = noteRefCollection.id

        FirestoreUtil.currentUserDocRef.addSnapshotListener { documentSnapshot, _ ->
            FirestoreUtil.getCurrentUser { user ->
                if (documentSnapshot?.exists()!!) {

        noteRefCollection.set(Goal(currentUserId, yearIntro, yearOneDay, yearTwoDay, yearTWOne, tearNWET, monthIntro, monthOneDay, monthTwoDay, monthTWOne, monthNWET, weekIntro, weekOneDay,weekTwoDay, weekTWOne, weekNWET, dayIntro, dayOneDay, dayTwoDay))
                }
            }
        }
       startActivity(Intent(context, MainActivity::class.java))
    }
}
