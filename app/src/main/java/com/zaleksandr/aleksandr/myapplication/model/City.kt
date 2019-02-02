package com.zaleksandr.aleksandr.myapplication.model

import java.util.*

data class City(
        val getId: String?,
        val id: String?,
        val intro: String?,
        val onedayWS: String?,
        val twoDayWS: String?,
        val twOneDay: String?,
        val centers: String?,
        val approach: String?,
        val timeStr: String?,
        val lectOnStr: String?,
        val lectCentr: String?,
        val time: Date,
        val timestamp: Long,
        val userPhotoPath: String?,
        val name: String?,
        val nwet: String?,
        val dp: String?

) {
    constructor() : this(getId = "",
            id = "0",
            intro = "0",
            onedayWS = "0",
            twoDayWS = "0",
            twOneDay = "0",
            centers = "",
            approach = "",
            timeStr = "",
            lectOnStr = "",
            lectCentr = "",
            time = Date(),
            timestamp = 0,
            userPhotoPath = "",
            name = "",
            nwet = "",
            dp = ""
    )

}

fun constructor() {
}

enum class Method(val method: String) {
    KYIV("KYIV"),
    KHARKIV("KHARKIV"),
    LVIV("LVIV"),
    DNEPR("DNEPR"),
    ZHYTOMYR("ZHYTOMYR"),
    ODESSA("ODESSA"),
    CHERNIGOV("CHERNIGOV"),
}
