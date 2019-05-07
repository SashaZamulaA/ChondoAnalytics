package com.zaleksandr.aleksandr.myapplication.ui.addResult

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.zaleksandr.aleksandr.myapplication.MainActivity
import com.zaleksandr.aleksandr.myapplication.MainActivity.Companion.AUTHOR_KEY
import com.zaleksandr.aleksandr.myapplication.MainActivity.Companion.SPINNER
import com.zaleksandr.aleksandr.myapplication.R
import com.zaleksandr.aleksandr.myapplication.model.City
import com.zaleksandr.aleksandr.myapplication.model.CityAddInfo
import com.zaleksandr.aleksandr.myapplication.model.EachCenter
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_result.*
import kotlinx.android.synthetic.main.fragment_add_result.view.*
import kotlinx.android.synthetic.main.fragment_nwet_guests.view.*
import java.text.DateFormat
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AddResultFragment : Fragment() {


    private var pictureJustChange = false
    val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("City/${FirebaseAuth.getInstance().uid
                ?: throw NullPointerException("UID is null.")}")

    var timestamp:Long = 0
    private val noteRefCommonCollection = firestoreInstance.collection("NewCity").document()
    private val noteRefAddCollection = firestoreInstance.collection("AnnInfo").document()
    private val noteRefCommonCollectionForEachCenter = firestoreInstance.collection("EachCenter").document()
    private val spinner_country = arrayOf(
            "Kyiv", "Kharkiv", "Dnepr", "Zhytomyr", "Lviv", "Odessa", "Chernigov", "Other"
    )

    private var category2: Array<String>? = null
    var calendarDate: Date? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_add_result, container, false)


        rootView.goal_description.setOnTouchListener(View.OnTouchListener { v, event ->
            if (goal_description.hasFocus()) {
                v.parent.requestDisallowInterceptTouchEvent(true)
                when (event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_SCROLL -> {
                        v.parent.requestDisallowInterceptTouchEvent(false)
                        return@OnTouchListener true
                    }
                }
            }
            false
        })

        val c = Calendar.getInstance()
        val currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.time)
        rootView.textViewDate.text = currentDateString


        rootView.button_back_from_result_to_common_res.setOnClickListener {
            Navigation.findNavController(this.view!!).navigate(R.id.action_addResultFragment_to_commonResultFragment)
        }
        category2 = resources.getStringArray(com.zaleksandr.aleksandr.myapplication.R.array.City)
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        val adapter2 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, category2)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinnerCountryAdapter = ArrayAdapter(context, com.zaleksandr.aleksandr.myapplication.R.layout.spinner_simple_item, spinner_country)
        spinnerCountryAdapter.setDropDownViewResource(com.zaleksandr.aleksandr.myapplication.R.layout.spinner_drop_down)
        rootView.registration_city.adapter = spinnerCountryAdapter
        rootView.registration_city.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }
        }

        rootView.buttonDate.setOnClickListener {
            showDatePickerDialog(this.context!!, String(), textViewDate)
        }

        rootView.result_button.setOnClickListener {
            addNote()
        }
        return rootView
    }


   private fun showDatePickerDialog(mContext: Context, format: String, textViewDate: TextView) {

        val c = Calendar.getInstance()
        DatePickerDialog(mContext, OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            c.set(year, monthOfYear, dayOfMonth)

            calendarDate = Date(c.timeInMillis)
            val currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.time)
            textViewDate.text = currentDateString
            timestamp = c.timeInMillis

        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)).show()


    }

    override fun onResume() {
        super.onResume()
        (this.activity!!.toolbar as Toolbar).visibility = View.GONE

    }

    private fun addNote() {

        val centers = registration_city.selectedItem.toString()
        val intro = introduction_edittext.text.toString()
        val onedayWS = one_day_seminar_edittext.text.toString()
        val twoDayWS = two_day_seminar_edittext.text.toString()
        val actionaiser = res_actionaiser_edittext.text.toString()
        val twOneDay = day21_seminar_edittext.text.toString()
        val approach = result_approach_edit_text.text.toString()
        val telCont = result_add_telephot.text.toString()
        val timeCenter = result_time_center_edit_text2.text.toString()
        val timeStr = result_time_street_edit_text.text.toString()
        val lectTraining = result_add_lect_training.text.toString()
        val lectOnStr = result_lectures_on_street_edittext.text.toString()
        val lectCentr = result_lectures_in_center_edittext.text.toString()
        val nwet = result_nwet_edittext.text.toString()
        val mmbk = result_mmbk_edittext.text.toString()
        val eduMat = result_edu_mat_edittext.text.toString()
        val dp = result_DP_edittext.text.toString()
        val dpKor = result_DP_kor_edittext.text.toString()
        val mobilis = result_mobilisation_edittext.text.toString()
        val descriptionGoal = goal_description.text.toString()
        val hdh = result_hdh.text.toString()
        val gradeIntGoal = result_internal_goal.text.toString()
        val date: Date

        if (calendarDate == null) {
            val c = Calendar.getInstance()
            val currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.time)
            textViewDate.text = currentDateString
            date = Date(c.timeInMillis)
            timestamp = System.currentTimeMillis()
        } else {
            date = calendarDate as Date
        }


        var userPhotoPath = ""
        var name = ""

        val id = FirebaseAuth.getInstance().currentUser!!.uid
        val dataToSave = HashMap<String, Any>()
        dataToSave[SPINNER] = centers
//        val timestamp = System.currentTimeMillis()

        val getId = noteRefCommonCollection.id

        FirestoreUtil.currentUserDocRef.addSnapshotListener { documentSnapshot, _ ->
            FirestoreUtil.getCurrentUser { user ->
                if (documentSnapshot?.exists()!! && centers != "Other") {
                    name = documentSnapshot.getString(AUTHOR_KEY) ?: ""
                    if (!pictureJustChange && user.profilePicturePath != null) {
                        userPhotoPath = user.profilePicturePath
                    }

                    noteRefCommonCollection.set(City(getId, id, intro, onedayWS, twoDayWS, actionaiser, twOneDay, centers, approach, telCont, timeCenter, timeStr, lectTraining, lectOnStr, lectCentr, date, timestamp, userPhotoPath, name, nwet, dpKor, dp, mmbk, mobilis,descriptionGoal, hdh, eduMat, gradeIntGoal))
                    noteRefCommonCollectionForEachCenter.set(EachCenter(id, id, intro, onedayWS, twoDayWS, actionaiser, twOneDay, centers, date, timestamp, userPhotoPath, name, nwet, mmbk))

                } else {
                    noteRefAddCollection.set(CityAddInfo(getId, id, date, timestamp, dpKor, dp, mobilis))
                }
            }
        }
        dataToSave[SPINNER] = centers
        startActivity(Intent(context, MainActivity::class.java))
    }
}