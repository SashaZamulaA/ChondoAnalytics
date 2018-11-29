package com.example.aleksandr.myapplication.ui.add_task.note.adapter
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.ui.add_task.note.model.ResultNote

class ImageAdapter(context:Context, uploads:List<ResultNote>):RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    override fun getItemCount() = mUploads.size

    private val mContext:Context = context
    private val mUploads:List<ResultNote> = uploads

    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):ImageViewHolder {
        val v = LayoutInflater.from(mContext).inflate(R.layout.item_goal_list, parent, false)
        return ImageViewHolder(v)
    }
   override fun onBindViewHolder(holder:ImageViewHolder, position:Int) {
        val uploadCurrent = mUploads[position]
       holder.textViewName.text = uploadCurrent.goal
//        Picasso.with(mContext)
//                .load(uploadCurrent.mImageUrl)
//                .placeholder(R.mipmap.ic_launcher)
//                .fit()
//                .centerCrop()
//                .into(holder.imageView)
    }
    inner class ImageViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        var textViewName:TextView = itemView.findViewById(R.id.name)
        var imageView:ImageView = itemView.findViewById(R.id.image_icon)
    }
}