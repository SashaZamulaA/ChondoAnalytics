package com.zaleksandr.aleksandr.myapplication.ui.updateResult

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.zaleksandr.aleksandr.myapplication.DialogCompositeDisposable
import com.zaleksandr.aleksandr.myapplication.MainActivity
import com.zaleksandr.aleksandr.myapplication.R
import com.zaleksandr.aleksandr.myapplication.model.City
import com.zaleksandr.aleksandr.myapplication.model.Guest
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_change_result.*
import kotlinx.android.synthetic.main.fragment_change_result.view.*
import java.text.DateFormat
import java.util.*


class EditResultFragment : Fragment() {

    var path = ""
    var name = ""
    var toolbar: Toolbar? = null
    val notesUpdateGuestRef = firestoreInstance.collection("NewCity")
    var city: Guest? = null
    private val dialogDisposable = DialogCompositeDisposable()
    private var pictureJustChange = false
    private lateinit var selectImageBytes: ByteArray
    private val spinner_country = arrayOf(
            "Kyiv", "Kharkiv", "Dnepr", "Zhytomyr", "Lviv", "Odessa", "Chernigov", "Other"
    )

    private var category2: Array<String>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_change_result, container, false)


//        rootView.update_buttonDate.setOnClickListener {
//            showDatePickerBirthdayDialog(this.context!!, String(), update_date)
//        }


        category2 = resources.getStringArray(com.zaleksandr.aleksandr.myapplication.R.array.City)
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        val adapter2 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, category2)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinnerCountryAdapter = ArrayAdapter(context, R.layout.spinner_simple_item, spinner_country)
        spinnerCountryAdapter.setDropDownViewResource(R.layout.spinner_drop_down)
        rootView.update_registration_city.adapter = spinnerCountryAdapter
        rootView.update_registration_city.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }
        }

        rootView.button_back_from_change_res.setOnClickListener {
            Navigation.findNavController(this.view!!).navigate(R.id.action_changeMyResultFragment_to_see_7_day_my_resultFragment)
        }
        rootView.change_result_button.setOnClickListener {

            setUpResult()
            updateCurrentUser(

                    update_registration_city.selectedItem.toString(),
                    update_introduction_edittext.text.toString(),
                    update_one_day_seminar_edittext.text.toString(),
                    update_two_day_seminar_edittext.text.toString(),
                    update_res_actionaiser_edittext.text.toString(),
                    update_day21_seminar_edittext.text.toString(),
                    update_result_approach_edit_text.text.toString(),
                    update_result_add_telephot.text.toString(),
                    update_result_time_center.text.toString(),
                    update_result_time_street_edit_text.text.toString(),
                    update_result_add_lect_training.text.toString(),
                    update_result_lectures_on_street_edittext.text.toString(),
                    update_result_lectures_in_center_edittext.text.toString(),
                    update_result_nwet_edittext.text.toString(),
                    update_result_mmbk_edittext.text.toString(),
                    update_result_edu_mat_edittext.text.toString(),
                    update_result_DP_edittext.text.toString(),
                    update_result_DP_kor_edittext.text.toString(),
                    update_result_mobilisation_edittext.text.toString(),
                    update_result_hdh.text.toString(),
                    update_result_internal_goal.text.toString()
            )

        }

        setUpResult()

        return rootView
    }

    override fun onResume() {
        super.onResume()
        (this.activity!!.toolbar as Toolbar).visibility = View.GONE

    }

    private fun updateCurrentUser(

            centers: String,
            intro: String,
            onedayWS: String,
            twoDayWS: String,
            actionaiser: String,
            twOneDay: String,
            approach: String,
            telCont: String,
            timeStr: String,
            timeCenter: String,
            lectTraining: String,
            lectOnStr: String,
            lectCentr: String,
            nwet: String,
            mmbk: String,
            eduMat: String,
            dp: String,
            dpKor: String,
            mobilis: String,
            hdh: String,
            gradeIntGoal: String

    ) {


        notesUpdateGuestRef.document(path).update(

                "centers", centers,
                "intro", intro,
                "onedayWS", onedayWS,
                "twoDayWS", twoDayWS,
                "actionaiser", actionaiser,
                "twOneDay", twOneDay,
                "approach", approach,
                "telCont", telCont,
                "timeStr", timeStr,
                "timeCenter", timeCenter,
                "lectTraining", lectTraining,
                "lectOnStr", lectOnStr,
                "lectCentr", lectCentr,
                "nwet", nwet,
                "mmbk", mmbk,
                "eduMat", eduMat,
                "dp", dp,
                "dpKor", dpKor,
                "mobilis", mobilis,
                "hdh", hdh,
                "gradeIntGoal", gradeIntGoal
        )

        startActivity(Intent(context, MainActivity::class.java))
    }

//    fun showDatePickerBirthdayDialog(mContext: Context, format: String, update_buttonDate: TextView) {
//
//        val c = Calendar.getInstance()
//        DatePickerDialog(mContext, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
//            c.set(year, monthOfYear, dayOfMonth)
//
//            val currentDateString = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.time)
//            update_buttonDate.text = currentDateString
//
//
//        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
//                c.get(Calendar.DAY_OF_MONTH)).show()
//
//
//    }



    private fun setUpResult() {

        val getId = arguments?.getString("getId")


        notesUpdateGuestRef
                .whereEqualTo("getId", getId)
                .get().addOnCompleteListener { querydocumentSnapshot ->
                    if (querydocumentSnapshot.isSuccessful) {
                        for (documentSnapshot in querydocumentSnapshot.result!!) {

                            val note = documentSnapshot.toObject<City>(City::class.java)

                            path = note.getId.toString()
                            update_result_internal_goal.setText(note.gradeIntGoal)
                            update_introduction_edittext.setText(note.intro)
                            update_result_time_street_edit_text.setText(note.timeStr)
                            update_result_approach_edit_text.setText(note.approach)
                            update_result_add_telephot.setText(note.telCont)
                            update_one_day_seminar_edittext.setText(note.onedayWS)
                            update_two_day_seminar_edittext.setText(note.twoDayWS)
                            update_res_actionaiser_edittext.setText(note.actionaiser)
                            update_day21_seminar_edittext.setText(note.twOneDay)
                            update_result_nwet_edittext.setText(note.nwet)
                            update_result_time_center.setText(note.timeCenter)
                            update_result_add_lect_training.setText(note.lectTraining)
                            update_result_lectures_on_street_edittext.setText(note.lectOnStr)
                            update_result_lectures_in_center_edittext.setText(note.lectCentr)
                            update_result_edu_mat_edittext.setText(note.eduMat)
                            update_result_DP_kor_edittext.setText(note.dpKor)
                            update_result_DP_edittext.setText(note.dp)
                            update_result_mmbk_edittext.setText(note.mmbk)

                        }
                    }
                }
    }
}