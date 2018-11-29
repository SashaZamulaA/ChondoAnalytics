package com.example.aleksandr.myapplication.model

import com.example.aleksandr.myapplication.R
import java.util.ArrayList

data class City(
                val intro : String? = "",
                val onedayWS: String? = "",
                val twoDayWS:String? = "",
                val centers: String?= "")
                 {
    constructor() : this("","", "")
}

//class WeightEntryVH(itemView: View?)
//    : RecyclerView.ViewHolder(itemView) {
//    var weightView: TextView? =
//            itemView?.findViewById(R.id.one_day_seminar_edittext)
//    var timeView: TextView? =
//            itemView?.findViewById(R.id.two_day_seminar_edittext)
//}