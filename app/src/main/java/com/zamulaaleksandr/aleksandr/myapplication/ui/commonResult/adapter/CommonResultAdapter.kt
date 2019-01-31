package com.zamulaaleksandr.aleksandr.myapplication.ui.commonResult.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.QuerySnapshot
import com.zamulaaleksandr.aleksandr.myapplication.model.City
import com.zamulaaleksandr.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import com.zamulaaleksandr.aleksandr.myapplication.util.clickByFilter
import com.zamulaaleksandr.aleksandr.myapplication.util.clickByFilterCommon
import kotlinx.android.synthetic.main.item_common_goal_and_result_list2.view.*
import kotlinx.android.synthetic.main.item_common_result_list.view.*


class CommonResultAdapter(private var list: ArrayList<City>, private var fragmentCommunication: FragmentCommunication) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    var city: City? = null

    override fun getItemCount(): Int {
        return list.size + 1
    }

    private fun getItem(position: Int): City {
        return list[position - 1]
    }


    override fun getItemViewType(position: Int): Int {
        return if (isPositionHeader(position)) TYPE_HEADER else TYPE_ITEM

    }

    private fun isPositionHeader(position: Int): Boolean {
        return position == 0
    }

    private val noteRefCollection = firestoreInstance.collection("NewCity")

    enum class ClickByFilter {
        DAY, MONTH, WEEK, YEAR
    }

    var period = 0
    fun perioSelected(periodSelected: CommonResultAdapter.ClickByFilter) {

        when (periodSelected) {
            CommonResultAdapter.ClickByFilter.DAY -> {
                period = 1; notifyDataSetChanged()
            }
            CommonResultAdapter.ClickByFilter.WEEK -> {
                period = 2; notifyDataSetChanged()
            }
            CommonResultAdapter.ClickByFilter.MONTH -> {
                period = 3; notifyDataSetChanged()
            }
            CommonResultAdapter.ClickByFilter.YEAR -> {
                period = 4; notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_ITEM) {
            val v = LayoutInflater.from(parent.context).inflate(com.zamulaaleksandr.aleksandr.myapplication.R.layout.item_common_result_list,
                    parent, false)
            return CityHolder(v, fragmentCommunication)
        } else if (viewType == TYPE_HEADER) {
            val v = LayoutInflater.from(parent.context).inflate(com.zamulaaleksandr.aleksandr.myapplication.R.layout.item_common_goal_and_result_list2,
                    parent, false)
            return VHHeader(v, fragmentCommunication);
        }
        throw RuntimeException("there is no type that matches the type $viewType + make sure your using types correctly")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is CityHolder) {
            val item = getItem(position)
            holder.itemView.individual_result_city.text = item.centers
            holder.bind()
        } else if (holder is VHHeader) {
            holder.bind2()
        }


    }

