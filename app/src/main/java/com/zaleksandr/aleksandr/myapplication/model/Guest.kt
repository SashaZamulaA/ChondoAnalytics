package com.zaleksandr.aleksandr.myapplication.model

import java.util.*

data class Guest(

        val id: String?,
        val currentUserId: String?,
        val name: String?,
        val photo: String?,
        val centers: String?,
        val invitedPerson: String?,
        val intro: Boolean?,
        val onedayWS: Boolean?,
        val twoDayWS: Boolean?,
        val actionaiser: Boolean?,
        val twOneDay: Boolean?,
        val nwet: Boolean?,
        val timeIntro: String?,
        val timeOneDay: String?,
        val timeTwoDay: String?,
        val timeAct: String?,
        val time21Day: String?,
        val timeNwet: String?,
        val time: Date,
        val timestamp: Long,
        val birthday: String?,
        val phoneNum: String?,
        val description: String?
) {
    constructor() : this(

            id = "0",
            currentUserId = "0",
            name = "0",
            photo = "",
            centers = "",
            invitedPerson = "",
            intro = false,
            onedayWS = false,
            twoDayWS = false,
            actionaiser = false,
            twOneDay = false,
            nwet = false,
            timeIntro = "",
            timeOneDay = "",
            timeTwoDay = "",
            timeAct = "",
            time21Day = "",
            timeNwet = "",
            time = Date(),
            timestamp = 0,
            birthday = "",
            phoneNum = "",
            description = ""
    )

    fun constructor() {
    }
}

