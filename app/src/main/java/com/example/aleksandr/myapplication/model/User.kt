package com.example.aleksandr.myapplication.model

data class User(var name: String,
                var email: String,
                var spinnerCity: String,
                val profilePicturePath: String?,
                var registrationTokens: MutableList<String>) {
    constructor() : this("", "","",null, mutableListOf())
}
