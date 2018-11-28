package com.example.aleksandr.myapplication.di.component

import com.example.aleksandr.myapplication.AndroidApplication
import com.example.aleksandr.myapplication.ui.add_task.NewNoteView
import com.example.aleksandr.myapplication.ui.hdh.HDHView
import com.example.aleksandr.myapplication.ui.chondo_result.ResultM3AView
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [(ApplicationComponent::class)])
internal interface ViewComponent {

    fun inject(application: AndroidApplication)

    // ACTIVITY


    // VIEW
    fun inject(infoView: ResultM3AView)
    fun inject(hdhView: HDHView)
    fun inject(newNoteView : NewNoteView)
}