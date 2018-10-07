package com.example.aleksandr.myapplication.ui.add_task

import android.app.Application
import com.example.aleksandr.myapplication.AndroidApplication
import com.example.aleksandr.myapplication.BasePresenter

class NewNotePresenter(newNotePresenter: NewNoteView, applicationComponent: Application) : BasePresenter<NewNoteView>(newNotePresenter) {
    init {
        (applicationComponent as AndroidApplication).applicationComponent.inject(this)

    }
    override fun onBindView() {}

    override fun onUnbindView() {}

    fun updateInStockVisibility() {

        view?.setVisibility(true)

    }

}