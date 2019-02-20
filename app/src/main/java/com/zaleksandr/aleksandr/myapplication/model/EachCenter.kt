package com.zaleksandr.aleksandr.myapplication.model

import java.util.*

data class EachCenter(
        val getId: String?,
        val id: String?,
        val intro: String?,
        val onedayWS: String?,
        val twoDayWS: String?,
        val actionaiser: String?,
        val twOneDay: String?,
        val centers: String?,
        val time: Date,
        val timestamp: Long,
        val userPhotoPath: String?,
        val name: String?,
        val nwet: String?,
        val mmbk: String?

) {
    constructor() : this(
            getId = "",
            id = "0",
            intro = "0",
            onedayWS = "0",
            twoDayWS = "0",
            actionaiser = "0",
            twOneDay = "0",
            centers = "",
            time = Date(),
            timestamp = 0,
            userPhotoPath = "",
            name = "",
            nwet = "",
            mmbk = ""

    )


    fun constructor() {
    }
}