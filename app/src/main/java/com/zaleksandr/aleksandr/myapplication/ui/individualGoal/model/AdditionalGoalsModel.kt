package com.zaleksandr.aleksandr.myapplication.ui.individualGoal.model

data class AdditionalGoalsModel(

        val currentUserId: String?,
        val yearDPKor: String?,
        val yearDPUkr: String?,
        val yearHDH:String?,
        val yearMobilisation: String?

        ) {
    constructor() : this(

            currentUserId = "",
            yearDPKor = "",
            yearDPUkr = "",
            yearHDH = "",
            yearMobilisation = ""
    )

    fun constructor() {
    }
}
