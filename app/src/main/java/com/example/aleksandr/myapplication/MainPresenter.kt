package com.example.aleksandr.myapplication

import android.app.Application
import com.example.aleksandr.myapplication.BasePresenter
import com.example.aleksandr.myapplication.MainActivity


abstract class MainPresenter(mainView: MainActivity, applicationComponent: Application): BasePresenter<MainActivity>(mainView){
//    init {


//        (applicationComponent as AndroidApplication).applicationComponent.inject(this)}

//    override fun onBindView() {
//       }

//    override fun onUnbindView() {
//    }
}