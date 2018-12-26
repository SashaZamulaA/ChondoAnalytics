package com.example.aleksandr.myapplication.model

import java.util.*

data class City(
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
//        val method: List<Method>
        ){
    constructor() : this("","", "", "", "","","","","",Date())
    enum class Type { Kyiv, Kharkiv, Dnepr, Lviv }
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
//class WeightEntryVH(itemView: View?)
//    : RecyclerView.ViewHolder(itemView) {
//    var weightView: TextView? =
//            itemView?.findViewById(R.id.one_day_seminar_edittext)
//    var timeView: TextView? =
//            itemView?.findViewById(R.id.two_day_seminar_edittext)
//}