package com.example.aleksandr.myapplication.ui.login.model

import com.google.firebase.database.IgnoreExtraProperties
import javax.inject.Singleton

@Singleton
@IgnoreExtraProperties
class User {
     var name: String? = null
     var email: String? = null


    constructor(name: String) {
this.name = name
    }

    constructor(name: String, email: String) {
        this.name = name
        this.email = email

    }
}

