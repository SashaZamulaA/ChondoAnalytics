package com.example.aleksandr.myapplication.ui.main.adapter
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import android.content.Context
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.model.City
import kotlinx.android.synthetic.main.item_main_list.*

class PersonItem(val city: City,
                 val userId: String,
                 private val context: Context)
    : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.main_city.text = city.nameCity


    }

    override fun getLayout() = R.layout.item_main_list

}
