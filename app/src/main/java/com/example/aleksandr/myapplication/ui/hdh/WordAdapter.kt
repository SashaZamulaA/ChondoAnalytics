package com.example.aleksandr.myapplication.ui.hdh

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.RelativeLayout
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.R.id.name
import com.example.aleksandr.myapplication.ui.hdh.model.HDHModel
import kotlinx.android.synthetic.main.item_is_word_list.view.*

class WordAdapter(private val wordList: List<HDHModel>,
                  private val context: Context) : RecyclerView.Adapter<WordAdapter.VH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_is_word_list, parent, false))

    override fun getItemCount(): Int {
        return wordList.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val singleWord = wordList[position]
        holder.bind(wordList[position])

    }

    class VH(view: View?) : RecyclerView.ViewHolder(view) {

//        val listView = view?.findViewById<RelativeLayout>(R.id.listViewWord)

        fun bind(hhwHDHModel: HDHModel) {
            itemView.apply {

                name.text = hhwHDHModel.name
                category.text = hhwHDHModel.category

            }
        }
    }
}