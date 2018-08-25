package com.example.aleksandr.myapplication.ui.hdh

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.ui.hdh.model.HDHModel

class ArtistList(private val context: Activity, private val artists: List<HDHModel>) : ArrayAdapter<HDHModel>(context, R.layout.item_is_word_list, artists) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val listViewItem = inflater.inflate(R.layout.item_is_word_list, null, true)

        val textViewName = listViewItem.findViewById<TextView>(R.id.name)
        val textViewGenre = listViewItem.findViewById<TextView>(R.id.category)

        val artist = artists[position]
        textViewName.text = artist.name
        textViewGenre.text = artist.category

        return listViewItem
    }
}