package com.zamulaaleksandr.aleksandr.myapplication.model

import java.util.*

data class City(
        val getId:String?,
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
        val time: Date,
        val timestamp: Long,
        val userPhotoPath: String?,
        val name: String
        ){
    constructor() : this("","0","0","0", "0", "0", "","","","","",Date(),0, "", "")

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
