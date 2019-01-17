package com.example.aleksandr.myapplication.ui.eachCentersResult.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.model.City
import com.example.aleksandr.myapplication.ui.eachCentersResult.adapter.EachCenterAdapter.VH
import com.example.aleksandr.myapplication.util.StorageUtil
import com.example.aleksandr.tmbook.glade.GlideApp
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.item_each_center.view.*


class EachCenterAdapter(private val context: Context,
        options: FirestoreRecyclerOptions<City>) : FirestoreRecyclerAdapter<City, VH>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_each_center,
                parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int, model: City) {

        holder.apply {
            if (!model.userPhotoPath.isNullOrBlank()) {
                GlideApp.with(context)
                        .load(model.userPhotoPath.let { StorageUtil.pathToReference(it) })
                        .circleCrop()
                        .into(itemView.imageView_profile_picture)
            }
            itemView.each_center_intro_num.text = model.intro

        }
    }

   class VH(itemView: View) : RecyclerView.ViewHolder(itemView)

    }
