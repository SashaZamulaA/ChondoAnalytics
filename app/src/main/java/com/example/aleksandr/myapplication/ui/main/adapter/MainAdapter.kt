package com.example.aleksandr.myapplication.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.model.City
import com.example.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import com.example.aleksandr.myapplication.util.endOfDay
import com.example.aleksandr.myapplication.util.startOfDay
import kotlinx.android.synthetic.main.item_main_list.view.*
import java.util.*


class MainAdapter(options: FirestoreRecyclerOptions<City>) : FirestoreRecyclerAdapter<City, CityHolder>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_main_list,
                parent, false)
        return CityHolder(v)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int, model: City) {
        holder.bind(model)
//         model.centers?.forEach { _ -> holder.bind(City()) }

    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    inner class CityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(note: City) {
//            noteRefCollection
//                    .whereEqualTo("centers", "Kyiv")
//                    .whereGreaterThanOrEqualTo("time", startOfDay(Date()))
//                    .whereLessThanOrEqualTo("time", endOfDay(Date()))
//                    .get()
//                    .addOnSuccessListener { queryDocumentSnapshots ->
//                        if (!queryDocumentSnapshots.isEmpty) {
//                            queryDocumentSnapshots.forEach { documentSnapshot ->
//
//                                val resultNote = documentSnapshot.toObject(City::class.java)
//
//                                if (!resultNote.intro.isNullOrEmpty()) {
//                                    val intro = (Integer.parseInt(resultNote.intro))
//                                    sumIntroKiev += intro
//                                }
                                itemView.result_city.text = note.centers
//                            }
//                        }
//                    }
        }
        fun bind2(note: City) {

            itemView.apply {
                name.text = note.centers

            }
        }
    }
}