package com.example.aleksandr.myapplication.ui.add_task.note.model

import com.google.firebase.database.IgnoreExtraProperties

@com.google.firebase.firestore.IgnoreExtraProperties

class ResultNote {
    var title: String = ""
    var result: Double? = null
    var goal: String? = null
    var procent: Double? = null
    var startPeriod: String = ""
    var endPeriod: String = ""
    var mImageUrl: String? = null

    constructor(mImageUrl: String) {
        this.mImageUrl = mImageUrl
    }

    constructor(title: String, goal: String, startPeriod : String, endPeriod:String, mImageUrl: String) {
        this.title = title
        this.goal = goal
        this.startPeriod = startPeriod
        this.endPeriod = endPeriod
        this.mImageUrl = mImageUrl
    }

    constructor(title: String, achivement: Double, goal: String) {
        this.title = title
        this.goal = goal
        this.result = achivement
    }

    constructor(){}

    constructor(title: String, goal: String, startPeriod: String, endPeriod: String) {
        this.title = title
        this.goal = goal
        this.startPeriod = startPeriod
        this.endPeriod = endPeriod
    }
}