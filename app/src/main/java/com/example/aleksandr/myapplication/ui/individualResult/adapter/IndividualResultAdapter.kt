package com.example.aleksandr.myapplication.ui.individualResult.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.model.City
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.item_individual_result_list.view.*

class IndividualResultAdapter(options: FirestoreRecyclerOptions<City>) : FirestoreRecyclerAdapter<City, IndividualResultAdapter.NoteHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_individual_result_list,
                parent, false)
        return NoteHolder(v)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int, model: City) {
        holder.bind(model)
    }

    fun deleteItem(position: Int){
        snapshots.getSnapshot(position).reference.delete()
    }

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: City) {

            itemView.apply {
                result_city.text = note.centers
                individual_time_city.text = note.time.toString()
            }
        }
    }
}