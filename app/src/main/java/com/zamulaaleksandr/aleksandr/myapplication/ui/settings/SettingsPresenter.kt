package com.vadym.adv.myhomepet.ui.settings

import android.app.Application
import com.zamulaaleksandr.aleksandr.myapplication.AndroidApplication
import com.zamulaaleksandr.aleksandr.myapplication.BasePresenter
import com.zamulaaleksandr.aleksandr.myapplication.ui.settings.SettingsView



class SettingsPresenter(settingsView: SettingsView, application: Application) : BasePresenter<SettingsView>(settingsView) {
    init { (application as AndroidApplication).applicationComponent.inject(this) }

      override fun onBindView() {}

    override fun onUnbindView() {}



}