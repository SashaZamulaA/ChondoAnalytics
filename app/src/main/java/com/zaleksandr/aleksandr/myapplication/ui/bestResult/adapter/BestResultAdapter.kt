package com.zaleksandr.aleksandr.myapplication.ui.bestResult.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaleksandr.aleksandr.myapplication.model.City
import com.zaleksandr.aleksandr.myapplication.ui.eachCentersResult.adapter.EachCenterAdapter
import kotlinx.android.synthetic.main.item_best_result_list.view.*
import java.util.*


class BestResultAdapter(private val context: Context,
                        private var pairList: List<Pair<String?, Int>>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


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

            val item = pairList[adapterPosition]

            itemView.best_result_name.text = item.second.toString()

        }
    }
}
