package com.example.aleksandr.myapplication.ui.hdh

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.R.id.name
import com.example.aleksandr.myapplication.ui.hdh.model.HDHModel
import kotlinx.android.synthetic.main.item_is_word_list.view.*

class WordAdapter(private val context: Context, private val word: List<HDHModel>) : RecyclerView.Adapter<WordAdapter.VH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= VH(
        LayoutInflater.from(parent.context).inflate(R.layout.item_is_word_list, parent, false)
    )

    override fun getItemCount(): Int {
              notifyItemChanged(word.size)
        return word.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
       holder.bind(word[position])

    }

    class VH(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(hdhModel: HDHModel) {
            itemView.apply {
                name.text = hdhModel.name
                category.text = hdhModel.category
            }
        }
    }
}
