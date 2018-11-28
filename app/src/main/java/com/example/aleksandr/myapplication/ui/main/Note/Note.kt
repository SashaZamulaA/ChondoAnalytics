package com.example.aleksandr.myapplication.ui.main.Note

class Note(val id: String?,
           val title: String?,
           val description: String,
           val priority: Int) {

    constructor() : this("", "", "", 0)
}




