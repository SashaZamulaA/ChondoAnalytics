package com.example.aleksandr.myapplication.ui.main.adapter

import android.app.LauncherActivity.ListItem
import android.text.format.DateUtils
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.support.annotation.NonNull
import android.view.View
import java.util.Collections.emptyList
import android.widget.TextView
import com.example.aleksandr.myapplication.R
import java.util.*



//
//
//class EventsAdapter(items: List<ListItem>) : RecyclerView.Adapter5<RecyclerView.ViewHolder>() {
//
//    private val items = Collections.emptyList()
//
//    private class HeaderViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        internal var txt_header: TextView
//
//        init {
//            txt_header = itemView.findViewById(R.id.txt_header)
//        }
//
//    }
//
//    object DummyData {
//        val data: List<CityEvent>
//            get() {
//                val list = ArrayList<CityEvent>()
//                list.add(CityEvent("London", null, CityEvent.CITY_TYPE))
//                list.add(CityEvent("Droidcon", "Droidcon in London", CityEvent.EVENT_TYPE))
//                list.add(CityEvent("Some event", "Some event in London", CityEvent.EVENT_TYPE))
//                list.add(CityEvent("Amsterdam", null, CityEvent.CITY_TYPE))
//                list.add(CityEvent("Droidcon", "Droidcon in Amsterdam", CityEvent.EVENT_TYPE))
//                list.add(CityEvent("Berlin", null, CityEvent.CITY_TYPE))
//                list.add(CityEvent("Droidcon", "Droidcon in Berlin", CityEvent.EVENT_TYPE))
//                return list
//            }
//    }
//
//    private class EventViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        internal var txt_title: TextView
//
//        init {
//            txt_title = itemView.findViewById(R.id.txt_title)
//        }
//
//    }
//
//    init {
//        this.items = items
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        when (viewType) {
//            ListItem.TYPE_HEADER -> {
//                val itemView = inflater.inflate(R.layout.view_list_item_header, parent, false)
//                return HeaderViewHolder(itemView)
//            }
//            ListItem.TYPE_EVENT -> {
//                val itemView = inflater.inflate(R.layout.view_list_item_event, parent, false)
//                return EventViewHolder(itemView)
//            }
//            else -> throw IllegalStateException("unsupported item type")
//        }
//    }
//
//    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
//        val viewType = getItemViewType(position)
//        when (viewType) {
//            ListItem.TYPE_HEADER -> {
//                val header = items.get(position) as HeaderItem
//                val holder = viewHolder as HeaderViewHolder
//                // your logic here
//                holder.txt_header.setText(DateUtils.formatDate(header.getDate()))
//            }
//            ListItem.TYPE_EVENT -> {
//                val event = items.get(position) as EventItem
//                val holder = viewHolder as EventViewHolder
//                // your logic here
//                holder.txt_title.setText(event.getEvent().getTitle())
//            }
//            else -> throw IllegalStateException("unsupported item type")
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return items.size
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return items.get(position).getType()
//    }
//
//}