package com.example.aleksandr.myapplication.ui.addResult

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.aleksandr.myapplication.R
import androidx.fragment.app.Fragment
import com.example.aleksandr.myapplication.MainActivity
import com.example.aleksandr.myapplication.model.City
import com.example.aleksandr.myapplication.model.User
import com.example.aleksandr.myapplication.ui.settings.SettingsView.Companion.SPINNER
import com.example.aleksandr.myapplication.util.FirestoreUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.first_fragment.*
import kotlinx.android.synthetic.main.first_fragment.view.*
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AddResultFragment : Fragment() {

    val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("City/${FirebaseAuth.getInstance().uid
                ?: throw NullPointerException("UID is null.")}")

    private val noteRefCollection = firestoreInstance.collection("NewCity")

    private val spinner_country = arrayOf(
            "Kyiv", "Kharkiv", "Dnepr", "Zhytomyr", "Lviv", "Odessa", "Chernigov"
    )

    private var category2: Array<String>? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.example.aleksandr.myapplication.R.layout.first_fragment, container, false)

        category2 = resources.getStringArray(R.array.City)
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        val adapter2 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, category2)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinnerCountryAdapter = ArrayAdapter(context, R.layout.spinner_simple_item, spinner_country)
        spinnerCountryAdapter.setDropDownViewResource(R.layout.spinner_drop_down)
        rootView.registration_city.adapter = spinnerCountryAdapter
        rootView.registration_city.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }
        }

        rootView.result_fab_confirm_goal.setOnClickListener {
            addNote(rootView)
        }
        return rootView
    }

    private fun addNote(rootView: View) {
        val centers = registration_city.selectedItem.toString()
        val intro = introduction_edittext.text.toString()
        val oneDayWS = one_day_seminar_edittext.text.toString()
        val twoDayWS = two_day_seminar_edittext.text.toString()
        val twOneDay = day21_seminar_edittext.text.toString()
        val approach = result_time_street_edit_text.text.toString()
        val timeStr = result_approach_edit_text.text.toString()
        val lectOnStr = result_lectures_on_street_edittext.text.toString()
        val lectCentr = result_lectures_in_center_edittext.text.toString()

//        val user: User? = null
        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val dataToSave = HashMap<String, Any>()
//        dataToSave[INRO] = intro
//        dataToSave[ONEDAYWS] = oneDayWS

        dataToSave[SPINNER] = centers
        val filename = UUID.randomUUID().toString()
//        FirestoreUtil.currentUserDocRef.collection("City")
//                .document(centers).collection(filename).document()
//                .set(City(currentUserId, intro, oneDayWS, twoDayWS, twOneDay, centers, approach, timeStr, lectOnStr, lectCentr, Date()))

        noteRefCollection.add(City(currentUserId, intro, oneDayWS, twoDayWS, twOneDay, centers, approach, timeStr, lectOnStr, lectCentr, Date()))

//        firestoreInstance.collection("City")
//                .document(centers).collection(centers).document()
//                .set(City(currentUserId, intro, oneDayWS, twoDayWS, twOneDay, centers, approach, timeStr, lectOnStr, lectCentr, Date()))

        startActivity(Intent(context, MainActivity::class.java))

    }
}
