package com.example.aleksandr.myapplication.ui.main

import android.os.Bundle
import com.example.aleksandr.myapplication.BaseActivity
import com.example.aleksandr.myapplication.BasePresenter
import com.example.aleksandr.myapplication.R

class MainActivity: BaseActivity() {
    private lateinit var presenter: MainPresenter

    override fun init(savedInstanceState: Bundle?) {
        super.setContentView(R.layout.activity_main)
        presenter = MainPresenter(this, application)
    }
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter.bindView(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.unbindView(this)
    }

    override fun onBackPressed() {
        return
    }

}