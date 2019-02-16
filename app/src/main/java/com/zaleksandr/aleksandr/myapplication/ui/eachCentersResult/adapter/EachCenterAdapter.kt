package com.zaleksandr.aleksandr.myapplication.ui.eachCentersResult.adapter

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.QuerySnapshot
import com.zaleksandr.aleksandr.myapplication.model.City
import com.zaleksandr.aleksandr.myapplication.model.GoalCenter
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import com.zaleksandr.aleksandr.myapplication.util.StorageUtil
import com.zaleksandr.aleksandr.myapplication.util.clickByFilterCommonResultForEachCenter
import com.zaleksandr.aleksandr.myapplication.util.clickByFilterForEachCenterGoal
import com.zaleksandr.aleksandr.tmbook.glade.GlideApp
import kotlinx.android.synthetic.main.item_each_center.view.*
import kotlinx.android.synthetic.main.item_individual_goal_and_result_for_center.view.*
import java.util.*


class EachCenterAdapter(private val context: Context,
                        private var list: ArrayList<City>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val noteRefCollection = firestoreInstance.collection("NewCity")
    val goleCenterRefCollection = firestoreInstance.collection("GoalCenters")
    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1
    var cityNumber: Int = 0
    var model: GoalCenter? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_ITEM) {
            val v = LayoutInflater.from(parent.context).inflate(com.zaleksandr.aleksandr.myapplication.R.layout.item_each_center,
                    parent, false)
            return CenterEachHolder(v)
        } else if (viewType == TYPE_HEADER) {
            val v = LayoutInflater.from(parent.context).inflate(com.zaleksandr.aleksandr.myapplication.R.layout.item_individual_goal_and_result_for_center,
                    parent, false)
            return CenterGoalHolder(v)
        }
        throw RuntimeException("there is no type that matches the type $viewType + make sure your using types correctly")
    }


    override fun getItemCount(): Int {
        return list.size + 1
    }


    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_ITEM
    }

    fun getNumber(cityNumber: Int): Int {
        this.cityNumber = cityNumber
        return cityNumber
    }

    enum class ClickByFilter {
        MONTH, WEEK, YEAR
    }

    var period = 0
    fun perioSelected(periodSelected: EachCenterAdapter.ClickByFilter) {

        when (periodSelected) {

            EachCenterAdapter.ClickByFilter.WEEK -> {
                period = 2; notifyDataSetChanged()
            }
            EachCenterAdapter.ClickByFilter.MONTH -> {
                period = 3; notifyDataSetChanged()
            }
            EachCenterAdapter.ClickByFilter.YEAR -> {
                period = 4; notifyDataSetChanged()
            }
        }
    }


    private val dateFormatter = DateFormat.getDateFormat(context)
    private val timeFormatter = DateFormat.getTimeFormat(context)

    private fun formatDateAndTime(ts: Long): String = formatDate(ts)
    private fun formatDate(ts: Long): String = Calendar.getInstance().run { timeInMillis = ts; dateFormatter.format(time) }
