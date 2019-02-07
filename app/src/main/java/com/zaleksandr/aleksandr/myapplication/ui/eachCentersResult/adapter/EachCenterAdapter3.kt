package com.zaleksandr.aleksandr.myapplication.ui.eachCentersResult.adapter

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.zaleksandr.aleksandr.myapplication.model.City
import com.zaleksandr.aleksandr.myapplication.util.StorageUtil
import com.zaleksandr.aleksandr.tmbook.glade.GlideApp
import kotlinx.android.synthetic.main.item_each_center.view.*
import java.util.*


class EachCenterAdapter3(private val context: Context,
                         private var list: ArrayList<City>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_ITEM) {
            val v = LayoutInflater.from(parent.context).inflate(com.zaleksandr.aleksandr.myapplication.R.layout.item_each_center,
                    parent, false)
            return CenterEachHolder(v)
        } else if (viewType == TYPE_HEADER) {
            val v = LayoutInflater.from(parent.context).inflate(com.zaleksandr.aleksandr.myapplication.R.layout.item_individual_goal_and_result,
                    parent, false)
            return CenterGoalHolder(v)
        }
        throw RuntimeException("there is no type that matches the type $viewType + make sure your using types correctly")
    }


    override fun getItemCount(): Int {
        return list.size+1
    }


    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_ITEM
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
//            holder.bind2()
        }
    }

    inner class CenterEachHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {

            val item = list[position-1]
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
//        fun bind2() {
////            val item = list[position]
//        }
    }



}