package com.zaleksandr.aleksandr.myapplication.model

import java.util.*

data class CityHDH(
        val getId: String?,
        val id: String?,
        val name: String,
        val userPhotoPath: String,
        val time: Date,
        val timestamp: Long,
        val hdhZero:Boolean,
        val hdhOne:Boolean,
        val hdhTwo:Boolean,
        val hdhThree:Boolean,
        val hdhFour:Boolean,
        val hdhFive: Boolean,
        val hdhSix: Boolean,
        val hdhSeven: Boolean,
        val mobilisation: Boolean,
        val ss: Boolean,
        val farm: Boolean

) {
    constructor() : this(
            getId = "",
            id = "0",
            name = "",
            userPhotoPath = "",
            time = Date(),
            timestamp = 0,
            hdhZero = false,
            hdhOne = false,
            hdhTwo = false,
            hdhThree = false,
            hdhFour = false,
            hdhFive = false,
            hdhSix = false,
            hdhSeven = false,
            mobilisation = false,
            ss = false,
            farm = false
    )

    fun constructor() {
    }
}

