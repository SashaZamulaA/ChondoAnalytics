package com.example.aleksandr.myapplication.ui.hdh

import android.app.Application
import com.example.aleksandr.myapplication.AndroidApplication
import com.example.aleksandr.myapplication.BasePresenter
import com.example.aleksandr.myapplication.ui.info.InfoView

class HDHPresenter(hdhView: HDHView, applicationComponent: Application) : BasePresenter<HDHView>(hdhView) {
    init {
        (applicationComponent as AndroidApplication).applicationComponent.inject(this)

    }
    override fun onBindView() {
        view?.setButtonVisibility(true)
    }

    override fun onUnbindView() {}


}