package com.example.aleksandr.myapplication.ui.hdh

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.ui.hdh.model.HDHModel

class ArtistList(private val context: Activity, private val wordList: ArrayList<HDHModel>) : ArrayAdapter<HDHModel>(context, R.layout.list_layout, wordList) {

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater

        val listViewItem = inflater.inflate(R.layout.list_layout, null, true)

        val textViewName = listViewItem.findViewById<View>(R.id.editText_hhw) as TextView
        val textViewCategory = listViewItem.findViewById<View>(R.id.add_category) as TextView

        val hdh = wordList[position]
        textViewName.text = hdh.name
        textViewCategory.text = hdh.category

        return listViewItem
    }

}
