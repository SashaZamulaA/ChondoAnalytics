package com.example.aleksandr.myapplication.ui.add_task.note.model

class Note {
    var title: String = ""
    var result: Double? = null
    var goal: Double? = null
    var procent: Double? = null
    var start_period: String = ""
    var end_period: String = ""

    constructor() {

    }

    constructor(title: String, achivement: Double, goal: Double) {
        this.title = title
        this.goal = goal
        this.result = achivement
    }

    constructor(title: String, achivement: Double, procent: Double, goal: Double) {
        this.title = title
        this.goal = goal
        this.procent = procent
        this.result = achivement
    }
}