package com.zaleksandr.aleksandr.myapplication.ui.commonResult.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.QuerySnapshot
import com.zaleksandr.aleksandr.myapplication.model.City
import com.zaleksandr.aleksandr.myapplication.model.Goal
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import com.zaleksandr.aleksandr.myapplication.util.clickByFilter
import com.zaleksandr.aleksandr.myapplication.util.clickByFilterCommon
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
            val v = LayoutInflater.from(parent.context).inflate(com.zaleksandr.aleksandr.myapplication.R.layout.item_common_result_list,
                    parent, false)
            return CityHolder(v, fragmentCommunication)
        } else if (viewType == TYPE_HEADER) {
            val v = LayoutInflater.from(parent.context).inflate(com.zaleksandr.aleksandr.myapplication.R.layout.item_common_goal_and_result_list2,
                    parent, false)
            return VHHeader(v, fragmentCommunication);
        }
        throw RuntimeException("there is no type that matches the type $viewType + make sure your using types correctly")
    }


    fun clearProductItemList() {
        val size = itemCount

        this.notifyItemRangeRemoved(0, size)

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

    fun updateList(list: ArrayList<City>) {
        this.list = list
        list.size
        notifyDataSetChanged()
    }

    inner class VHHeader(itemView: View, var fragmentCommunication: FragmentCommunication) : RecyclerView.ViewHolder(itemView) {

        fun bind2() {
            clickByFilterCommon(noteRefCollection, position, period).addOnSuccessListener { queryDocumentSnapshots ->
                commonResult(queryDocumentSnapshots)
            }
            noteRefCollection.get().addOnSuccessListener { queryDocumentSnapshots ->
                commonGoal(queryDocumentSnapshots)

            }
        }

        private fun commonGoal(queryDocumentSnapshots: QuerySnapshot) {

//year
            var sumIntroyear = 0
            var sumOneD1year = 0
            var sumTwoD1year = 0
            var sumTwent1year = 0
            var sumNwetyear = 0
//month
            var sumIntromonth = 0
            var sumOneD1month = 0
            var sumTwoD1month = 0
            var sumTwent1month = 0
            var sumNwetmonth = 0
//week
            var sumIntroweek = 0
            var sumOneD1week = 0
            var sumTwoD1week = 0
//day
            var sumIntroDay = 0
            var sumOneDDay = 0
            var sumTwoDDay = 0

            if (!queryDocumentSnapshots.isEmpty) {
                queryDocumentSnapshots.forEach { documentSnapshot ->
                    val goalNote = documentSnapshot.toObject(Goal::class.java)

//year
                    if (!goalNote.yearIntro.isNullOrEmpty() && period == 4) {
                        val intro = (Integer.parseInt(goalNote.yearIntro))
                        sumIntroyear += intro
                        itemView.goal_intro.text = sumIntroyear.toString()
                    }


                    if (!goalNote.yearOneDay.isNullOrEmpty() && period == 4) {
                        val intro = (Integer.parseInt(goalNote.yearOneDay))
                        sumOneD1year += intro
                        itemView.common_goal_one_day_sem.text = sumOneD1year.toString()
                    }


                    if (!goalNote.yearTwoDay.isNullOrEmpty() && period == 4) {
                        val intro = (Integer.parseInt(goalNote.yearTwoDay))
                        sumTwoD1year += intro
                        itemView.common_goal_year_two_day_sem.text = sumTwoD1year.toString()
                    }


                    if (!goalNote.yearTWOne.isNullOrEmpty() && period == 4) {
                        val intro = (Integer.parseInt(goalNote.yearTWOne))
                        sumTwent1year += intro
                        itemView.common_goal_21_day.text = sumTwent1year.toString()
                    }


                    if (!goalNote.yearNWET.isNullOrEmpty() && period == 4) {
                        val intro = (Integer.parseInt(goalNote.yearNWET))
                        sumNwetyear += intro
                        itemView.common_goal_year_nwet.text = sumNwetyear.toString()
                    }


//month

                    if (!goalNote.monthIntro.isNullOrEmpty() && period == 3) {
                        val intro = (Integer.parseInt(goalNote.monthIntro))
                        sumIntromonth += intro
                        itemView.goal_intro.text = sumIntromonth.toString()
                    }


                    if (!goalNote.monthOneDay.isNullOrEmpty() && period == 3) {
                        val intro = (Integer.parseInt(goalNote.monthOneDay))
                        sumOneD1month += intro
                        itemView.common_goal_one_day_sem.text = sumOneD1month.toString()
                    }


                    if (!goalNote.monthTwoDay.isNullOrEmpty() && period == 3) {
                        val intro = (Integer.parseInt(goalNote.monthTwoDay))
                        sumTwoD1month += intro
                        itemView.common_goal_year_two_day_sem.text = sumTwoD1month.toString()
                    }


                    if (!goalNote.monthTWOne.isNullOrEmpty() && period == 3) {
                        val intro = (Integer.parseInt(goalNote.monthTWOne))
                        sumTwent1month += intro
                        itemView.common_goal_21_day.text = sumTwent1month.toString()
                    }


                    if (!goalNote.monthNWET.isNullOrEmpty() && period == 3) {
                        val intro = (Integer.parseInt(goalNote.monthNWET))
                        sumNwetmonth += intro
                        itemView.common_goal_year_nwet.text = sumNwetmonth.toString()
                    }


//week
                    if (!goalNote.weekIntro.isNullOrEmpty() && period == 2) {
                        val intro = (Integer.parseInt(goalNote.weekIntro))
                        sumIntroweek += intro
                        itemView.goal_intro.text = sumIntroweek.toString()
                    }


                    if (!goalNote.weekOneDay.isNullOrEmpty() && period == 2) {
                        val intro = (Integer.parseInt(goalNote.weekOneDay))
                        sumOneD1week += intro
                        itemView.common_goal_one_day_sem.text = sumOneD1week.toString()
                    }


                    if (!goalNote.weekTwoDay.isNullOrEmpty() && period == 2) {
                        val intro = (Integer.parseInt(goalNote.weekTwoDay))
                        sumTwoD1week += intro
                        itemView.common_goal_year_two_day_sem.text = sumTwoD1week.toString()
                    }


                    if (period == 2) {
                        itemView.common_goal_21_day.text = "0"
                        itemView.common_goal_year_nwet.text = "0"
                    }

//day

                    if (!goalNote.dayIntro.isNullOrEmpty() && period == 1) {
                        val intro = (Integer.parseInt(goalNote.dayIntro))
                        sumIntroDay += intro
                        itemView.goal_intro.text = sumIntroDay.toString()
                    }


                    if (!goalNote.dayOneDay.isNullOrEmpty() && period == 1) {
                        val intro = (Integer.parseInt(goalNote.dayOneDay))
                        sumOneDDay += intro
                        itemView.common_goal_one_day_sem.text = sumOneDDay.toString()
                    }


                    if (!goalNote.dayTwoDay.isNullOrEmpty() && period == 1) {
                        val intro = (Integer.parseInt(goalNote.dayTwoDay))
                        sumTwoDDay += intro
                        itemView.common_goal_year_two_day_sem.text = sumTwoDDay.toString()
                    }

                    if (period == 1) {
                        itemView.common_goal_21_day.text = "0"
                        itemView.common_goal_year_nwet.text = "0"
                    }
                }
            }
        }

        private fun commonResult(queryDocumentSnapshots: QuerySnapshot) {

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
                        val approach = Integer.parseInt(resultNote.nwet)
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

