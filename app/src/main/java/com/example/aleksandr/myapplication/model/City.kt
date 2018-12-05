package com.example.aleksandr.myapplication.model

import android.text.format.DateUtils
import com.example.aleksandr.myapplication.R
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

data class City(
        val intro : String?,
        val onedayWS: String?,
        val twoDayWS:String?,
        val centers: String?,
        val time: Long = System.currentTimeMillis())
{
    constructor() : this("0","0", "0", "", System.currentTimeMillis())
}

//class WeightEntryVH(itemView: View?)
//    : RecyclerView.ViewHolder(itemView) {
//    var weightView: TextView? =
//            itemView?.findViewById(R.id.one_day_seminar_edittext)
//    var timeView: TextView? =
//            itemView?.findViewById(R.id.two_day_seminar_edittext)
//}