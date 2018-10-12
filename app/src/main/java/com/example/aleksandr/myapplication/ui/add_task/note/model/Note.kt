package com.example.aleksandr.myapplication.ui.add_task.note.model

class Note {
    var title: String = ""
    var result: Double? = null
    var goal: String? = null
    var procent: Double? = null
    var startPeriod: String = ""
    var endPeriod: String = ""

    constructor() {

    }

    constructor(title: String, goal: String, startPeriod : String, endPeriod:String) {
        this.title = title
        this.goal = goal
        this.startPeriod = startPeriod
        this.endPeriod = endPeriod
    }

    constructor(title: String, achivement: Double, goal: String) {
        this.title = title
        this.goal = goal
        this.result = achivement
    }

    constructor(title: String, achivement: Double, procent: Double, goal: String) {
        this.title = title
        this.goal = goal
        this.procent = procent
        this.result = achivement
    }
}