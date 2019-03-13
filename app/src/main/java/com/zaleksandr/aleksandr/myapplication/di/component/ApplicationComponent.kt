package com.zaleksandr.aleksandr.myapplication.di.component

import com.zaleksandr.aleksandr.myapplication.AndroidApplication
import com.zaleksandr.aleksandr.myapplication.di.module.AppModule
import com.zaleksandr.aleksandr.myapplication.di.module.NetModule
import com.zaleksandr.aleksandr.myapplication.ui.login.LoginActivity
import com.zaleksandr.aleksandr.myapplication.ui.login.RegistrationActivity
import com.zaleksandr.aleksandr.myapplication.ui.login.presenter.LoginPresenter
import com.zaleksandr.aleksandr.myapplication.ui.login.presenter.RegistrationPresenter
import com.zaleksandr.aleksandr.myapplication.MainActivity
import com.zaleksandr.aleksandr.myapplication.MainPresenter
import dagger.Component

@Component(modules = [AppModule::class, NetModule::class, NetModule::class])
interface ApplicationComponent {
    fun inject(modApplication: AndroidApplication)

    // VIEW
//    fun inject(infoView: ResultM3AView)
    fun inject(mainView: MainActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(registrationActivity: RegistrationActivity)

    // PRESENTER
//    fun inject(infoPresenter: ResultM3APresenter)
    fun inject(mainView: MainPresenter)
    fun inject(loginPresenter: LoginPresenter)
    fun inject(registrationPresenter: RegistrationPresenter)
  }