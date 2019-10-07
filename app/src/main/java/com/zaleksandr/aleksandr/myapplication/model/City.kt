package com.zaleksandr.aleksandr.myapplication.model

import java.util.*

data class City(
        val getId: String?,
        val id: String?,
        val intro: String?,
        val onedayWS: String?,
        val twoDayWS: String?,
        val actionaiser: String?,
        val twOneDay: String?,
        val centers: String?,
        val approach: String?,
        val telCont: String?,
        val timeCenter: String?,
        val timeStr: String?,
        val lectTraining: String?,
        val lectOnStr: String?,
        val lectCentr: String?,
        val time: Date,
        val timestamp: Long,
        val userPhotoPath: String?,
        val name: String?,
        val nwet: String?,
        val dp: String?,
        val mmbk: String?,
        val descriptionGoal: String?,
        val eduMat: String?,
        val gradeIntGoal: String?


) {
    constructor() : this(getId = "",
            id = "0",
            intro = "0",
            onedayWS = "0",
            twoDayWS = "0",
            actionaiser = "0",
            twOneDay = "0",
            centers = "",
            approach = "",
            telCont = "",
            timeCenter = "",
            timeStr = "",
            lectTraining = "",
            lectOnStr = "",
            lectCentr = "",
            time = Date(),
            timestamp = 0,
            userPhotoPath = "",
            name = "",
            nwet = "",
            dp = "",
            mmbk = "",
            descriptionGoal = "",
            eduMat = "",
            gradeIntGoal = ""
    )


}

fun constructor() {}

