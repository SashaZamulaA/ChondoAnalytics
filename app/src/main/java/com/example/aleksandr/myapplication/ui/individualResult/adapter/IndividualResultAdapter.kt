package com.example.aleksandr.myapplication.ui.individualResult.adapter

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.model.City
import com.example.aleksandr.myapplication.ui.eachCentersResult.adapter.EachCenterAdapter
import com.example.aleksandr.myapplication.util.StorageUtil
import com.example.aleksandr.tmbook.glade.GlideApp
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.item_each_center.view.*
import kotlinx.android.synthetic.main.item_individual_result_list.view.*
import java.util.*

class IndividualResultAdapter(context: Context, options: FirestoreRecyclerOptions<City>) : FirestoreRecyclerAdapter<City, IndividualResultAdapter.VH>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndividualResultAdapter.VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_individual_result_list,
                parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int, model: City) {
        holder.apply {
            itemView.apply {
                result_city.text = model.centers
                individual_time_city.text = formatDateAndTime(model.timestamp)
            }
        }
    }

    fun deleteItem(position: Int){
        snapshots.getSnapshot(position).reference.delete()
    }


    private val dateFormatter = DateFormat.getDateFormat(context)
    private val timeFormatter = DateFormat.getTimeFormat(context)
    private fun formatDateAndTime(ts: Long): String = formatDate(ts)
    private fun formatDate(ts: Long): String = Calendar.getInstance().run { timeInMillis = ts; dateFormatter.format(time) }
//    private fun formatTime(ts: Long): String = Calendar.getInstance().run { timeInMillis = ts; timeFormatter.format(time) }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView)
}