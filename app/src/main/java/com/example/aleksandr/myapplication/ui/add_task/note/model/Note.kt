package com.example.aleksandr.myapplication.ui.add_task.note.model

class Note {
    var title: String = ""
    var achivement: Double? = null
    var goal: Double? = null
    var procent: Double? = null

    constructor() {

    }

    constructor(title: String, achivement: Double, goal: Double) {
        this.title = title
        this.goal = goal
        this.achivement = achivement
    }

    constructor(title: String, achivement: Double, procent: Double, goal: Double) {
        this.title = title
        this.goal = goal
        this.procent = procent
        this.achivement = achivement
    }
}