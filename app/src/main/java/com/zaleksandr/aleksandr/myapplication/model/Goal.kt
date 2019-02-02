package com.zaleksandr.aleksandr.myapplication.model

data class Goal(
        val currentUserId: String?,
        val yearIntro: String?,
        val yearOneDay: String?,
        val yearTwoDay: String?,
        val yearTWOne: String?,
        val yearNWET: String?,
        val monthIntro: String?,
        val monthOneDay: String?,
        val monthTwoDay: String?,
        val monthTWOne: String?,
        val monthNWET: String?,
        val weekIntro: String?,
        val weekOneDay: String?,
        val weekTwoDay: String?,
        val weekTWOne: String?,
        val weekNWET: String?,
        val dayIntro: String?,
        val dayOneDay: String?,
        val dayTwoDay: String?
        ) {
    constructor() : this(
            currentUserId ="",
            yearIntro = "",
            yearOneDay = "0",
            yearTwoDay = "0",
            yearTWOne = "0",
            yearNWET = "0",
            monthIntro = "0",
            monthOneDay = "",
            monthTwoDay = "",
            monthTWOne = "",
            monthNWET = "",
            weekIntro = "",
            weekOneDay = "",
            weekTwoDay = "",
            weekTWOne = "",
            weekNWET = "",
            dayIntro = "",
            dayOneDay = "",
            dayTwoDay = ""
    )

    fun constructor() {
    }
}
