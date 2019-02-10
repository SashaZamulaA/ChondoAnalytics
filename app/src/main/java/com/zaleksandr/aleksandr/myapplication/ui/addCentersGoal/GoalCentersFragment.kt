package com.zaleksandr.aleksandr.myapplication.ui.addCentersGoal

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.zaleksandr.aleksandr.myapplication.*
import com.zaleksandr.aleksandr.myapplication.model.Goal
import com.zaleksandr.aleksandr.myapplication.model.GoalCenter
import kotlinx.android.synthetic.main.fragment_add_centers_goal.*
import kotlinx.android.synthetic.main.fragment_add_centers_goal.view.*
import kotlinx.android.synthetic.main.fragment_add_goal.*
import kotlinx.android.synthetic.main.fragment_add_goal.view.*
import kotlinx.android.synthetic.main.fragment_add_result2.*
import kotlinx.android.synthetic.main.fragment_add_result2.view.*
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class GoalCentersFragment : Fragment() {
    private val dialogDisposable = DialogCompositeDisposable()
    val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    val firestoreInstanceGoal: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("City/${FirebaseAuth.getInstance().uid
                ?: throw NullPointerException("UID is null.")}")


    companion object {
        val SPINNER = "spinner"
    }

    private val spinner_country = arrayOf(
            "Kyiv", "Kharkiv", "Dnepr", "Zhytomyr", "Lviv", "Odessa", "Chernigov"
    )

    private var category2: Array<String>? = null
    private val goleCenterRefCollection = firestoreInstance.collection("GoalCenters").document()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.zaleksandr.aleksandr.myapplication.R.layout.fragment_add_centers_goal, container, false)

        category2 = resources.getStringArray(R.array.City)
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        val adapter2 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, category2)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinnerCountryAdapter = ArrayAdapter(context, R.layout.spinner_simple_item, spinner_country)
        spinnerCountryAdapter.setDropDownViewResource(R.layout.spinner_drop_down)
        rootView.registration_city_each_center.adapter = spinnerCountryAdapter
        rootView.registration_city_each_center.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }
        }


        context?.showMaterialDialogNoYes(
                title = resources.getText(com.zaleksandr.aleksandr.myapplication.R.string.change_center_goal).toString(),
                message = resources.getText(com.zaleksandr.aleksandr.myapplication.R.string.change_center_team_goals).toString(),
                onNoClick = {
                    Navigation.findNavController(this.view!!).navigate(com.zaleksandr.aleksandr.myapplication.R.id.commonResultFragment)
                },
                onYesClick = {
                }
        )?.addTo(dialogDisposable)



        rootView.button_save_each_center.setOnClickListener {
            addNote()
        }


        rootView.button_back_goal_each_center.setOnClickListener {
            Navigation.findNavController(it).navigate(com.zaleksandr.aleksandr.myapplication.R.id.commonResultFragment)
        }
        return rootView
    }

    private fun addNote() {

        val center = registration_city_each_center.selectedItem.toString()
        val yearIntroCenter = goal_year_introduction_edittext_each_center.text.toString()
        val yearOneDayCenter = goal_year_one_day_seminar_edittext_each_center.text.toString()
        val yearTwoDayCenter = goal_year_two_day_seminar_edit_text_each_center.text.toString()
        val yearTWOneCenter = goal_year_21day_edit_text_each_center.text.toString()
        val tearNWETCenter = goal_year_two_day_seminar_edittext_each_center.text.toString()

        val monthIntroCenter = goal_month_introduction_edittext_each_center.text.toString()
        val monthOneDayCenter = goal_month_one_day_seminar_edittext_each_center.text.toString()
        val monthTwoDayCenter = goal_month_two_day_seminar_edit_text_each_center.text.toString()
        val monthActionCenter = goal_month_action_edit_text_each_center.text.toString()


        val weekIntroCenter = goal_week_introduction_edittext_each_center.text.toString()
        val weekOneDayCenter = goal_week_one_day_seminar_edittext_each_center.text.toString()
        val weekTwoDayCenter = goal_week_two_day_seminar_edit_text_each_center.text.toString()

        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid

        val dataToSave = HashMap<String, Any>()
        dataToSave[SPINNER] = center
        val timestamp = System.currentTimeMillis()

        val id = goleCenterRefCollection.id

//        goalRefDocumentCurrent.set(Goal(currentUserId, yearIntro, yearOneDay, yearTwoDay, yearTWOne, tearNWET, monthIntro, monthOneDay, monthTwoDay, monthTWOne, monthNWET, weekIntro, weekOneDay, weekTwoDay, weekTWOne, weekNWET, dayIntro, dayOneDay, dayTwoDay))
//        startActivity(Intent(context, MainActivity::class.java))

        goleCenterRefCollection.set(GoalCenter(
                center,
                currentUserId,
                yearIntroCenter,
                yearOneDayCenter,
                yearTwoDayCenter,
                yearTWOneCenter,
                tearNWETCenter,
                monthIntroCenter,
                monthOneDayCenter,
                monthTwoDayCenter,
                monthActionCenter,
                weekIntroCenter,
                weekOneDayCenter,
                weekTwoDayCenter
                ))
        startActivity(Intent(context, MainActivity::class.java))
    }
}