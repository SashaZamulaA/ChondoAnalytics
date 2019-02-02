package com.zaleksandr.aleksandr.myapplication.di.component

import com.zaleksandr.aleksandr.myapplication.AndroidApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [(ApplicationComponent::class)])
internal interface ViewComponent {

    fun inject(application: AndroidApplication)

}