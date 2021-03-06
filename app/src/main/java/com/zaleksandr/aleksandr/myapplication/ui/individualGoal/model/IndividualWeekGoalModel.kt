package com.zaleksandr.aleksandr.myapplication.ui.individualGoal.model

data class IndividualWeekGoalModel(
        val currentUserId: String?,
        val weekMMBK: String,
        val weekLectTraining: String?,
        val weekContacts: String,
        val weekIntro: String?,
        val weekOneDay: String?,
        val weekTwoDay: String?,
        val weekAct: String

) {
    constructor() : this(
            currentUserId = "",
            weekMMBK = "",
            weekLectTraining = "",
            weekContacts = "",
            weekIntro = "",
            weekOneDay = "",
            weekTwoDay = "",
            weekAct = ""
    )

    fun constructor() {
    }
}
