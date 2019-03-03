package com.zaleksandr.aleksandr.myapplication.ui.commonResult.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.zaleksandr.aleksandr.myapplication.model.Guest
import com.zaleksandr.aleksandr.myapplication.ui.updateGuest.GuestFragment
import com.zaleksandr.aleksandr.myapplication.util.StorageUtil
import com.zaleksandr.aleksandr.tmbook.glade.GlideApp
import kotlinx.android.synthetic.main.item_individual_result_list.view.*
import kotlinx.android.synthetic.main.item_my_guest_list.view.*
import java.util.*


class MyGuestAdapter(private val context: Context,
                     private var list: ArrayList<Guest>,
                     private var fragmentCommunication: GuestFragment
//                     private val onClickEditItem: (Guest) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    var city: Guest? = null
    private var mSelectedNoteIndex: Int = 0

    override fun getItemCount(): Int {
        return list.size
    }

    private fun getItem(position: Int): Guest {
        return list[position]
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_ITEM
    }

    enum class ClickByFilter {
        MONTH, WEEK, YEAR
    }

    var period = 3
//    fun perioSelected(periodSelected: MyGuestAdapter.ClickByFilter) {
//
//        when (periodSelected) {
//
//            MyGuestAdapter.ClickByFilter.WEEK -> {
//                period = 1; notifyDataSetChanged()
//            }
//            MyGuestAdapter.ClickByFilter.MONTH -> {
//                period = 2; notifyDataSetChanged()
//            }
//            MyGuestAdapter.ClickByFilter.YEAR -> {
//                period = 3; notifyDataSetChanged()
//            }
//        }
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(com.zaleksandr.aleksandr.myapplication.R.layout.item_my_guest_list,
                parent, false)
        return IndividualHolder(v, fragmentCommunication)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is IndividualHolder) {
            holder.bind()
        }
    }

    inner class IndividualHolder(itemView: View, fragmentCommunication: GuestFragment) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        override fun onClick(v: View?) {
            mSelectedNoteIndex = adapterPosition
            fragmentCommunication.respond(list[mSelectedNoteIndex])

        }

        init {
            itemView.setOnClickListener(this)
        }


        fun bind() {
            val item = list[position]
//            itemView.individual_result_city.text = item.centers
//            itemView.individual_time_city.text = formatDateAndTime(item.timestamp)
            if (!item.name.isNullOrEmpty()) itemView.guest_item_name.text = item.name else itemView.guest_item_name.text = "Mister X"
            if (!item.phoneNum.isNullOrEmpty()) itemView.my_guest_phone.text = item.phoneNum else itemView.my_guest_phone.text = "Empty"

            if (!item.photo.isNullOrBlank()) {
                GlideApp.with(context)
                        .load(item.photo.let { StorageUtil.pathToReference(it) })
                        .circleCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(itemView.guest_profile_picture)
            }

        }
    }

    //    fun updateList(list: ArrayList<City>) {
//        this.list = list
//        notifyDataSetChanged()
//    }
    interface FragmentCommunication {
        fun respond(city: Guest)
    }
}
