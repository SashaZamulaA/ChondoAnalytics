package com.zaleksandr.aleksandr.myapplication.ui.commonResult.adapter

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaleksandr.aleksandr.myapplication.R
import com.zaleksandr.aleksandr.myapplication.model.City
import com.zaleksandr.aleksandr.myapplication.ui.individualResult.IndividualResultFragment
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import kotlinx.android.synthetic.main.item_common_result_list.view.*
import kotlinx.android.synthetic.main.item_individual_result_list.view.*
import java.util.*

class IndividualAdapter(context: Context,
                        private var list: ArrayList<City>,
                        private var fragmentCommunication: IndividualResultFragment) : RecyclerView.Adapter<IndividualAdapter.CityHolder>() {


    var city: City? = null
    private var mSelectedNoteIndex: Int = 0
    override fun getItemCount(): Int {
        return list.size
    }

    private val noteRefCollection = firestoreInstance.collection("NewCity")

    enum class ClickByFilter {
        DAY, MONTH, WEEK, YEAR
    }

    var period = 0
    fun perioSelected(periodSelected: IndividualAdapter.ClickByFilter) {

        when (periodSelected) {
            CommonResultAdapter.ClickByFilter.DAY -> {
                period = 1; notifyDataSetChanged()
            }
            CommonResultAdapter.ClickByFilter.WEEK -> {
                period = 2; notifyDataSetChanged()
            }
            CommonResultAdapter.ClickByFilter.MONTH -> {
                period = 3; notifyDataSetChanged()
            }
            CommonResultAdapter.ClickByFilter.YEAR -> {
                period = 4; notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_individual_result_list,
                parent, false)
        return CityHolder(v, fragmentCommunication)
    }

    override fun onBindViewHolder(holder: IndividualAdapter.CityHolder, position: Int) {
        val item = list[position]
        holder.apply {
            itemView.individual_result_city.text = item.centers
            itemView.individual_time_city.text = formatDateAndTime(item.timestamp)
            if (!item.intro.isNullOrEmpty()) itemView.individual_result_intro.text = item.intro else itemView.individual_result_intro.text = "0"
            if (!item.onedayWS.isNullOrEmpty()) itemView.individual_result_one_day.text = item.onedayWS else itemView.individual_result_one_day.text = "0"
            if (!item.twoDayWS.isNullOrEmpty()) itemView.individual_result_two_day.text = item.twoDayWS else itemView.individual_result_two_day.text = "0"
            if (!item.twOneDay.isNullOrEmpty()) itemView.individual_result_21_day.text = item.twOneDay else itemView.individual_result_21_day.text = "0"
            if (!item.timeStr.isNullOrEmpty()) itemView.individual_result_time_str.text = item.timeStr else itemView.individual_result_time_str.text = "0"
            if (!item.approach.isNullOrEmpty()) itemView.individual_result_approach.text = item.approach else itemView.individual_result_approach.text = "0"
            if (!item.lectOnStr.isNullOrEmpty()) itemView.individual_result_street_lect.text = item.lectOnStr else itemView.individual_result_street_lect.text = "0"
            if (!item.lectOnStr.isNullOrEmpty()) itemView.individual_result_lect_center.text = item.lectCentr else itemView.individual_result_lect_center.text = "0"
            if (!item.nwet.isNullOrEmpty()) itemView.ind_result_nwet_num.text = item.nwet else itemView.ind_result_nwet_num.text = "0"
            if (!item.dp.isNullOrEmpty()) itemView.ind_result_read_dp_num.text = item.dp else itemView.ind_result_read_dp_num.text = "0"
            if (!item.telCont.isNullOrEmpty()) itemView.individual_result_contacts.text = item.telCont else itemView.individual_result_contacts.text = "0"
            if (!item.mmbk.isNullOrEmpty()) itemView.individual_result_mmbk.text = item.mmbk else itemView.individual_result_mmbk.text = "0"
        }
    }

    fun removeNote(city: City) {
        list.remove(city)
        notifyDataSetChanged()
    }

    private val dateFormatter = DateFormat.getDateFormat(context)
    private val timeFormatter = DateFormat.getTimeFormat(context)
    private fun formatDateAndTime(ts: Long): String = formatDate(ts)
    private fun formatDate(ts: Long): String = Calendar.getInstance().run { timeInMillis = ts; dateFormatter.format(time) }
    private fun formatTime(ts: Long): String = Calendar.getInstance().run { timeInMillis = ts; timeFormatter.format(time) }

//    fun updateList(list: ArrayList<City>) {
//        this.list = list
//        notifyDataSetChanged()
//    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    inner class CityHolder(itemView: View, var fragmentCommunication: IndividualResultFragment) : RecyclerView.ViewHolder(itemView), View.OnLongClickListener {
        override fun onLongClick(v: View?): Boolean {
            mSelectedNoteIndex = adapterPosition
            fragmentCommunication.respond(list[mSelectedNoteIndex])
            return false
        }

        init {
            itemView.setOnLongClickListener(this)
        }

//        override fun onClick(v: View?) {
//            mSelectedNoteIndex = adapterPosition
//            fragmentCommunication.respond(list[mSelectedNoteIndex])
//        }

//        fun bind() {
//
//            noteRefCollection.
//                    whereEqualTo("id", if ("${FirebaseAuth.getInstance().uid}" == FirebaseAuth.getInstance().currentUser!!.uid) {
//                FirebaseAuth.getInstance().uid
//            } else null)
//                    .orderBy("time", Query.Direction.DESCENDING).get().addOnSuccessListener { queryDocumentSnapshots ->
//                        cityName(queryDocumentSnapshots)
//                    }
//        }
//
//        private fun cityName(queryDocumentSnapshots: QuerySnapshot) {
//
//            var intro = 0
//            var onaDay = 0
//            var twoDay = 0
//            var twOneDay = 0
//            var approach = 0
//            var timeStr = 0
//            var lectOnStr = 0
//            var lectCentr = 0
//
//            if (!queryDocumentSnapshots.isEmpty) {
//                queryDocumentSnapshots.forEach { documentSnapshot ->
//
//                    val resultNote = documentSnapshot.toObject(City::class.java)
//
//                    if (!resultNote.intro.isNullOrEmpty()) {
//                        intro = (Integer.parseInt(resultNote.intro))
//                    } else {
//                        itemView.main_intro.text = "0"
//                    }
//                    if (!resultNote.onedayWS.isNullOrEmpty()) {
//                        onaDay = Integer.parseInt(resultNote.onedayWS)
//
//                    } else {
//                        itemView.main_one_day.text = "0"
//                    }
//                    if (!resultNote.twoDayWS.isNullOrEmpty()) {
//                        twoDay = Integer.parseInt(resultNote.twoDayWS)
//                    }
//
//                    if (!resultNote.twOneDay.isNullOrEmpty()) {
//                        twOneDay = Integer.parseInt(resultNote.twOneDay)
//
//                    }
//
//                    if (!resultNote.approach.isNullOrEmpty()) {
//                        approach = Integer.parseInt(resultNote.approach)
//                    } else {
//                        itemView.main_approach.text = "0"
//                    }
//                    if (!resultNote.timeStr.isNullOrEmpty()) {
//                        timeStr = Integer.parseInt(resultNote.timeStr)
//                    } else {
//                        itemView.main_time_str.text = "0"
//                    }
//                    if (!resultNote.lectOnStr.isNullOrEmpty()) {
//                        lectOnStr = Integer.parseInt(resultNote.lectOnStr)
//
//                    } else {
//                        itemView.main_street_lect.text = "0"
//                    }
//                    if (!resultNote.lectCentr.isNullOrEmpty()) {
//                        lectCentr = Integer.parseInt(resultNote.lectCentr)
//
//                    } else {
//                        itemView.main_lect_center.text = "0"
//                    }
//

//                }
//
//                itemView.individual_intro.text = intro.toString()
//                itemView.individual_one_day.text = onaDay.toString()
//                itemView.individual_two_day.text = twoDay.toString()
//                itemView.individual_21_day.text = twOneDay.toString()
//                itemView.individual_time_str.text = timeStr.toString()
//                itemView.individual_approach.text = approach.toString()
//                itemView.individual_street_lect.text = lectOnStr.toString()
//                itemView.individual_lect_center.text = lectCentr.toString()
//            } else {
////                itemView.result_city.text = model.centers.toString()
//                itemView.main_intro.text = "0"
//                itemView.main_one_day.text = "0"
//                itemView.main_two_day.text = "0"
//                itemView.main_21_day.text = "0"
//                itemView.main_time_str.text = "0"
//                itemView.main_approach.text = "0"
//                itemView.main_street_lect.text = "0"
//                itemView.main_lect_center.text = "0"
//            }
//        }
    }

    interface FragmentCommunication {
        fun respond(city: City)
    }
}
