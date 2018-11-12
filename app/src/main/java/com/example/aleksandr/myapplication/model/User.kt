package com.example.aleksandr.myapplication.model

data class User(var name: String,
                var email: String,
                val profilePicturePath: String?,
                var registrationTokens: MutableList<String>) {
    constructor() : this("", "",null, mutableListOf())
}
