package com.example.aleksandr.myapplication.ui.main

import android.app.Application
import android.view.View
import com.example.aleksandr.myapplication.AndroidApplication
import com.example.aleksandr.myapplication.BasePresenter
import com.example.aleksandr.myapplication.di.component.ApplicationComponent

class MainPresenter(mainView: MainActivity, applicationComponent: Application): BasePresenter<MainActivity>(mainView){
    init {


        (applicationComponent as AndroidApplication).applicationComponent.inject(this)}

    override fun onBindView() {
//        view?.loadKyivDay()
    }
fun day(){
//    view?.loadKyivDay()
}

    override fun onUnbindView() {

    }
}