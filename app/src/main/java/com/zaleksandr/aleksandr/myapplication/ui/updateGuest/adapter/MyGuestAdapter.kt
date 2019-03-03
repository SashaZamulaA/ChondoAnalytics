package com.zaleksandr.aleksandr.myapplication.ui.commonResult.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaleksandr.aleksandr.myapplication.model.Guest
import com.zaleksandr.aleksandr.myapplication.ui.updateGuest.GuestFragment
import kotlinx.android.synthetic.main.item_my_guest_list.view.*
import java.util.*


class MyGuestAdapter(context: Context,
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
    fun perioSelected(periodSelected: MyGuestAdapter.ClickByFilter) {

        when (periodSelected) {

            MyGuestAdapter.ClickByFilter.WEEK -> {
                period = 1; notifyDataSetChanged()
            }
            MyGuestAdapter.ClickByFilter.MONTH -> {
                period = 2; notifyDataSetChanged()
            }
            MyGuestAdapter.ClickByFilter.YEAR -> {
                period = 3; notifyDataSetChanged()
            }
        }
    }

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

    inner class IndividualHolder(itemView: View, fragmentCommunication: GuestFragment) : RecyclerView.ViewHolder(itemView), View.OnLongClickListener {

        override fun onLongClick(v: View?): Boolean {
            mSelectedNoteIndex = adapterPosition
            fragmentCommunication.respond(list[mSelectedNoteIndex])
            return false
        }

        init {
            itemView.setOnLongClickListener(this)
        }


        fun bind() {
            val item = list[position]
//            itemView.individual_result_city.text = item.centers
//            itemView.individual_time_city.text = formatDateAndTime(item.timestamp)
            if (!item.name.isNullOrEmpty()) itemView.guest_item_name.text = item.name else itemView.guest_item_name.text = "Mister X"
//            if (!item.intro.isNullOrEmpty()) itemView.individual_result_intro.text = item.intro else itemView.individual_result_intro.text = "0"
//            if (!item.onedayWS.isNullOrEmpty()) itemView.individual_result_one_day.text = item.onedayWS else itemView.individual_result_one_day.text = "0"
//            if (!item.twoDayWS.isNullOrEmpty()) itemView.individual_result_two_day.text = item.twoDayWS else itemView.individual_result_two_day.text = "0"
//            if (!item.actionaiser.isNullOrEmpty()) itemView.individual_result_actioniser.text = item.actionaiser else itemView.individual_result_actioniser.text = "0"
//            if (!item.twOneDay.isNullOrEmpty()) itemView.individual_result_21_day.text = item.twOneDay else itemView.individual_result_21_day.text = "0"
//            if (!item.timeStr.isNullOrEmpty()) itemView.individual_result_time_str.text = item.timeStr else itemView.individual_result_time_str.text = "0"
//            if (!item.approach.isNullOrEmpty()) itemView.individual_result_approach.text = item.approach else itemView.individual_result_approach.text = "0"
//            if (!item.lectTraining.isNullOrEmpty()) itemView.individual_result_train_lect.text = item.lectTraining else itemView.individual_result_train_lect.text = "0"
//            if (!item.lectOnStr.isNullOrEmpty()) itemView.individual_result_street_lect.text = item.lectOnStr else itemView.individual_result_street_lect.text = "0"
//            if (!item.lectOnStr.isNullOrEmpty()) itemView.individual_result_lect_center.text = item.lectCentr else itemView.individual_result_lect_center.text = "0"
//            if (!item.nwet.isNullOrEmpty()) itemView.ind_result_nwet_num.text = item.nwet else itemView.ind_result_nwet_num.text = "0"
////            if (!item.dpKor.isNullOrEmpty()) itemView.individual_result_dp_kor.text = item.dpKor else itemView.individual_result_dp_kor.text = "0"
////            if (!item.dp.isNullOrEmpty()) itemView.ind_result_read_dp_num.text = item.dp else itemView.ind_result_read_dp_num.text = "0"
//            if (!item.mmbk.isNullOrEmpty()) itemView.individual_result_mmbk.text = item.mmbk else itemView.individual_result_mmbk.text = "0"
//            if (!item.eduMat.isNullOrEmpty()) itemView.individual_result_edu_mat.text = item.eduMat else itemView.individual_result_edu_mat.text = "0"

//            itemView.setOnClickListener { onClickEditItem(item) }

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