//    private fun formatTime(ts: Long): String = Calendar.getInstance().run { timeInMillis = ts; timeFormatter.format(time) }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is CenterEachHolder) {
            holder.bind()
        } else if (holder is CenterGoalHolder) {
            holder.bind2()
        }
    }

    inner class CenterEachHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {

            val item = list[position - 1]
            if (!item.userPhotoPath.isNullOrBlank()) {
                GlideApp.with(context)
                        .load(item.userPhotoPath.let { StorageUtil.pathToReference(it) })
                        .circleCrop()
                        .into(itemView.imageView_profile_picture)
            }
            itemView.each_center_name.text = item.name
            itemView.each_center_time.text = formatDateAndTime(item.timestamp)
            itemView.each_center_intro.text = if (!item.intro.isNullOrBlank()) item.intro else "0"
            itemView.each_center_one_day_edittext.text = if (!item.onedayWS.isNullOrBlank()) item.onedayWS else "0"
            itemView.each_center_two_day.text = if (!item.twoDayWS.isNullOrBlank()) item.twoDayWS else "0"
            itemView.each_center_21_day.text = if (!item.twOneDay.isNullOrBlank()) item.twOneDay else "0"
            itemView.each_center_nwet.text = if (!item.nwet.isNullOrBlank()) item.nwet else "0"
            itemView.each_center_mmbk.text = if (!item.mmbk.isNullOrBlank()) item.mmbk else "0"
        }
    }

    inner class CenterGoalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind2() {

            clickByFilterCommonResultForEachCenter(noteRefCollection, getNumber(cityNumber), period).addOnSuccessListener { queryDocumentSnapshots ->
                commonResult(queryDocumentSnapshots)
            }

            clickByFilterForEachCenterGoal(goleCenterRefCollection, getNumber(cityNumber), period).addOnSuccessListener { queryDocumentSnapshots ->
                commonGoal(queryDocumentSnapshots)
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
                        val nwet = Integer.parseInt(resultNote.nwet)
                        sumNwet += nwet
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
                itemView.common_one_d_result.text = "0"
                itemView.common_two_d_result.text = "0"
                itemView.common_tw_one_d_result.text = "0"
                itemView.common_nwet_result.text = "0"
            }
        }


        fun commonGoal(queryDocumentSnapshots: QuerySnapshot) {
            if (!queryDocumentSnapshots.isEmpty) {
                queryDocumentSnapshots.forEach { documentSnapshot ->

                    val goalNote = documentSnapshot.toObject(GoalCenter::class.java)

//year
                    if (!goalNote.yearIntroCenter.isNullOrEmpty() && period == 4) {
                        itemView.goal_intro_center.text = goalNote.yearIntroCenter
                    }
                    if (!goalNote.yearOneDayCenter.isNullOrEmpty() && period == 4) {
                        itemView.goal_one_day_sem_center.text = goalNote.yearOneDayCenter
                    }
                    if (!goalNote.yearTwoDayCenter.isNullOrEmpty() && period == 4) {
                        itemView.goal_two_day_sem_center.text = goalNote.yearTwoDayCenter
                    }
                    if (!goalNote.yearTWOneCenter.isNullOrEmpty() && period == 4) {
                        itemView.goal_21_day_center.text = goalNote.yearTWOneCenter
                    }
                    if (!goalNote.yearNWETCenter.isNullOrEmpty() && period == 4) {
                        itemView.goal_year_nwet_center.text = goalNote.yearNWETCenter
                    }

//month
                    if (!goalNote.monthIntroCenter.isNullOrEmpty() && period == 3) {
                        itemView.goal_intro_center.text = goalNote.monthIntroCenter
                    }
                    if (!goalNote.monthOneDayCenter.isNullOrEmpty() && period == 3) {
                        itemView.goal_one_day_sem_center.text = goalNote.monthOneDayCenter
                    }
                    if (!goalNote.monthTwoDayCenter.isNullOrEmpty() && period == 3) {
                        itemView.goal_two_day_sem_center.text = goalNote.monthTwoDayCenter
                    }
                    if (!goalNote.monthActionCenter.isNullOrEmpty() && period == 3) {
                        itemView.goal_21_day_center.text = goalNote.monthActionCenter
                    }

//week
                    if (!goalNote.weekIntroCenter.isNullOrEmpty() && period == 2) {
                        itemView.goal_intro_center.text = goalNote.weekIntroCenter
                    }
                    if (!goalNote.weekOneDayCenter.isNullOrEmpty() && period == 2) {
                        itemView.goal_one_day_sem_center.text = goalNote.weekOneDayCenter
                    }
                    if (!goalNote.weekTwoDayCenter.isNullOrEmpty() && period == 2) {
                        itemView.goal_two_day_sem_center.text = goalNote.weekTwoDayCenter

                    }
                    if (period == 2) {
                        itemView.goal_21_day_center.text = "0"
                        itemView.goal_year_nwet_center.text = "0"
                    }
                }
            }
        }
    }
}