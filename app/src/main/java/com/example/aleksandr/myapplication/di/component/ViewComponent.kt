package com.example.aleksandr.myapplication.di.component

import com.example.aleksandr.myapplication.AndroidApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [(ApplicationComponent::class)])
internal interface ViewComponent {

    fun inject(application: AndroidApplication)
//    fun inject(infoView: ResultM3AView)

}