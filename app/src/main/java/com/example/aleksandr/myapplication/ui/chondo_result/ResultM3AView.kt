package com.example.aleksandr.myapplication.ui.chondo_result

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.aleksandr.myapplication.BaseActivity
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.model.City
import com.example.aleksandr.myapplication.model.User
import com.example.aleksandr.myapplication.ui.settings.SettingsView.Companion.SPINNER
import com.example.aleksandr.myapplication.util.FirestoreUtil
import com.example.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.view_m3a_result.*
import java.util.*




class ResultM3AView : BaseActivity(), IResultM3AView{

    private lateinit var presenter: ResultM3APresenter
    private val chatChannelsCollectionRef = firestoreInstance.collection("City")
    private val spinner_country = arrayOf(
            "Kyiv", "Kharkiv", "Dnepr", "Zhytomyr", "Lviv", "Odessa", "Chernigov"
    )

    override fun init(savedInstantState:Bundle?){
        super.setContentView(R.layout.view_m3a_result)
        presenter = ResultM3APresenter(this, application)

        val spinnerCountryAdapter = ArrayAdapter(this, R.layout.spinner_simple_item, spinner_country)
        spinnerCountryAdapter.setDropDownViewResource(R.layout.spinner_drop_down)
        registration_city.adapter = spinnerCountryAdapter
        registration_city.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                presenter.onUpdateOwnerAddress(spinner_country[position])
            }
        }

        fab_confirm_goal.setOnClickListener {
            saveQuote()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter.bindView(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.unbindView(this)
    }

    override fun setButtonVisibility(isVisible: Boolean) {
//        btn_info.visibility = isVisible.toAndroidVisibility()
    }


    private fun saveQuote() {
        val intro = introduction_edittext.text.toString()
        val oneDayWS = one_day_seminar_edittext.text.toString()
        val category = registration_city.selectedItem.toString()
        if (intro.isEmpty() || oneDayWS.isEmpty()) {
            return
        }
        val user:User? = null
        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val dataToSave = HashMap<String, Any>()
//        dataToSave[INRO] = intro
//        dataToSave[ONEDAYWS] = oneDayWS

        dataToSave[SPINNER] = category

        FirestoreUtil.currentUserDocRef
                .collection("City")
                .document(category)
                .set(City(intro, oneDayWS))
    }
    companion object {
        val INRO = "intriduction"
        val ONEDAYWS = "oneDayWS"

    }

}