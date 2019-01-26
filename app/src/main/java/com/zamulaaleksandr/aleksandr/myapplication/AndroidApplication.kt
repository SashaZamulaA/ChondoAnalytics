package com.zamulaaleksandr.aleksandr.myapplication

import android.app.Application
import com.zamulaaleksandr.aleksandr.myapplication.di.component.ApplicationComponent
import com.zamulaaleksandr.aleksandr.myapplication.di.module.NetModule
import com.zamulaaleksandr.aleksandr.myapplication.di.component.DaggerApplicationComponent


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