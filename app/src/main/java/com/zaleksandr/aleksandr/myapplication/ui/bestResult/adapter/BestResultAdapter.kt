package com.zaleksandr.aleksandr.myapplication.ui.bestResult.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaleksandr.aleksandr.myapplication.model.City
import com.zaleksandr.aleksandr.myapplication.ui.eachCentersResult.adapter.EachCenterAdapter
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import com.zaleksandr.aleksandr.myapplication.util.clickByFilterBestResult
import kotlinx.android.synthetic.main.item_best_result_list.view.*


class BestResultAdapter(private val context: Context,
                        private var pairList: List<Pair<String?, Int>>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val noteRefCollection = firestoreInstance.collection("NewCity")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(com.zaleksandr.aleksandr.myapplication.R.layout.item_best_result_list,
                parent, false)
        return CentersHolder(v)
    }

    fun setList(pairList: List<Pair<String?, Int>>) {
        this.pairList = pairList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return pairList.size
    }

    enum class ClickByFilter {
        MONTH, WEEK, YEAR
    }

    var period = 0
    fun perioSelected(periodSelected: EachCenterAdapter.ClickByFilter) {

        when (periodSelected) {

            EachCenterAdapter.ClickByFilter.WEEK -> {
                period = 2; notifyDataSetChanged()

            }
            EachCenterAdapter.ClickByFilter.MONTH -> {
                period = 3; notifyDataSetChanged()

            }
            EachCenterAdapter.ClickByFilter.YEAR -> {
                period = 4; notifyDataSetChanged()

            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is CentersHolder) {
            holder.bind()
        }
    }

    inner class CentersHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            var sumIntro = 0
            var sumOneDay = 0
            var sumTwoDay = 0
            var sumTwoOneDay = 0
            var sumNWET = 0
            val item = pairList[adapterPosition]
            clickByFilterBestResult(noteRefCollection).addOnSuccessListener { queryDocumentSnapshots ->
                if (!queryDocumentSnapshots.isEmpty) {
                    queryDocumentSnapshots.forEach { documentSnapshot ->

                        val resultNote = documentSnapshot.toObject(City::class.java)

                        if (!resultNote.intro.isNullOrEmpty()) {
                            if (resultNote.name == item.first) {
                                val intro = (Integer.parseInt(resultNote.intro))
                                           sumIntro += intro
                            }
                        }

                        if (!resultNote.onedayWS.isNullOrEmpty()) {
                            if (resultNote.name == item.first) {
                                val oneDay = (Integer.parseInt(resultNote.onedayWS))
                                sumOneDay += oneDay
                            }
                        }

                        if (!resultNote.twoDayWS.isNullOrEmpty()) {
                            if (resultNote.name == item.first) {
                                val twoDay = (Integer.parseInt(resultNote.twoDayWS))
                                sumTwoDay += twoDay
                            }
                        }
                            if (!resultNote.twOneDay.isNullOrEmpty()) {
                                if (resultNote.twOneDay == item.first) {
                                    val twoOneDay = (Integer.parseInt(resultNote.twOneDay))
                                    sumTwoOneDay += twoOneDay
                                }
                            }

                            if (!resultNote.nwet.isNullOrEmpty()) {
                                if (resultNote.nwet == item.first) {
                                    val nwet = (Integer.parseInt(resultNote.nwet))
                                    sumNWET += nwet
                                }
                            }


                            itemView.best_result_name.text = item.first.toString()
                            itemView.best_result.text = item.second.toString()

                        }
                        itemView.best_res_intro.text = sumIntro.toString()
                        itemView.best_result_one_day.text = sumOneDay.toString()
                        itemView.best_res_two_day.text = sumTwoDay.toString()
                        itemView.best_res_twoOne_day.text = sumTwoOneDay.toString()
                        itemView.best_res_nwet.text = sumNWET.toString()
                    }
                }
            }
        }
    }