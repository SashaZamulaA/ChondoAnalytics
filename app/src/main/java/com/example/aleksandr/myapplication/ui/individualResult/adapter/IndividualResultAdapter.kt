package com.example.aleksandr.myapplication.ui.individualResult.adapter

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aleksandr.myapplication.model.City
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.item_individual_result_list.view.*
import java.util.*


class IndividualResultAdapter(context: Context, options: FirestoreRecyclerOptions<City>) : FirestoreRecyclerAdapter<City, IndividualResultAdapter.NoteHolder>(options) {


    override fun onBindViewHolder(holder: NoteHolder, position: Int, model: City) {
        holder.apply {
            itemView.apply {
                result_city.text = model.centers
                individual_time_city.text = formatDateAndTime(model.timestamp)
                individual_intro.text = model.intro
                individual_one_day.text = model.onedayWS
                individual_two_day.text = model.twoDayWS
                individual_21_day.text = model.twOneDay
                individual_time_str.text = model.timeStr
                individual_approach.text = model.approach
                individual_street_lect.text = model.lectOnStr
                individual_lect_center.text = model.lectCentr
         }
        }
            }

    private val dateFormatter = DateFormat.getDateFormat(context)
    private val timeFormatter = DateFormat.getTimeFormat(context)
    private fun formatDateAndTime(ts: Long): String = formatDate(ts)
    private fun formatDate(ts: Long): String = Calendar.getInstance().run { timeInMillis = ts; dateFormatter.format(time) }
    private fun formatTime(ts: Long): String = Calendar.getInstance().run { timeInMillis = ts; timeFormatter.format(time) }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
                val v = LayoutInflater.from(parent.context).inflate(com.example.aleksandr.myapplication.R.layout.item_individual_result_list,
                        parent, false)
                return NoteHolder(v)
            }

            fun deleteItem(position: Int) {
                snapshots.getSnapshot(position).reference.delete()
            }

            class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
        }
