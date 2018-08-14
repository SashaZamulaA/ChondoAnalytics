package com.example.aleksandr.myapplication.di.component

import com.example.aleksandr.myapplication.AndroidApplication
import com.example.aleksandr.myapplication.ui.info.InfoView
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [(ApplicationComponent::class)])
internal interface ViewComponent {

    fun inject(application: AndroidApplication)

    // ACTIVITY


    // VIEW
    fun inject(infoView: InfoView)
}