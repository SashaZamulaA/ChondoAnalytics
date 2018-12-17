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
        ){
    constructor() : this("","", "", "", "","","","","",Date())
}

//class WeightEntryVH(itemView: View?)
//    : RecyclerView.ViewHolder(itemView) {
//    var weightView: TextView? =
//            itemView?.findViewById(R.id.one_day_seminar_edittext)
//    var timeView: TextView? =
//            itemView?.findViewById(R.id.two_day_seminar_edittext)
//}