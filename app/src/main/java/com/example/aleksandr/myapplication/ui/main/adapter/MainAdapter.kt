package com.example.aleksandr.myapplication.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.model.City
import com.example.aleksandr.myapplication.util.*
import com.example.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.item_main_list.view.*
import java.util.*

class MainAdapter(val list: ArrayList<City>) : RecyclerView.Adapter<MainAdapter.CityHolder>() {

    override fun getItemCount(): Int {
        return list.size
    }
    private val noteRefCollection = firestoreInstance.collection("NewCity")

    enum class ClickByFilter {
        DAY, MONTH, WEEK, YEAR
    }

    var period = 0
    fun perioSelected(periodSelected: MainAdapter.ClickByFilter) {
        when (periodSelected) {
            MainAdapter.ClickByFilter.DAY -> {
                period = 1; notifyDataSetChanged()
            }
            MainAdapter.ClickByFilter.WEEK -> {
                period = 2; notifyDataSetChanged()
            }
            MainAdapter.ClickByFilter.MONTH -> {
                period = 3; notifyDataSetChanged()
            }
            MainAdapter.ClickByFilter.YEAR -> {
                period = 4; notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_main_list,
                parent, false)
        return CityHolder(v)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
          holder.bind()
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    inner class CityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() {
            val sumIntro = 0
            val sumOneD = 0
            val sumTwoD = 0
            val sumTwent = 0
            val sumTimeStr = 0
            val sumAppr = 0
            val sumStrLect = 0
            val sumCenteLect = 0


            clickByFilter(noteRefCollection, position, period).addOnSuccessListener { queryDocumentSnapshots ->
                cityName(queryDocumentSnapshots, sumIntro, sumOneD, sumTwoD, sumTwent, sumAppr, sumTimeStr, sumStrLect, sumCenteLect)


            }
        }

        private fun cityName(queryDocumentSnapshots: QuerySnapshot, sumIntro: Int, sumOneD: Int, sumTwoD: Int, sumTwent: Int, sumAppr: Int, sumTimeStr: Int, sumStrLect: Int, sumCenteLect: Int) {
            itemView.result_city.text = "KYIV"
            var sumIntroKiev1 = sumIntro
            var sumOneD1 = sumOneD
            var sumTwoD1 = sumTwoD
            var sumTwent1 = sumTwent
            var sumAppr1 = sumAppr
            var sumTimeStr1 = sumTimeStr
            var sumStrLect1 = sumStrLect
            var sumCenteLect1 = sumCenteLect
            if (!queryDocumentSnapshots.isEmpty) {
                queryDocumentSnapshots.forEach { documentSnapshot ->

                    val resultNote = documentSnapshot.toObject(City::class.java)

                    if (!resultNote.intro.isNullOrEmpty()) {
                        val intro = (Integer.parseInt(resultNote.intro))
                        sumIntroKiev1 += intro
                    } else {
                        itemView.main_intro.text = "0"
                    }
                    if (!resultNote.onedayWS.isNullOrEmpty()) {
                        val onaDay = Integer.parseInt(resultNote.onedayWS)
                        sumOneD1 += onaDay
                    } else {
                        itemView.main_one_day.text = "0"
                    }
                    if (!resultNote.twoDayWS.isNullOrEmpty()) {
                        val twoDay = Integer.parseInt(resultNote.twoDayWS)
                        sumTwoD1 += twoDay
                    } else {
                        itemView.main_two_day.text = "0"
                    }
                    if (!resultNote.twOneDay.isNullOrEmpty()) {
                        val twOneDay = Integer.parseInt(resultNote.twOneDay)
                        sumTwent1 += twOneDay
                    } else {
                        itemView.main_21_day.text = "0"
                    }
                    if (!resultNote.approach.isNullOrEmpty()) {
                        val approach = Integer.parseInt(resultNote.approach)
                        sumAppr1 += approach
                    } else {
                        itemView.main_approach.text = "0"
                    }
                    if (!resultNote.timeStr.isNullOrEmpty()) {
                        val timeStr = Integer.parseInt(resultNote.timeStr)
                        sumTimeStr1 += timeStr
                    } else {
                        itemView.main_time_str.text = "0"
                    }
                    if (!resultNote.lectOnStr.isNullOrEmpty()) {
                        val lectOnStr = Integer.parseInt(resultNote.lectOnStr)
                        sumStrLect1 += lectOnStr
                    } else {
                        itemView.main_street_lect.text = "0"
                    }
                    if (!resultNote.lectCentr.isNullOrEmpty()) {
                        val lectCentr = Integer.parseInt(resultNote.lectCentr)
                        sumCenteLect1 += lectCentr
                    } else {
                        itemView.main_lect_center.text = "0"
                    }

//                    itemView.result_city.text = model.centers.toString()
                    itemView.main_intro.text = sumIntroKiev1.toString()
                    itemView.main_one_day.text = sumOneD1.toString()
                    itemView.main_two_day.text = sumTwoD1.toString()
                    itemView.main_21_day.text = sumTwent1.toString()
                    itemView.main_time_str.text = sumTimeStr1.toString()
                    itemView.main_approach.text = sumAppr1.toString()
                    itemView.main_street_lect.text = sumStrLect1.toString()
                    itemView.main_lect_center.text = sumCenteLect1.toString()

                }
            } else {
//                itemView.result_city.text = model.centers.toString()
                itemView.main_intro.text = "0"
                itemView.main_one_day.text = "0"
                itemView.main_two_day.text = "0"
                itemView.main_21_day.text = "0"
                itemView.main_time_str.text = "0"
                itemView.main_approach.text = "0"
                itemView.main_street_lect.text = "0"
                itemView.main_lect_center.text = "0"

            }

        }
    }
}
