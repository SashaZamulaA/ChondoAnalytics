//package com.example.aleksandr.myapplication.ui.chondo_result
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.View
//import android.widget.AdapterView
//import android.widget.ArrayAdapter
//import android.widget.Spinner
//import com.example.aleksandr.myapplication.BaseActivity
//import com.example.aleksandr.myapplication.R
//import com.example.aleksandr.myapplication.model.City
//import com.example.aleksandr.myapplication.model.User
//import com.example.aleksandr.myapplication.ui.main.MainActivity
//import com.example.aleksandr.myapplication.ui.settings.SettingsView.Companion.SPINNER
//import com.example.aleksandr.myapplication.util.FirestoreUtil
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.DocumentReference
//import com.google.firebase.firestore.FirebaseFirestore
//import kotlinx.android.synthetic.main.view_m3a_result.*
//import java.util.*
//
//
//class ResultM3AView : BaseActivity(), IResultM3AView {
//
//    private lateinit var presenter: ResultM3APresenter
//    val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
//    val currentUserDocRef: DocumentReference
//        get() = firestoreInstance.document("City/${FirebaseAuth.getInstance().uid
//                ?: throw NullPointerException("UID is null.")}")
//
//    private val noteRefCollection = firestoreInstance.collection("NewCity")
//
//    private val spinner_country = arrayOf(
//            "Kyiv", "Kharkiv", "Dnepr", "Zhytomyr", "Lviv", "Odessa", "Chernigov"
//    )
//
//    private var category2: Array<String>? = null
//
//    override fun init(savedInstantState: Bundle?) {
//        super.setContentView(R.layout.view_m3a_result)
//        presenter = ResultM3APresenter(this, application)
//
//        category2 = resources.getStringArray(R.array.City)
//        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
//        val adapter2 = ArrayAdapter(this,
//                android.R.layout.simple_dropdown_item_1line, category2)
//        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//
//
//
//        val spinnerCountryAdapter = ArrayAdapter(this, R.layout.spinner_simple_item, spinner_country)
//        spinnerCountryAdapter.setDropDownViewResource(R.layout.spinner_drop_down)
//        registration_city.adapter = spinnerCountryAdapter
//        registration_city.setSelection(getIndex(registration_city, "Lviv"))
//        registration_city.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(parent: AdapterView<*>?) {}
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                presenter.onUpdateOwnerAddress(spinner_country[position])
//
//            }
//        }
//
//        result_fab_confirm_goal.setOnClickListener {
//            addNote()
//        }
//    }
//
//    private fun getIndex(spinner: Spinner, myString: String): Int {
//
//        var index = 0
//
//        for (i in 0 until spinner.count) {
//            if (spinner.getItemAtPosition(i) == myString) {
//                index = i
//            }
//        }
//        return index
//    }
//
//    override fun onAttachedToWindow() {
//        super.onAttachedToWindow()
//        presenter.bindView(this)
//    }
//
//    override fun onDetachedFromWindow() {
//        super.onDetachedFromWindow()
//        presenter.unbindView(this)
//    }
//
//    override fun setButtonVisibility(isVisible: Boolean) {
////        btn_info.visibility = isVisible.toAndroidVisibility()
//    }
//
//
//    private fun addNote() {
//        val centers = registration_city.selectedItem.toString()
//        val intro = introduction_edittext.text.toString()
//        val oneDayWS = one_day_seminar_edittext.text.toString()
//        val twoDayWS = two_day_seminar_edittext.text.toString()
//        val twOneDay = day21_seminar_edittext.text.toString()
//        val approach = result_time_street_edit_text.text.toString()
//        val timeStr = result_approach_edit_text.text.toString()
//        val lectOnStr = result_lectures_on_street_edittext.text.toString()
//        val lectCentr = result_lectures_in_center_edittext.text.toString()
//
//
//        val user: User? = null
//        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
//        val dataToSave = HashMap<String, Any>()
////        dataToSave[INRO] = intro
////        dataToSave[ONEDAYWS] = oneDayWS
//
//        dataToSave[SPINNER] = centers
//        val filename = UUID.randomUUID().toString()
//        FirestoreUtil.currentUserDocRef.collection("City")
//                .document(centers).collection(filename).document()
//                .set(City(intro, oneDayWS, twoDayWS, twOneDay, centers, approach, timeStr, lectOnStr, lectCentr, Date()))
//
//        noteRefCollection.add(City(intro, oneDayWS, twoDayWS, twOneDay, centers, approach, timeStr, lectOnStr, lectCentr, Date()))
//
//        firestoreInstance.collection("City")
//                .document(centers).collection(centers).document()
//                .set(City(intro, oneDayWS, twoDayWS, twOneDay, centers, approach, timeStr, lectOnStr, lectCentr, Date()))
//
//        startActivity(Intent(this@ResultM3AView, MainActivity::class.java))
//        finish()
//    }
//
//    companion object {
//        val INRO = "intriduction"
//        val ONEDAYWS = "oneDayWS"
//
//    }
//}