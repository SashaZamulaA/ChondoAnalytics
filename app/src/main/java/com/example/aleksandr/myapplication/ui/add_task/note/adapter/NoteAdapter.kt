package com.example.aleksandr.myapplication.ui.add_task.note.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.ui.add_task.note.model.ResultNote
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.item_goal_list.view.*

class NoteAdapter(options: FirestoreRecyclerOptions<ResultNote>) : FirestoreRecyclerAdapter<ResultNote, NoteAdapter.NoteHolder>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_goal_list,
                parent, false)
        return NoteHolder(v)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int, model: ResultNote) {
        holder.bind(model)


    }

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

//        lateinit var imageView: ImageView

        fun bind(note: ResultNote) {
//            imageView = itemView.findViewById(R.id.image_icon)
            itemView.apply {
                name.text = note.title
                start_time.text = note.startPeriod
                end_time.text = note.endPeriod
                goal.text = note.goal

//                Picasso.with(image_icon.context)
//                        .load(note.mImageUrl)
//                        .placeholder(R.mipmap.analytics)
//                        .fit()
//                        .centerCrop()
//                        .into(image_icon)


//                percentage.text = ((note.result!!/note.goal!!)*100).toString()

            }
        }
//        var textViewTitle: TextView = itemView.findViewById(R.id.thema)
//        var textViewDescription: TextView = itemView.findViewById(R.id.main_action)
//        var textViewPriority: TextView = itemView.findViewById(R.id.goal)

    }
}