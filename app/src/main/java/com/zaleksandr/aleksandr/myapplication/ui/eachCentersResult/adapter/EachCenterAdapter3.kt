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
import com.zaleksandr.aleksandr.myapplication.util.clickByFilterForEachCenter
import com.zaleksandr.aleksandr.tmbook.glade.GlideApp
import kotlinx.android.synthetic.main.item_each_center.view.*
import kotlinx.android.synthetic.main.item_individual_goal_and_result_for_center.view.*
import java.util.*


class EachCenterAdapter3(private val context: Context,
                         private var list: ArrayList<City>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val notesCollectionRef = firestoreInstance.collection("NewCity")
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
        return cityNumber + 1
    }

    enum class ClickByFilter {
        MONTH, WEEK, YEAR
    }

    var period = 0
    fun perioSelected(periodSelected: EachCenterAdapter3.ClickByFilter) {

        when (periodSelected) {

            EachCenterAdapter3.ClickByFilter.WEEK -> {
                period = 2; notifyDataSetChanged()
            }
            EachCenterAdapter3.ClickByFilter.MONTH -> {
                period = 3; notifyDataSetChanged()
            }
            EachCenterAdapter3.ClickByFilter.YEAR -> {
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

            clickByFilterForEachCenter(goleCenterRefCollection, getNumber(cityNumber), period).addOnSuccessListener { queryDocumentSnapshots ->
                commonGoal(queryDocumentSnapshots)
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
                    if (period == 2)
                    itemView.goal_21_day_center.text = "0"
                    itemView.goal_year_nwet_center.text = "0"

                }
//                if (!goalNote?.yearOneDay.isNullOrEmpty() && period == 4) {
//                    val intro = (Integer.parseInt(goalNote?.yearOneDay))
//                    sumOneD1year += intro
//                    itemView.common_goal_one_day_sem.text = sumOneD1year.toString()
//                }
//
//
//                if (!goalNote?.yearTwoDay.isNullOrEmpty() && period == 4) {
//                    val intro = (Integer.parseInt(goalNote?.yearTwoDay))
//                    sumTwoD1year += intro
//                    itemView.common_goal_year_two_day_sem.text = sumTwoD1year.toString()
//                }
//
//
//                if (!goalNote?.yearTWOne.isNullOrEmpty() && period == 4) {
//                    val intro = (Integer.parseInt(goalNote?.yearTWOne))
//                    sumTwent1year += intro
//                    itemView.common_goal_21_day.text = sumTwent1year.toString()
//                }
//
//
//                if (!goalNote?.yearNWET.isNullOrEmpty() && period == 4) {
//                    val intro = (Integer.parseInt(goalNote?.yearNWET))
//                    sumNwetyear += intro
//                    itemView.common_goal_year_nwet.text = sumNwetyear.toString()
//                }
//
//
////month
//
//                if (!goalNote?.monthIntro.isNullOrEmpty() && period == 3) {
//                    val intro = (Integer.parseInt(goalNote?.monthIntro))
//                    sumIntromonth += intro
//                    itemView.goal_intro.text = sumIntromonth.toString()
//                }
//
//
//                if (!goalNote?.monthOneDay.isNullOrEmpty() && period == 3) {
//                    val intro = (Integer.parseInt(goalNote?.monthOneDay))
//                    sumOneD1month += intro
//                    itemView.common_goal_one_day_sem.text = sumOneD1month.toString()
//                }
//
//
//                if (!goalNote?.monthTwoDay.isNullOrEmpty() && period == 3) {
//                    val intro = (Integer.parseInt(goalNote?.monthTwoDay))
//                    sumTwoD1month += intro
//                    itemView.common_goal_year_two_day_sem.text = sumTwoD1month.toString()
//                }
//
//
//                if (!goalNote?.monthTWOne.isNullOrEmpty() && period == 3) {
//                    val intro = (Integer.parseInt(goalNote?.monthTWOne))
//                    sumTwent1month += intro
//                    itemView.common_goal_21_day.text = sumTwent1month.toString()
//                }
//
//
//                if (!goalNote?.monthNWET.isNullOrEmpty() && period == 3) {
//                    val intro = (Integer.parseInt(goalNote?.monthNWET))
//                    sumNwetmonth += intro
//                    itemView.common_goal_year_nwet.text = sumNwetmonth.toString()
//                }
//
//
////week
//                if (!goalNote?.weekIntro.isNullOrEmpty() && period == 2) {
//                    val intro = (Integer.parseInt(goalNote?.weekIntro))
//                    sumIntroweek += intro
//                    itemView.goal_intro.text = sumIntroweek.toString()
//                }
//
//
//                if (!goalNote?.weekOneDay.isNullOrEmpty() && period == 2) {
//                    val intro = (Integer.parseInt(goalNote?.weekOneDay))
//                    sumOneD1week += intro
//                    itemView.common_goal_one_day_sem.text = sumOneD1week.toString()
//                }
//
//
//                if (!goalNote?.weekTwoDay.isNullOrEmpty() && period == 2) {
//                    val intro = (Integer.parseInt(goalNote?.weekTwoDay))
//                    sumTwoD1week += intro
//                    itemView.common_goal_year_two_day_sem.text = sumTwoD1week.toString()
//                }
//
//
//                if (period == 2) {
//                    itemView.common_goal_21_day.text = "0"
//                    itemView.common_goal_year_nwet.text = "0"
//                }


            }

//            val itemCenterGoal = listCenterGoal[position]
//
//            if (period == 4) {
//                itemView.goal_intro_center.text = itemCenterGoal?.yearIntroCenter
//                itemView.goal_one_day_sem_center.text = itemCenterGoal?.yearOneDayCenter
//                itemView.goal_year_two_day_sem_center.text = itemCenterGoal?.yearTwoDayCenter
//                itemView.goal_21_day_center.text = itemCenterGoal?.yearTWOneCenter
//                itemView.goal_year_nwet_center.text = itemCenterGoal?.yearNWETCenter
//            } else
//            if (period == 3) {
//                itemView.goal_intro_center.text = itemCenterGoal?.monthIntroCenter
//                itemView.goal_one_day_sem_center.text = itemCenterGoal?.monthOneDayCenter
//                itemView.goal_year_two_day_sem_center.text = itemCenterGoal?.monthTwoDayCenter
//                itemView.goal_21_day_center.text = itemCenterGoal?.monthActionCenter
//            } else
//            if (period == 2) {
//                itemView.goal_intro_center.text = itemCenterGoal?.weekIntroCenter
//                itemView.goal_one_day_sem_center.text = itemCenterGoal?.weekOneDayCenter
//                itemView.goal_year_two_day_sem_center.text = itemCenterGoal?.weekTwoDayCenter
//            }
        }
    }
}