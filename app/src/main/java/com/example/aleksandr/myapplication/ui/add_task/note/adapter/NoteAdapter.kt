package com.example.aleksandr.myapplication.ui.add_task.note.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.ui.add_task.note.model.Note
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.item_goal_list.view.*

class NoteAdapter(options: FirestoreRecyclerOptions<Note>) : FirestoreRecyclerAdapter<Note, NoteAdapter.NoteHolder>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_goal_list,
                parent, false)
        return NoteHolder(v)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int, model: Note) {
     holder.bind(model)
//        holder.textViewTitle.text = model.title
//        holder.textViewDescription.text = model.result.toString()
//        holder.textViewPriority.text = model.goal.toString()
    }

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(note: Note) {
            itemView.apply {

                name.text = note.title
                result_count.text = note.result.toString()
                goal.text = note.goal.toString()
                start_time.text = note.start_period
                end_time.text = note.end_period
//                percentage.text = ((note.result!!/note.goal!!)*100).toString()

            }
        }
//        var textViewTitle: TextView = itemView.findViewById(R.id.thema)
//        var textViewDescription: TextView = itemView.findViewById(R.id.main_action)
//        var textViewPriority: TextView = itemView.findViewById(R.id.goal)

    }
}