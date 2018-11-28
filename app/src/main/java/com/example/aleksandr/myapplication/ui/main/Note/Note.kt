package com.example.aleksandr.myapplication.ui.main.Note

class Note(val id: String?,
           val title: String?,
           val description: String,
           val priority: Int,
           val tags: MutableList<Array<String>>) {

    constructor() : this("", "", "", 0, mutableListOf())
}




