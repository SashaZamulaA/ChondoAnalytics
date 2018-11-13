package com.example.aleksandr.myapplication.ui.result

import android.app.Application
import com.example.aleksandr.myapplication.AndroidApplication
import com.example.aleksandr.myapplication.BasePresenter

class ResultM3APresenter(infoView: ResultM3AView, applicationComponent: Application) :BasePresenter<ResultM3AView>(infoView) {
init {
    (applicationComponent as AndroidApplication).applicationComponent.inject(this)

}
    override fun onBindView() {
        view?.setButtonVisibility(true)
    }

    override fun onUnbindView() {}


}