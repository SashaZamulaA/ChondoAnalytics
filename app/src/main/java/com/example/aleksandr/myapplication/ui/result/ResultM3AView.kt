package com.example.aleksandr.myapplication.ui.result

import android.os.Bundle
import com.example.aleksandr.myapplication.BaseActivity
import com.example.aleksandr.myapplication.R

class ResultM3AView : BaseActivity(), IResultM3AView{

    private lateinit var presenter: ResultM3APresenter

    override fun init(savedInstantState:Bundle?){
        super.setContentView(R.layout.view_m3a_result)
        presenter = ResultM3APresenter(this, application)
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

}