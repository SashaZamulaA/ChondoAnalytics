package com.example.aleksandr.myapplication.ui.main.adapter

import android.content.Context
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.model.CityUkraine
import com.example.aleksandr.myapplication.util.StorageUtil
import com.example.aleksandr.tmbook.glade.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_main_list.*
import kotlinx.android.synthetic.main.view_settings.*

//
//class PersonItem(val person: CityUkraine,
//                 val userId: String,
//                 private val context: Context)
//    : Item() {
//
//    var listPaymentTypes: List<CityType> = ArrayList()
//    override fun bind(viewHolder: ViewHolder, position: Int) {
//        val cityType = listPaymentTypes[position]
//
//
//        viewHolder.result_city.text = cityType.method
//        viewHolder.settings_owner_city.text = cityType.method
//        viewHolder.textView_bio.text = person.bio
//        if(person.profilePicturePath != null)
//            GlideApp.with(context)
//                    .load(StorageUtil.pathToReference(person.profilePicturePath))
//                    .placeholder(R.drawable.ic_account_circle_black_24dp)
//                    .into(viewHolder.imageView_profile_picture)
//    }
//    enum class CityType(val method: String) {
//        KYIV("KYIV"),
//        KHARKIV("KHARKIV"),
//        LVIV("LVIV"),
//        DNEPR("DNEPR"),
//        ZHYTOMYR("ZHYTOMYR"),
//        ODESSA("ODESSA"),
//        CHERNIGOV("CHERNIGOV"),
//    }
//
////    companion object {
////        private const val KYIV = R.string.kiev
////        private const val KHARKIV = R.string.kharkiv
////        private const val LVIV = R.string.lviv
////        private const val DNEPR = R.string.dnepr
////        private const val ZHYTOMYR = R.string.zhytomyr
////        private const val ODESSA = R.string.odessa
////        private const val CHERNIGOV = R.string.chernigov
////    }
//}
