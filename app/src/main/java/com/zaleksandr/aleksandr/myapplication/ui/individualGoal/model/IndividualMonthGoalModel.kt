package com.zaleksandr.aleksandr.myapplication.ui.individualGoal.model

data class IndividualMonthGoalModel(
        val currentUserId: String?,
        val monthIntro: String?,
        val monthOneDay: String?,
        val monthTwoDay: String?,
        val monthTWOne: String?,
        val monthActioniser: String?,
        val monthNWET: String

        ) {
    constructor() : this(
            currentUserId ="",
            monthIntro = "0",
            monthOneDay = "",
            monthTwoDay = "",
            monthTWOne = "",
            monthActioniser = "",
            monthNWET = ""
    )

    fun constructor() {
    }
}
