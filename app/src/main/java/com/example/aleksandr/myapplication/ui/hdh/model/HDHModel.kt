package com.example.aleksandr.myapplication.ui.hdh.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class HDHModel {
    var id: String? = null
    var name: String? = null
    var category: String? = null

    constructor() {
        //this constructor is required
    }

    constructor(id: String, name: String, category: String) {
        this.id = id
        this.name = name
        this.category = category
    }
}