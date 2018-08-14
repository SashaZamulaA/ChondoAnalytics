package com.example.aleksandr.myapplication

import android.app.Application
import com.example.aleksandr.myapplication.di.component.ApplicationComponent
import com.example.aleksandr.myapplication.di.module.NetModule
import com.example.aleksandr.myapplication.di.component.DaggerApplicationComponent

class AndroidApplication :Application(){

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder()
                .netModule(NetModule())
                .build()

        applicationComponent.inject(this)
    }

}