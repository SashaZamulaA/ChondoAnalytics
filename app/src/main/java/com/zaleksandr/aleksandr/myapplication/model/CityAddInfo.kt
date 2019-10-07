package com.zaleksandr.aleksandr.myapplication.model

import java.util.*

data class CityAddInfo(
        val getId: String?,
        val id: String?,
        val time: Date,
        val timestamp: Long,
        val dp: String?


) {
    constructor() : this(
            getId = "",
            id = "0",
            time = Date(),
            timestamp = 0,
            dp = ""
               )

    fun constructor() {
    }
}

