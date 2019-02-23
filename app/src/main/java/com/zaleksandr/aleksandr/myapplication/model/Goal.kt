package com.zaleksandr.aleksandr.myapplication.model

data class Goal(
        val currentUserId: String?,
        val yearIntro: String?,
        val yearOneDay: String?,
        val yearTwoDay: String?,
        val yearAct: String?,
        val yearTWOne: String?,
        val yearNWET: String?,
        val monthIntro: String?,
        val monthOneDay: String?,
        val monthTwoDay: String?,
        val monthAct: String?,
        val monthTWOne: String?,
        val monthNWET: String?,
        val weekIntro: String?,
        val weekOneDay: String?,
        val weekTwoDay: String?,
        val weekAct: String?,
        val dayIntro: String?,
        val dayOneDay: String?,
        val dayTwoDay: String?
        ) {
    constructor() : this(
            currentUserId ="",
            yearIntro = "",
            yearOneDay = "0",
            yearTwoDay = "0",
            yearAct = "0",
            yearTWOne = "0",
            yearNWET = "0",
            monthIntro = "0",
            monthOneDay = "",
            monthTwoDay = "",
            monthAct = "",
            monthTWOne = "",
            monthNWET = "",
            weekIntro = "",
            weekOneDay = "",
            weekTwoDay = "",
            weekAct = "",
            dayIntro = "",
            dayOneDay = "",
            dayTwoDay = ""
    )

    fun constructor() {
    }
}
