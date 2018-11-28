package com.example.aleksandr.myapplication.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.model.CityData
import com.example.aleksandr.myapplication.ui.add_task.note.model.Note
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.item_goal_list.view.*

class MainAdapter(options: FirestoreRecyclerOptions<CityData>) : FirestoreRecyclerAdapter<CityData, MainAdapter.NoteHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_main_list,
                parent, false)
        return NoteHolder(v)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int, model: CityData) {
        holder.bind(model)

    }

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(note: CityData) {

            itemView.apply {
                name.text = note.centers

            }
        }
    }
}