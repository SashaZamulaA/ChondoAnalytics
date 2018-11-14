package com.example.aleksandr.myapplication.ui.result

import android.os.Bundle
import com.example.aleksandr.myapplication.BaseActivity
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.ui.settings.SettingsView.Companion.AUTHOR_KEY
import com.example.aleksandr.myapplication.ui.settings.SettingsView.Companion.QUOTE_KEY
import com.example.aleksandr.myapplication.ui.settings.SettingsView.Companion.SPINNER
import com.example.aleksandr.myapplication.util.FirestoreUtil
import kotlinx.android.synthetic.main.view_m3a_result.*

class ResultM3AView : BaseActivity(), IResultM3AView{

    private lateinit var presenter: ResultM3APresenter

    override fun init(savedInstantState:Bundle?){
        super.setContentView(R.layout.view_m3a_result)
        presenter = ResultM3APresenter(this, application)

    saveQuote()
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

        if (intro.isEmpty() || oneDayWS.isEmpty()) {
            return
        }
        val dataToSave = HashMap<String, Any>()
        dataToSave[INRO] = intro
        dataToSave[ONEDAYWS] = oneDayWS

        FirestoreUtil.currentUserDocRef.set(dataToSave).addOnSuccessListener {
        }
    }
    companion object {
        val INRO = "intriduction"
        val ONEDAYWS = "oneDayWS"
        
    }

}