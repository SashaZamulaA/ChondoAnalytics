//package com.example.aleksandr.myapplication.ui.chondo_result
//
//import android.app.Application
//import com.example.aleksandr.myapplication.AndroidApplication
//import com.example.aleksandr.myapplication.BasePresenter
//
//class ResultM3APresenter(infoView: ResultM3AView, applicationComponent: Application) :BasePresenter<ResultM3AView>(infoView) {
//
//    private var address = ""
//
//    init {
//    (applicationComponent as AndroidApplication).applicationComponent.inject(this)
//
//}
//
//    fun onUpdateOwnerAddress(address: String) {
//        this.address = address
//    }
//    override fun onBindView() {
//        view?.setButtonVisibility(true)
//    }
//
//    override fun onUnbindView() {}
//
//
//}