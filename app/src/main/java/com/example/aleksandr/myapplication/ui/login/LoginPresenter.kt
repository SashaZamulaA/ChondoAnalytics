package com.example.aleksandr.myapplication.ui.login

import android.app.Application
import com.example.aleksandr.myapplication.AndroidApplication
import com.example.aleksandr.myapplication.BasePresenter
import com.example.aleksandr.myapplication.ui.main.MainActivity

class LoginPresenter(loginView: LoginActivity, applicationComponent: Application): BasePresenter<LoginActivity>(loginView){
    init {


        (applicationComponent as AndroidApplication).applicationComponent.inject(this)}

    override fun onBindView() {

    }

    override fun onUnbindView() {

    }
}