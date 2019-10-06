package com.zaleksandr.aleksandr.myapplication.ui.bestResultHdh.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zaleksandr.aleksandr.myapplication.R
import com.zaleksandr.aleksandr.myapplication.model.CityHDH
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import com.zaleksandr.aleksandr.myapplication.util.clickByFilterBestResult
import kotlinx.android.synthetic.main.item_best_hdh_result_list.view.*

class BestHDHResultAdapter(private val context: Context,
                           private var pairList: List<Pair<String?, Float>>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val noteRefHDHCollection = firestoreInstance.collection("ResultHDH")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_best_hdh_result_list,
                parent, false)
        return CentersHolder(v)
    }

    fun <I> RecyclerView.Adapter<*>.updateListWithDiffs(oldList: List<I>, newList: List<I>, listSetter: (List<I>) -> Unit, itemsTheSame: (I, I) -> Boolean = { o, n -> o == n }, contentsTheSame: (I, I) -> Boolean = { o, n -> o == n }, detectMoves: Boolean = false) {
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = oldList.size
            override fun getNewListSize(): Int = newList.size

            override fun areItemsTheSame(oi: Int, ni: Int): Boolean = itemsTheSame(oldList[oi], newList[ni])
            override fun areContentsTheSame(oi: Int, ni: Int): Boolean = contentsTheSame(oldList[oi], newList[ni])
            override fun getChangePayload(oi: Int, ni: Int): Any? = Unit
        }, detectMoves).also {
            listSetter(newList)
            it.dispatchUpdatesTo(this)
        }
    }

    fun setList(pairList: List<Pair<String?, Float>>) = updateListWithDiffs(
            this.pairList, pairList,
            { this.pairList = pairList },
            { o, n -> o.first == n.first },
            detectMoves = true)


    override fun getItemCount(): Int {
        return pairList.size
    }

    enum class ClickByFilter {
        MONTH, WEEK, YEAR
    }

    var period = 3
    fun perioSelected(periodSelected: BestHDHResultAdapter.ClickByFilter) {

        when (periodSelected) {

            BestHDHResultAdapter.ClickByFilter.WEEK -> {
                period = 1; notifyDataSetChanged()

            }
            BestHDHResultAdapter.ClickByFilter.MONTH -> {
                period = 2; notifyDataSetChanged()

            }
            BestHDHResultAdapter.ClickByFilter.YEAR -> {
                period = 3; notifyDataSetChanged()

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
            var sumhdh = 0
            var sumMob = 0
            var sumSS = 0
            var sumFarm = 0
            var sumNight = 0
            var numberHDH = 0
            var name = ""
            val item = pairList[adapterPosition]

                        clickByFilterBestResult(noteRefHDHCollection, period).addOnSuccessListener { queryDocumentSnapshots ->
                if (!queryDocumentSnapshots.isEmpty) {
                    queryDocumentSnapshots.forEach { documentSnapshot ->

                        val resultNoteHDH = documentSnapshot.toObject(CityHDH::class.java)

                        if (resultNoteHDH.hdhZero)
                            numberHDH = 0
                        if (resultNoteHDH.hdhOne)
                            numberHDH = 1
                        if (resultNoteHDH.hdhTwo)
                            numberHDH = 2
                        if (resultNoteHDH.hdhThree)
                            numberHDH = 3
                        if (resultNoteHDH.hdhFour)
                            numberHDH = 4
                        if (resultNoteHDH.hdhFive)
                            numberHDH = 5
                        if (resultNoteHDH.hdhSix)
                            numberHDH = 6
                        if (resultNoteHDH.hdhSeven)
                            numberHDH = 7

                        if (resultNoteHDH.id == item.first) {
                            name = resultNoteHDH.name
                        }
                        if (resultNoteHDH.id == item.first) {
                            sumhdh += numberHDH
                        }

//                        if (layoutPosition == 0 ||layoutPosition == 1 ||layoutPosition == 2 ){
//                            itemView.best_cardView.setBackgroundResource(R.color.best3_color)
//                        }
//                        if (layoutPosition == 3 ||layoutPosition == 4 ||layoutPosition == 5||
//                                layoutPosition == 6 ||layoutPosition == 7 ||layoutPosition == 8
//                                ||layoutPosition == 9){
//                            itemView.best_cardView.setBackgroundResource(R.color.best3_color)
//                        }

                        if (resultNoteHDH.id == item.first) {
                            itemView.best_number_position_hdh.text = (layoutPosition + 1).toString()
                        }

//                        itemView.best_result_name.text = item.first.toString()
//                        itemView.best_result.text = item.second.toString()

                    }

                    itemView.best_result_name_hdh.text = name
                    itemView.best_res_hdh.text = item.second.toString()

                }
            }
        }
    }
}