package com.zamulaaleksandr.aleksandr.myapplication.di.component

import com.zamulaaleksandr.aleksandr.myapplication.AndroidApplication
import com.zamulaaleksandr.aleksandr.myapplication.di.module.AppModule
import com.zamulaaleksandr.aleksandr.myapplication.di.module.NetModule
import com.zamulaaleksandr.aleksandr.myapplication.ui.login.LoginActivity
import com.zamulaaleksandr.aleksandr.myapplication.ui.login.RegistrationActivity
import com.zamulaaleksandr.aleksandr.myapplication.ui.login.presenter.LoginPresenter
import com.zamulaaleksandr.aleksandr.myapplication.ui.login.presenter.RegistrationPresenter
import com.zamulaaleksandr.aleksandr.myapplication.MainActivity
import com.zamulaaleksandr.aleksandr.myapplication.ui.settings.SettingsView
import com.vadym.adv.myhomepet.ui.settings.SettingsPresenter
import dagger.Component

@Component(modules = [AppModule::class, NetModule::class, NetModule::class])
interface ApplicationComponent {
    fun inject(modApplication: AndroidApplication)

    // VIEW
//    fun inject(infoView: ResultM3AView)
    fun inject(mainView: MainActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(registrationActivity: RegistrationActivity)
    fun inject( settingsView: SettingsView)


    // PRESENTER
//    fun inject(infoPresenter: ResultM3APresenter)
    fun inject(loginPresenter: LoginPresenter)
    fun inject(registrationPresenter: RegistrationPresenter)
    fun inject(settingsPresenter: SettingsPresenter)
}