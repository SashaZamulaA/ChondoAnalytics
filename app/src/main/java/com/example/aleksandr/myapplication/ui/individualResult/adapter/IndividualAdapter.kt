package com.example.aleksandr.myapplication.ui.commonResult.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.model.City
import com.example.aleksandr.myapplication.ui.individualResult.IndividualResultFragment
import com.example.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.item_common_result_list.view.*
import kotlinx.android.synthetic.main.item_individual_result_list.view.*
import kotlinx.android.synthetic.main.item_is_word_list.*
import java.util.*

class IndividualAdapter(private var list: ArrayList<City>, private var fragmentCommunication: IndividualResultFragment) : RecyclerView.Adapter<IndividualAdapter.CityHolder>() {


    private var mNotes = ArrayList<City>()
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
        holder.bind()
        val item = list[position]
        holder.itemView.individual_intro.text = item.intro
    }

    fun removeNote(city: City) {
    list.remove(city)
        notifyDataSetChanged()
    }

//    fun updateList(list: ArrayList<City>) {
//        this.list = list
//        notifyDataSetChanged()
//    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    inner class CityHolder(itemView: View, var fragmentCommunication: IndividualResultFragment) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            mSelectedNoteIndex = adapterPosition
            fragmentCommunication.respond(list[mSelectedNoteIndex])
        }

        fun bind() {

            noteRefCollection.whereEqualTo("id", if ("${FirebaseAuth.getInstance().uid}" == FirebaseAuth.getInstance().currentUser!!.uid) {
                FirebaseAuth.getInstance().uid
            } else null)
                    .orderBy("time", Query.Direction.DESCENDING).get().addOnSuccessListener { queryDocumentSnapshots ->
                        cityName(queryDocumentSnapshots)
                    }
        }

        private fun cityName(queryDocumentSnapshots: QuerySnapshot) {

            var intro = 0
            var onaDay = 0
            var twoDay = 0
            var sumTwent1 = 0
            var sumAppr = 0
            var sumTimeStr = 0
            var sumStrLect = 0
            var sumCenteLect = 0

            if (!queryDocumentSnapshots.isEmpty) {
                queryDocumentSnapshots.forEach { documentSnapshot ->

                    val resultNote = documentSnapshot.toObject(City::class.java)

                    if (!resultNote.intro.isNullOrEmpty()) {
                        intro = (Integer.parseInt(resultNote.intro))
                    } else {
                        itemView.main_intro.text = "0"
                    }
                    if (!resultNote.onedayWS.isNullOrEmpty()) {
                        onaDay = Integer.parseInt(resultNote.onedayWS)

                    } else {
                        itemView.main_one_day.text = "0"
                    }
                    if (!resultNote.twoDayWS.isNullOrEmpty()) {
                        twoDay = Integer.parseInt(resultNote.twoDayWS)
                    }

                    if (!resultNote.twOneDay.isNullOrEmpty()) {
                        val twOneDay = Integer.parseInt(resultNote.twOneDay)
                        sumTwent1 += twOneDay
                    }

                    if (!resultNote.approach.isNullOrEmpty()) {
                        val approach = Integer.parseInt(resultNote.approach)
                        sumAppr += approach
                    } else {
                        itemView.main_approach.text = "0"
                    }
                    if (!resultNote.timeStr.isNullOrEmpty()) {
                        val timeStr = Integer.parseInt(resultNote.timeStr)
                        sumTimeStr += timeStr
                    } else {
                        itemView.main_time_str.text = "0"
                    }
                    if (!resultNote.lectOnStr.isNullOrEmpty()) {
                        val lectOnStr = Integer.parseInt(resultNote.lectOnStr)
                        sumStrLect += lectOnStr
                    } else {
                        itemView.main_street_lect.text = "0"
                    }
                    if (!resultNote.lectCentr.isNullOrEmpty()) {
                        val lectCentr = Integer.parseInt(resultNote.lectCentr)
                        sumCenteLect += lectCentr
                    } else {
                        itemView.main_lect_center.text = "0"
                    }


                }
                itemView.main_intro.text = intro.toString()
                itemView.main_one_day.text = onaDay.toString()
                itemView.main_two_day.text = twoDay.toString()
//                itemView.main_21_day.text = sumTwent1.toString()
//                itemView.main_time_str.text = sumTimeStr.toString()
//                itemView.main_approach.text = sumAppr.toString()
//                itemView.main_street_lect.text = sumStrLect.toString()
//                itemView.main_lect_center.text = sumCenteLect.toString()

            } else {
//                itemView.result_city.text = model.centers.toString()
                itemView.main_intro.text = "0"
                itemView.main_one_day.text = "0"
                itemView.main_two_day.text = "0"
                itemView.main_21_day.text = "0"
                itemView.main_time_str.text = "0"
                itemView.main_approach.text = "0"
                itemView.main_street_lect.text = "0"
                itemView.main_lect_center.text = "0"
            }
        }
    }

    interface FragmentCommunication {
        fun respond(city: City)
    }
}
