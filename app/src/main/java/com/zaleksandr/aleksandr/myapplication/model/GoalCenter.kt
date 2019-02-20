package com.zaleksandr.aleksandr.myapplication.model

data class GoalCenter(
        val center: String?,
        val currentUserId: String?,
        val yearIntroCenter: String?,
        val yearOneDayCenter: String?,
        val yearActionCenter: String?,
        val yearTwoDayCenter: String?,
        val yearTWOneCenter: String?,
        val yearNWETCenter: String?,
        val monthIntroCenter: String?,
        val monthOneDayCenter: String?,
        val monthTwoDayCenter: String?,
        val monthActionCenter: String?,
        val weekIntroCenter: String?,
        val weekOneDayCenter: String?,
        val weekTwoDayCenter: String?
        ) {
    constructor() : this(
            center = "",
            currentUserId = "",
            yearIntroCenter = "",
            yearOneDayCenter ="",
            yearActionCenter = "",
            yearTwoDayCenter = "",
            yearTWOneCenter = "",
            yearNWETCenter = "",
            monthIntroCenter = "",
            monthOneDayCenter = "",
            monthTwoDayCenter = "",
            monthActionCenter = "",
            weekIntroCenter = "",
            weekOneDayCenter = "",
            weekTwoDayCenter = ""
    )

    fun constructor() {
    }
}
