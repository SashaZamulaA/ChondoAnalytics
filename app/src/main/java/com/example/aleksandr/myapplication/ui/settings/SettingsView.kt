package com.example.aleksandr.myapplication.ui.settings

import android.os.Bundle
import com.example.aleksandr.myapplication.BaseActivity
import com.example.aleksandr.myapplication.R
import com.vadym.adv.myhomepet.ui.settings.ISettingsView
import com.vadym.adv.myhomepet.ui.settings.SettingsPresenter

class SettingsView: BaseActivity(), ISettingsView {

    private lateinit var presenter: SettingsPresenter

    override fun init(savedInstanceState: Bundle?) {
        super.setContentView(R.layout.view_settings)
        presenter = SettingsPresenter(this, application)

    }
}