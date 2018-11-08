package com.example.aleksandr.myapplication.ui.login.model

class Settings {
    lateinit var name: String
    lateinit var phone: String

    constructor()

    constructor(name: String, phone: String) {
        this.name = name
        this.phone = phone

    }
}

