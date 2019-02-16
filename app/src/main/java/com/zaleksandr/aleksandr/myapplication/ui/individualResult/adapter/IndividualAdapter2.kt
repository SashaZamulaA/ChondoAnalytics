package com.zaleksandr.aleksandr.myapplication.ui.commonResult.adapter

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.QuerySnapshot
import com.zaleksandr.aleksandr.myapplication.model.City
import com.zaleksandr.aleksandr.myapplication.ui.individualGoal.model.IndividualMonthGoalModel
import com.zaleksandr.aleksandr.myapplication.ui.individualGoal.model.IndividualWeekGoalModel
import com.zaleksandr.aleksandr.myapplication.ui.individualGoal.model.IndividualYearGoalModel
import com.zaleksandr.aleksandr.myapplication.ui.individualResult.IndividualResultFragment
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import com.zaleksandr.aleksandr.myapplication.util.clickByFilterIndividualGoal
import kotlinx.android.synthetic.main.item_individual_goal_and_result_for_person.view.*
import kotlinx.android.synthetic.main.item_individual_result_list.view.*
import java.util.*


class IndividualAdapter2(context: Context,
                         private var list: ArrayList<City>,
                         private var fragmentCommunication: IndividualResultFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    var city: City? = null
    private var mSelectedNoteIndex: Int = 0

    override fun getItemCount(): Int {
        return list.size + 1
    }

    private fun getItem(position: Int): City {
        return list[position - 1]
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_ITEM
    }

    private val noteRefCollection = firestoreInstance.collection("NewCity")
    private val refCollectionYearGoal = firestoreInstance.collection("EachMemberYearGoal")
    private val refCollectionMonthGoal = firestoreInstance.collection("EachMemberMonthGoal")
    private val refCollectionWeekGoal = firestoreInstance.collection("EachMemberWeekGoal")
    private val refCollectionAdditionalGoal = firestoreInstance.collection("EachMemberAdditionalGoal")

    enum class ClickByFilter {
        MONTH, WEEK, YEAR
    }

    var period = 1
    fun perioSelected(periodSelected: IndividualAdapter2.ClickByFilter) {

        when (periodSelected) {

            IndividualAdapter2.ClickByFilter.WEEK -> {
                period = 1; notifyDataSetChanged()
            }
            IndividualAdapter2.ClickByFilter.MONTH -> {
                period = 2; notifyDataSetChanged()
            }
            IndividualAdapter2.ClickByFilter.YEAR -> {
                period = 3; notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_ITEM) {
            val v = LayoutInflater.from(parent.context).inflate(com.zaleksandr.aleksandr.myapplication.R.layout.item_individual_result_list,
                    parent, false)
            return IndividualHolder(v)
        } else if (viewType == TYPE_HEADER) {
            val v = LayoutInflater.from(parent.context).inflate(com.zaleksandr.aleksandr.myapplication.R.layout.item_individual_goal_and_result_for_person,
                    parent, false)
            return IndividualGoalHolder(v)
        }
        throw RuntimeException("there is no type that matches the type $viewType + make sure your using types correctly")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is IndividualHolder) {
            holder.bind()
        } else if (holder is IndividualGoalHolder) {

            holder.bind2()
        }
    }

    inner class IndividualHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            val item = list[position - 1]
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
            if (!item.dp.isNullOrEmpty()) itemView.ind_result_read_dp_num.text = item.lectCentr else itemView.ind_result_read_dp_num.text = "0"
        }
    }

    inner class IndividualGoalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind2() {

            if (period == 1) {
                clickByFilterIndividualGoal(refCollectionWeekGoal).addOnSuccessListener { queryDocumentSnapshots ->
                    weekIndividualGoal(queryDocumentSnapshots)
                }
            } else if (period == 2) {
                clickByFilterIndividualGoal(refCollectionMonthGoal).addOnSuccessListener { queryDocumentSnapshots ->
                    monthIndividualGoal(queryDocumentSnapshots)
                }
            } else if (period == 3) {

                clickByFilterIndividualGoal(refCollectionYearGoal).addOnSuccessListener { querySnapshot ->
                    yearIndividualGoal(querySnapshot)
                }
            }

        }

        private fun weekIndividualGoal(queryDocumentSnapshots: QuerySnapshot) {
            itemView.goal_21_day_person_text.text = "Mmbk"
            itemView.goal_intr_person_text.text = "Cont."
            itemView.nwet_individual.visibility = View.GONE
            itemView.goal_year_nwet_person.visibility = View.GONE
            itemView.nwet_result_person.visibility = View.GONE

            if (!queryDocumentSnapshots.isEmpty) {
                queryDocumentSnapshots.forEach { documentSnapshot ->
                    val individualGoalNote = documentSnapshot.toObject(IndividualWeekGoalModel::class.java)
//week

                    if (!individualGoalNote.weekContacts.isNullOrEmpty()) {
                        itemView.goal_intro_person.text = individualGoalNote.weekContacts
                    }
                    if (!individualGoalNote.weekIntro.isNullOrEmpty()) {
                        itemView.year_goal_one_day_sem_person.text = individualGoalNote.weekIntro
                    }
                    if (!individualGoalNote.weekOneDay.isNullOrEmpty()) {
                        itemView.goal_two_day_sem_person.text = individualGoalNote.weekOneDay
                    }
                    if (!individualGoalNote.weekTwoDay.isNullOrEmpty()) {
                        itemView.individ_goal_action_center.text = individualGoalNote.weekTwoDay
                    }
                    if (!individualGoalNote.weekMMBK.isNullOrEmpty()) {
                        itemView.goal_21_day_person.text = individualGoalNote.weekMMBK
                    }
                }
            }
        }

        private fun monthIndividualGoal(queryDocumentSnapshots: QuerySnapshot) {
            itemView.goal_intr_person_text.text = "Int."
            itemView.nwet_individual.text = "NWET"
            itemView.goal_21_day_person_text.text = "21-d"
            itemView.nwet_individual.visibility = View.VISIBLE
            itemView.goal_year_nwet_person.visibility = View.VISIBLE
            itemView.nwet_result_person.visibility = View.VISIBLE
            if (!queryDocumentSnapshots.isEmpty) {
                queryDocumentSnapshots.forEach { documentSnapshot ->
                    val individualGoalNote = documentSnapshot.toObject(IndividualMonthGoalModel::class.java)
//month


                    if (!individualGoalNote.monthIntro.isNullOrEmpty()) {
                        itemView.goal_intro_person.text = individualGoalNote.monthIntro
                    }
                    if (!individualGoalNote.monthOneDay.isNullOrEmpty()) {
                        itemView.year_goal_one_day_sem_person.text = individualGoalNote.monthOneDay
                    }
                    if (!individualGoalNote.monthTwoDay.isNullOrEmpty()) {
                        itemView.goal_two_day_sem_person.text = individualGoalNote.monthTwoDay
                    }
                    if (!individualGoalNote.monthActioniser.isNullOrEmpty()) {
                        itemView.individ_goal_action_center.text = individualGoalNote.monthActioniser
                    }
                    if (!individualGoalNote.monthTWOne.isNullOrEmpty()) {
                        itemView.goal_21_day_person.text = individualGoalNote.monthTWOne
                    }
                    if (!individualGoalNote.monthNWET.isNullOrEmpty()) {
                        itemView.goal_year_nwet_person.text = individualGoalNote.monthNWET
                    }
                }
            }
        }

        private fun yearIndividualGoal(queryDocumentSnapshots: QuerySnapshot) {
            itemView.goal_intr_person_text.text = "Int."
            itemView.nwet_individual.text = "NWET"
            itemView.goal_21_day_person_text.text = "21-d"
            itemView.nwet_individual.visibility = View.VISIBLE
            itemView.goal_year_nwet_person.visibility = View.VISIBLE
            itemView.nwet_result_person.visibility = View.VISIBLE

            if (!queryDocumentSnapshots.isEmpty) {
                queryDocumentSnapshots.forEach { documentSnapshot ->
                    val individualGoalNote = documentSnapshot.toObject(IndividualYearGoalModel::class.java)
//year

                    if (!individualGoalNote.yearIntro.isNullOrEmpty()) {
                        itemView.goal_intro_person.text = individualGoalNote.yearIntro
                    }
                    if (!individualGoalNote.yearOneDay.isNullOrEmpty()) {
                        itemView.year_goal_one_day_sem_person.text = individualGoalNote.yearOneDay
                    }
                    if (!individualGoalNote.yearTwoDay.isNullOrEmpty()) {
                        itemView.goal_two_day_sem_person.text = individualGoalNote.yearTwoDay
                    }
                    if (!individualGoalNote.yearActionaiser.isNullOrEmpty()) {
                        itemView.individ_goal_action_center.text = individualGoalNote.yearActionaiser
                    }
                    if (!individualGoalNote.yearTWOne.isNullOrEmpty()) {
                        itemView.goal_21_day_person.text = individualGoalNote.yearTWOne
                    }
                    if (!individualGoalNote.yearNWET.isNullOrEmpty()) {
                        itemView.goal_year_nwet_person.text = individualGoalNote.yearNWET
                    }
                }
            }
        }
    }
//    fun updateNote(city: City) {
//        list[mSelectedNoteIndex].intro
//        notifyDataSetChanged()
//    }


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
    }

    interface FragmentCommunication {
        fun respond(city: City)
    }
}
