package com.example.aleksandr.myapplication.di.component

import com.example.aleksandr.myapplication.AndroidApplication
import com.example.aleksandr.myapplication.di.module.AppModule
import com.example.aleksandr.myapplication.di.module.NetModule
import com.example.aleksandr.myapplication.ui.info.InfoPresenter
import com.example.aleksandr.myapplication.ui.info.InfoView
import com.example.aleksandr.myapplication.ui.main.MainActivity
import com.example.aleksandr.myapplication.ui.main.MainPresenter
import dagger.Component

@Component(modules = [AppModule::class, NetModule::class, NetModule::class])
interface ApplicationComponent {
    fun inject(modApplication: AndroidApplication)

    // VIEW
    fun inject(infoView: InfoView)
    fun inject(mainView: MainActivity)

    // PRESENTER
    fun inject(infoPresenter: InfoPresenter)
    fun inject(mainPresenter: MainPresenter)
}