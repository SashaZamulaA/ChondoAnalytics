package com.example.aleksandr.myapplication.ui.hdh

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.ui.hdh.model.HDHModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class WordAdapter(options: FirestoreRecyclerOptions<HDHModel>) : FirestoreRecyclerAdapter<HDHModel, WordAdapter.NoteHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
    val v = LayoutInflater.from(parent.context).inflate(R.layout.item_is_word_list, parent, false)
        return NoteHolder(v)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int, model: HDHModel) {
        holder.textViewTitle.text = model.id
        holder.textViewDescription.text = model.name
        holder.textViewPriority.text = model.category
    }


    class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textViewTitle: TextView
        var textViewDescription: TextView
        var textViewPriority: TextView

        init {
            textViewTitle = itemView.findViewById(R.id.title_id)
            textViewDescription = itemView.findViewById(R.id.word)
            textViewPriority = itemView.findViewById(R.id.category)
        }
    }
}



