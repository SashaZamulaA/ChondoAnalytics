package com.example.aleksandr.myapplication.model

import java.util.*

data class City(
        val id : String?,
        val intro : String?,
        val onedayWS: String?,
        val twoDayWS: String?,
        val twOneDay: String?,
        val centers: String?,
        val approach: String?,
        val timeStr: String?,
        val lectOnStr: String?,
        val lectCentr: String?,
        val time: Date
        ){
    constructor() : this("","","", "", "", "","","","","",Date())

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
