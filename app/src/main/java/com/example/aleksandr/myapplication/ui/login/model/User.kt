package com.example.aleksandr.myapplication.ui.login.model

data class User(var name: String,
                var email: String,
                var registrationTokens: MutableList<String>) {
    constructor() : this("", "", mutableListOf())
}
