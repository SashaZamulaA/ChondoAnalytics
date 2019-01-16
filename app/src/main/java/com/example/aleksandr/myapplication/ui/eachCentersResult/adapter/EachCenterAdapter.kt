package com.example.aleksandr.myapplication.ui.eachCentersResult.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.model.City
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.item_each_center.view.*
import kotlinx.android.synthetic.main.item_individual_result_list.view.*

class EachCenterAdapter(options: FirestoreRecyclerOptions<City>) : FirestoreRecyclerAdapter<City, EachCenterAdapter.NoteHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_each_center,
                parent, false)
        return NoteHolder(v)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int, model: City) {
        holder.bind(model)
    }

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: City) {

            itemView.apply {
                each_center_intro_num.text = note.intro

            }
        }
    }
}