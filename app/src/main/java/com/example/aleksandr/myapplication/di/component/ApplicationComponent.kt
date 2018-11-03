package com.example.aleksandr.myapplication.di.component

import com.example.aleksandr.myapplication.AndroidApplication
import com.example.aleksandr.myapplication.di.module.AppModule
import com.example.aleksandr.myapplication.di.module.NetModule
import com.example.aleksandr.myapplication.ui.add_task.NewNoteView
import com.example.aleksandr.myapplication.ui.add_task.NewNotePresenter
import com.example.aleksandr.myapplication.ui.add_task.RecyclerActivity
import com.example.aleksandr.myapplication.ui.hdh.HDHPresenter
import com.example.aleksandr.myapplication.ui.hdh.HDHView
import com.example.aleksandr.myapplication.ui.info.InfoPresenter
import com.example.aleksandr.myapplication.ui.info.InfoView
import com.example.aleksandr.myapplication.ui.login.LoginActivity
import com.example.aleksandr.myapplication.ui.login.RegistrationActivity
import com.example.aleksandr.myapplication.ui.login.presenter.LoginPresenter
import com.example.aleksandr.myapplication.ui.login.presenter.RegistrationPresenter
import com.example.aleksandr.myapplication.ui.main.MainActivity
import com.example.aleksandr.myapplication.ui.main.MainPresenter
import com.example.aleksandr.myapplication.ui.settings.SettingsView
import com.vadym.adv.myhomepet.ui.settings.SettingsPresenter
import dagger.Component

@Component(modules = [AppModule::class, NetModule::class, NetModule::class])
interface ApplicationComponent {
    fun inject(modApplication: AndroidApplication)

    // VIEW
    fun inject(infoView: InfoView)
    fun inject(mainView: MainActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(registrationActivity: RegistrationActivity)
    fun inject(hdhView: HDHView)
    fun inject(recView :RecyclerActivity)
    fun inject(newNoteView : NewNoteView)
    fun inject( settingsView: SettingsView)


    // PRESENTER
    fun inject(infoPresenter: InfoPresenter)
    fun inject(mainPresenter: MainPresenter)
    fun inject(loginPresenter: LoginPresenter)
    fun inject(registrationPresenter: RegistrationPresenter)
    fun inject(hdhPresenter: HDHPresenter)
    fun inject(newNotePresenter: NewNotePresenter)
    fun inject(settingsPresenter: SettingsPresenter)
}