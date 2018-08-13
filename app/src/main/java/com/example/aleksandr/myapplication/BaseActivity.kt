package com.example.aleksandr.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class BaseActivity : AppCompatActivity(), IView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)







    }
}
