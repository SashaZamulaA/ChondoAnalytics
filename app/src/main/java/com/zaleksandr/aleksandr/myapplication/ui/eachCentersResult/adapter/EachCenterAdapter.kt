package com.zaleksandr.aleksandr.myapplication.ui.eachCentersResult.adapter

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaleksandr.aleksandr.myapplication.R
import com.zaleksandr.aleksandr.myapplication.model.City
import com.zaleksandr.aleksandr.myapplication.ui.eachCentersResult.adapter.EachCenterAdapter.VH
import com.zaleksandr.aleksandr.myapplication.util.StorageUtil
import com.zaleksandr.aleksandr.tmbook.glade.GlideApp
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.item_each_center.view.*
import java.util.*


class EachCenterAdapter(private val context: Context,
                        options: FirestoreRecyclerOptions<City>) : FirestoreRecyclerAdapter<City, VH>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_each_center,
                parent, false)
        return VH(v)
    }

    private val dateFormatter = DateFormat.getDateFormat(context)
    private val timeFormatter = DateFormat.getTimeFormat(context)

    private fun formatDateAndTime(ts: Long): String = formatDate(ts)
    private fun formatDate(ts: Long): String = Calendar.getInstance().run { timeInMillis = ts; dateFormatter.format(time) }
//    private fun formatTime(ts: Long): String = Calendar.getInstance().run { timeInMillis = ts; timeFormatter.format(time) }

    override fun onBindViewHolder(holder: VH, position: Int, model: City) {

        holder.apply {
            if (!model.userPhotoPath.isNullOrBlank()) {
                GlideApp.with(context)
                        .load(model.userPhotoPath.let { StorageUtil.pathToReference(it) })
                        .circleCrop()
                        .into(itemView.imageView_profile_picture)
            }
            itemView.apply {
                each_center_name.text = model.name
                each_center_time.text = formatDateAndTime(model.timestamp)
                each_center_intro.text =  if (!model.intro.isNullOrBlank()) model.intro else "0"
                each_center_one_day_edittext.text =  if (!model.onedayWS.isNullOrBlank()) model.onedayWS else "0"
                each_center_two_day.text = if (!model.twoDayWS.isNullOrBlank()) model.twoDayWS else "0"
                each_center_21_day.text =  if (!model.twOneDay.isNullOrBlank()) model.twOneDay else "0"
                each_center_nwet.text = if (!model.nwet.isNullOrBlank()) model.nwet else "0"
                each_center_mmbk.text = if(!model.mmbk.isNullOrBlank()) model.mmbk else "0"

            }
        }
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView)

}