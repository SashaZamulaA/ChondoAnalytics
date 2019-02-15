package com.zaleksandr.aleksandr.myapplication.ui.individualGoal.model

data class IndividualYearGoalModel(
        val currentUserId: String?,
        val yearIntro: String?,
        val yearOneDay: String?,
        val yearTwoDay: String?,
        val yearTWOne: String?,
        val yearActionaiser: String,
        val yearNWET: String?

        ) {
    constructor() : this(

            currentUserId ="",
            yearIntro = "",
            yearOneDay = "0",
            yearTwoDay = "0",
            yearTWOne = "0",
            yearActionaiser = "0",
            yearNWET = "0"
    )

    fun constructor() {
    }
}
