package com.zaleksandr.aleksandr.myapplication.ui.updateResult

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


class EditResultFragment : Fragment() {

    private val SELECT_IMAGE = 2
    var updateDateBirthday: String? = null
    var path = ""
    var name = ""
    var photo = ""
    var toolbar: Toolbar? = null
    val notesUpdateGuestRef = firestoreInstance.collection("NewCity")
    var city: Guest? = null
    private val dialogDisposable = DialogCompositeDisposable()
    private var pictureJustChange = false
    private lateinit var selectImageBytes: ByteArray

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_change_result, container, false)



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
                    update_result_time_center_edit_text2.text.toString(),
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

    private fun updateCurrentUser(centers: String,
                                  intro: String,
                                  oneDayWS: String,
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

        val userFieldMap = mutableMapOf<String, Any>()

        notesUpdateGuestRef.document(path).update(userFieldMap)
        notesUpdateGuestRef.document(path).update(
                "centers", centers,
                "intro", intro,
                "oneDayWS", oneDayWS,
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


    private fun setUpResult() {

        val getId = arguments?.getString("getId")


        notesUpdateGuestRef
                .whereEqualTo("getId", getId)
                .get().addOnCompleteListener { querydocumentSnapshot ->
                    if (querydocumentSnapshot.isSuccessful) {
                        for (documentSnapshot in querydocumentSnapshot.result!!) {

                            val note = documentSnapshot.toObject<City>(City::class.java)
                            update_introduction_edittext.setText(note.intro).toString()

                        }
                    }
                }
    }
}