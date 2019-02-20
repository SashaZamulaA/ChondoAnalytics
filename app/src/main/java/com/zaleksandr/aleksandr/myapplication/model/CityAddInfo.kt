package com.zaleksandr.aleksandr.myapplication.model

import java.util.*

data class CityAddInfo(
        val getId: String?,
        val id: String?,
        val time: Date,
        val timestamp: Long,
        val dpKor: String?,
        val dp: String?,
        val mobilis: String?

) {
    constructor() : this(
            getId = "",
            id = "0",
            time = Date(),
            timestamp = 0,
            dpKor = "",
            dp = "",
            mobilis = ""
    )

    fun constructor() {
    }
}