//        fun updateList(list: ArrayList<City>) {
//        this.list = list
//        notifyDataSetChanged()
//    }
    inner class VHHeader(itemView: View, var fragmentCommunication: FragmentCommunication) : RecyclerView.ViewHolder(itemView) {

        fun bind2() {
            clickByFilterCommon(noteRefCollection, position, period).addOnSuccessListener { queryDocumentSnapshots ->
                cityName(queryDocumentSnapshots)
            }
        }

        private fun cityName(queryDocumentSnapshots: QuerySnapshot) {

            var sumIntro = 0
            var sumOneD1 = 0
            var sumTwoD1 = 0
            var sumTwent1 = 0
            var sumNwet = 0


            if (!queryDocumentSnapshots.isEmpty) {
                queryDocumentSnapshots.forEach { documentSnapshot ->

                    val resultNote = documentSnapshot.toObject(City::class.java)

                    if (!resultNote.intro.isNullOrEmpty()) {
                        val intro = (Integer.parseInt(resultNote.intro))
                        sumIntro += intro
                    }
                    if (!resultNote.onedayWS.isNullOrEmpty()) {
                        val onaDay = Integer.parseInt(resultNote.onedayWS)
                        sumOneD1 += onaDay
                    }
                    if (!resultNote.twoDayWS.isNullOrEmpty()) {
                        val twoDay = Integer.parseInt(resultNote.twoDayWS)
                        sumTwoD1 += twoDay
                    }
                    if (!resultNote.twOneDay.isNullOrEmpty()) {
                        val twOneDay = Integer.parseInt(resultNote.twOneDay)
                        sumTwent1 += twOneDay
                    }
                    if (!resultNote.nwet.isNullOrEmpty()) {
                        val approach = Integer.parseInt(resultNote.approach)
                        sumNwet += approach
                    }

                }
                itemView.common_result_intro.text = sumIntro.toString()
                itemView.common_one_d_result.text = sumOneD1.toString()
                itemView.common_two_d_result.text = sumTwoD1.toString()
                itemView.common_tw_one_d_result.text = sumTwent1.toString()
                itemView.common_nwet_result.text = sumNwet.toString()


            } else {
//                itemView.result_city.text = model.centers.toString()
                itemView.common_result_intro.text = "0"
                itemView.common_result_intro.text = "0"
                itemView.common_two_d_result.text = "0"
                itemView.common_tw_one_d_result.text = "0"
                itemView.common_nwet_result.text = "0"

            }
        }


    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    inner class CityHolder(itemView: View, var fragmentCommunication: FragmentCommunication) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            fragmentCommunication.respond(position)

        }

        fun bind() {

            val item = getItem(position)
            itemView.individual_result_city.text = item.centers

            clickByFilter(noteRefCollection, position, period).addOnSuccessListener { queryDocumentSnapshots ->
                cityName(queryDocumentSnapshots)
            }


        }

        private fun cityName(queryDocumentSnapshots: QuerySnapshot) {

            var sumIntro = 0
            var sumOneD1 = 0
            var sumTwoD1 = 0
            var sumTwent1 = 0
            var sumAppr = 0
            var sumTimeStr = 0
            var sumStrLect = 0
            var sumCenteLect = 0

            if (!queryDocumentSnapshots.isEmpty) {
                queryDocumentSnapshots.forEach { documentSnapshot ->

                    val resultNote = documentSnapshot.toObject(City::class.java)

                    if (!resultNote.intro.isNullOrEmpty()) {
                        val intro = (Integer.parseInt(resultNote.intro))
                        sumIntro += intro
                    } else {
                        itemView.individual_intro.text = "0"
                    }
                    if (!resultNote.onedayWS.isNullOrEmpty()) {
                        val onaDay = Integer.parseInt(resultNote.onedayWS)
                        sumOneD1 += onaDay
                    } else {
                        itemView.individual_one_day.text = "0"
                    }
                    if (!resultNote.twoDayWS.isNullOrEmpty()) {
                        val twoDay = Integer.parseInt(resultNote.twoDayWS)
                        sumTwoD1 += twoDay
                    }
                    if (!resultNote.twOneDay.isNullOrEmpty()) {
                        val twOneDay = Integer.parseInt(resultNote.twOneDay)
                        sumTwent1 += twOneDay
                    }

                    if (!resultNote.approach.isNullOrEmpty()) {
                        val approach = Integer.parseInt(resultNote.approach)
                        sumAppr += approach
                    } else {
                        itemView.individual_approach.text = "0"
                    }
                    if (!resultNote.timeStr.isNullOrEmpty()) {
                        val timeStr = Integer.parseInt(resultNote.timeStr)
                        sumTimeStr += timeStr
                    } else {
                        itemView.individual_time_str.text = "0"
                    }
                    if (!resultNote.lectOnStr.isNullOrEmpty()) {
                        val lectOnStr = Integer.parseInt(resultNote.lectOnStr)
                        sumStrLect += lectOnStr
                    } else {
                        itemView.individual_street_lect.text = "0"
                    }
                    if (!resultNote.lectCentr.isNullOrEmpty()) {
                        val lectCentr = Integer.parseInt(resultNote.lectCentr)
                        sumCenteLect += lectCentr
                    } else {
                        itemView.individual_lect_center.text = "0"
                    }
                }
                itemView.individual_intro.text = sumIntro.toString()
                itemView.individual_one_day.text = sumOneD1.toString()
                itemView.individual_two_day.text = sumTwoD1.toString()
                itemView.individual_21_day.text = sumTwent1.toString()
                itemView.individual_time_str.text = sumTimeStr.toString()
                itemView.individual_approach.text = sumAppr.toString()
                itemView.individual_street_lect.text = sumStrLect.toString()
                itemView.individual_lect_center.text = sumCenteLect.toString()

            } else {
//                itemView.result_city.text = model.centers.toString()
                itemView.individual_intro.text = "0"
                itemView.individual_one_day.text = "0"
                itemView.individual_two_day.text = "0"
                itemView.individual_21_day.text = "0"
                itemView.individual_time_str.text = "0"
                itemView.individual_approach.text = "0"
                itemView.individual_street_lect.text = "0"
                itemView.individual_lect_center.text = "0"

            }
        }
    }


    interface FragmentCommunication {
        fun respond(position: Int)
    }
}


