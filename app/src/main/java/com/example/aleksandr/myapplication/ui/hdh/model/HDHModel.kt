package com.example.aleksandr.myapplication.ui.hdh.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class HDHModel {
    var id: String = ""
    var name: String = ""
    var category: String = ""

    constructor() {
        //this constructor is required
    }

    constructor(id: String, name: String, category: String) {
        this.id = id
        this.name = name
        this.category = category
    }
}