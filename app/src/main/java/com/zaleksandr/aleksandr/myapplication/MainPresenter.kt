package com.zaleksandr.aleksandr.myapplication

import android.app.Application


class MainPresenter(mainView: MainActivity, application: Application): BasePresenter<MainActivity>(mainView){
    init {

                (application as AndroidApplication).applicationComponent.inject(this)


    }

    override fun onBindView() {

       }

    override fun onUnbindView() {
    }
}