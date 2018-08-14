package com.example.aleksandr.myapplication.ui.info

import android.app.Application
import com.example.aleksandr.myapplication.AndroidApplication
import com.example.aleksandr.myapplication.BasePresenter

class InfoPresenter(infoView: InfoView, applicationComponent: Application) :BasePresenter<InfoView>(infoView) {
init {
    (applicationComponent as AndroidApplication).applicationComponent.inject(this)

}
    override fun onBindView() {
        view?.setButtonVisibility(true)
    }

    override fun onUnbindView() {}


}