package com.zaleksandr.aleksandr.myapplication.ui.myResult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.DocumentSnapshot
import com.zaleksandr.aleksandr.myapplication.BottomNavigationViewBehavior
import com.zaleksandr.aleksandr.myapplication.DialogCompositeDisposable
import com.zaleksandr.aleksandr.myapplication.model.City
import com.zaleksandr.aleksandr.myapplication.ui.individualGoal.model.IndividualMonthGoalModel
import com.zaleksandr.aleksandr.myapplication.ui.individualGoal.model.IndividualWeekGoalModel
import com.zaleksandr.aleksandr.myapplication.ui.individualGoal.model.IndividualYearGoalModel
import com.zaleksandr.aleksandr.myapplication.ui.updateResult.adapter.UpdateResultAdapter
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import com.zaleksandr.aleksandr.myapplication.util.clickByFilterIndividualGoal
import com.zaleksandr.aleksandr.myapplication.util.clickByFilterIndividualResult
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_my_result.*
import kotlinx.android.synthetic.main.fragment_my_result.view.*
import java.text.DecimalFormat
import kotlin.math.roundToInt


class MyResultFragment : Fragment(){

    var toolbar: Toolbar? = null
    var adapter: UpdateResultAdapter? = null
    var city: City? = null
    private val items: ArrayList<City> = ArrayList()
    private var mLastQueriedDocument: DocumentSnapshot? = null
    private val dialogDisposable = DialogCompositeDisposable()

    var percentDPGoal = 0
    var dpKoreanGoal = 0
    var dpMob = 0
    var dpHDH = 0
    var percentIntrioGoal = 0
    var percentOneDayGoal = 0
    var percentTwoDayGoal = 0
    var percentActGoal = 0
    var percentTwoOneDayGoal = 0
    var percentNWETGoal = 0
    var sumInDouble: Double = 1.0
    var df = DecimalFormat("#.##")
    var dpYear = ""
    private val refCollectionYearGoal = firestoreInstance.collection("EachMemberYearGoal")
    private val refCollectionMonthGoal = firestoreInstance.collection("EachMemberMonthGoal")
    private val refCollectionWeekGoal = firestoreInstance.collection("EachMemberWeekGoal")
    var period = 3
    enum class ClickByFilter {
        MONTH, WEEK, YEAR
    }


    fun perioSelected(periodSelected: ClickByFilter) {

        when (periodSelected) {

            ClickByFilter.WEEK -> {
                period = 1
                setUpRecyclerView()
                res()
            }
            ClickByFilter.MONTH -> {
                period = 2
                setUpRecyclerView()
                res()
            }
            ClickByFilter.YEAR -> {
                period = 3
                setUpRecyclerView()
                res()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.zaleksandr.aleksandr.myapplication.R.layout.fragment_my_result, container, false)

        setUpRecyclerView()
        res()

        bottomMenuInit(rootView)

        return rootView
    }


    override fun onResume() {
        super.onResume()
        (this.activity!!.toolbar as Toolbar).visibility = View.VISIBLE
        (this.activity!!.toolbar as Toolbar).title = "Individual result"
    }

    private fun setUpRecyclerView() {

        if (period == 1) {
            clickByFilterIndividualGoal(refCollectionWeekGoal).addOnSuccessListener { queryDocumentSnapshots ->
                if (!queryDocumentSnapshots.isEmpty) {
                    queryDocumentSnapshots.forEach { documentSnapshot ->
                        val individualGoalNote = documentSnapshot.toObject(IndividualWeekGoalModel::class.java)
//week

                        percentDPGoal = 0

                        if (!individualGoalNote.weekMMBK.isNullOrEmpty()) {
                            my_goal_mmbk.text = individualGoalNote.weekMMBK
                        } else my_goal_mmbk.text = "0"

                        if (!individualGoalNote.weekContacts.isNullOrEmpty()) {
                            my_goal_cont.text = individualGoalNote.weekContacts
                        } else my_goal_cont.text = "0"

                        if (!individualGoalNote.weekIntro.isNullOrEmpty()) {
                            my_goal_intro.text = individualGoalNote.weekIntro
                            percentIntrioGoal = (Integer.parseInt(individualGoalNote.weekIntro))


                        } else {
                            my_goal_intro.text = "0"
                            percentIntrioGoal = 0
                        }
                        if (!individualGoalNote.weekOneDay.isNullOrEmpty()) {
                            my_goal_one_d.text = individualGoalNote.weekOneDay
                            percentOneDayGoal = (Integer.parseInt(individualGoalNote.weekOneDay))
                        } else {
                            percentOneDayGoal = 0
                            my_goal_one_d.text = "0"
                        }
                        if (!individualGoalNote.weekTwoDay.isNullOrEmpty()) {
                            my_goal_two_d.text = individualGoalNote.weekTwoDay
                            percentTwoDayGoal = (Integer.parseInt(individualGoalNote.weekTwoDay))
                        } else {
                            my_goal_two_d.text = "0"
                            percentTwoDayGoal = 0
                        }
                        if (individualGoalNote.weekAct.isNotEmpty()) {
                            my_goal_act.text = individualGoalNote.weekAct
                            percentActGoal = (Integer.parseInt(individualGoalNote.weekAct))
                        } else {
                            my_goal_two_d.text = "0"
                            percentActGoal = 0
                        }
                    }
                }
            }
        } else if (period == 2) {
            clickByFilterIndividualGoal(refCollectionMonthGoal).addOnSuccessListener { queryDocumentSnapshots ->
                if (!queryDocumentSnapshots.isEmpty) {
                    queryDocumentSnapshots.forEach { documentSnapshot ->
                        val individualGoalNote = documentSnapshot.toObject(IndividualMonthGoalModel::class.java)
//month
                        percentDPGoal = 0

                        if (!individualGoalNote.monthIntro.isNullOrEmpty()) {
                            my_goal_intro.text = individualGoalNote.monthIntro
                            percentIntrioGoal = (Integer.parseInt(individualGoalNote.monthIntro))
                        } else {
                            my_goal_intro.text = "0"
                            percentIntrioGoal = 0
                        }
                        if (!individualGoalNote.monthOneDay.isNullOrEmpty()) {
                            my_goal_one_d.text = individualGoalNote.monthOneDay
                            percentOneDayGoal = (Integer.parseInt(individualGoalNote.monthOneDay))
                        } else {
                            percentOneDayGoal = 0
                            my_goal_one_d.text = "0"
                        }
                        if (!individualGoalNote.monthTwoDay.isNullOrEmpty()) {
                            my_goal_two_d.text = individualGoalNote.monthTwoDay
                            percentTwoDayGoal = (Integer.parseInt(individualGoalNote.monthTwoDay))
                        } else {
                            my_goal_two_d.text = "0"
                            percentTwoDayGoal = 0
                        }
                        if (!individualGoalNote.monthActioniser.isNullOrEmpty()) {
                            my_goal_act.text = individualGoalNote.monthActioniser
                            percentTwoDayGoal = (Integer.parseInt(individualGoalNote.monthActioniser))
                        } else {
                            my_goal_act.text = "0"
                            percentTwoDayGoal = 0
                        }
                        if (!individualGoalNote.monthTWOne.isNullOrEmpty()) {
                        my_goal_21.text = individualGoalNote.monthTWOne
                        percentTwoOneDayGoal = (Integer.parseInt(individualGoalNote.monthTWOne))
                    } else {
                        my_goal_21.text = "0"
                        percentTwoOneDayGoal = 0
                    }
                        if (!individualGoalNote.monthNWET.isNullOrEmpty()) {
                            my_goal_nwet.text = individualGoalNote.monthNWET
                            percentNWETGoal = (Integer.parseInt(individualGoalNote.monthNWET))
                        } else {
                            my_goal_nwet.text = "0"
                            percentNWETGoal = 0
                        }
                    }
                }
            }
        } else if (period == 3) {

            clickByFilterIndividualGoal(refCollectionYearGoal).addOnSuccessListener { queryDocumentSnapshots ->
                if (!queryDocumentSnapshots.isEmpty) {
                    queryDocumentSnapshots.forEach { documentSnapshot ->
                        val individualGoalNote = documentSnapshot.toObject(IndividualYearGoalModel::class.java)
//year

                        if (!individualGoalNote.yearDPUkr.isNullOrEmpty()) {
                            my_goal_dp_ukr.text = individualGoalNote.yearDPUkr
                            percentDPGoal = (Integer.parseInt(individualGoalNote.yearDPUkr))
                        } else {
                            my_goal_dp_ukr.text = "0"
                            percentDPGoal = 0
                        }

                        if (!individualGoalNote.yearDPKor.isNullOrEmpty()) {
                            my_goal_dp_kor.text = individualGoalNote.yearDPKor
                            dpKoreanGoal = (Integer.parseInt(individualGoalNote.yearDPKor))
                        } else {
                            my_goal_dp_kor.text = "0"
                            dpKoreanGoal = 0
                        }

                        if (!individualGoalNote.yearMobilisation.isNullOrEmpty()) {
                            goal_mob.text = individualGoalNote.yearMobilisation
                            dpMob = (Integer.parseInt(individualGoalNote.yearMobilisation))
                        } else {
                            goal_mob.text = "0"
                            dpMob = 0
                        }

                        if (!individualGoalNote.yearHDH.isNullOrEmpty()) {
                            goal_hdh.text = individualGoalNote.yearHDH
                            dpHDH = (Integer.parseInt(individualGoalNote.yearHDH))
                        } else {
                            goal_hdh.text = "0"
                            dpHDH = 0
                        }

                        if (!individualGoalNote.yearIntro.isNullOrEmpty()) {
                            my_goal_intro.text = individualGoalNote.yearIntro
                            percentIntrioGoal = (Integer.parseInt(individualGoalNote.yearIntro))
                        } else {
                            my_goal_intro.text = "0"
                            percentIntrioGoal = 0
                        }
                        if (!individualGoalNote.yearOneDay.isNullOrEmpty()) {
                            my_goal_one_d.text = individualGoalNote.yearOneDay
                            percentOneDayGoal = (Integer.parseInt(individualGoalNote.yearOneDay))
                        } else {
                            my_goal_one_d.text = "0"
                            percentOneDayGoal = 0
                        }
                        if (!individualGoalNote.yearTwoDay.isNullOrEmpty()) {
                            my_goal_two_d.text = individualGoalNote.yearTwoDay
                            percentTwoDayGoal = (Integer.parseInt(individualGoalNote.yearTwoDay))
                        } else {
                            my_goal_two_d.text = "0"
                            percentTwoDayGoal = 0
                        }
                        if (!individualGoalNote.yearActionaiser.isNullOrEmpty()) {
                            my_goal_act.text = individualGoalNote.yearActionaiser
                            percentActGoal = (Integer.parseInt(individualGoalNote.yearActionaiser))
                        } else {
                            my_goal_act.text = "0"
                            percentActGoal = 0
                        }
                        if (!individualGoalNote.yearTWOne.isNullOrEmpty()) {
                            my_goal_21.text = individualGoalNote.yearTWOne
                            percentTwoOneDayGoal = (Integer.parseInt(individualGoalNote.yearTWOne))
                        } else {
                            my_goal_21.text = "0"
                            percentTwoOneDayGoal = 0
                        }
                        if (!individualGoalNote.yearNWET.isNullOrEmpty()) {
                            my_goal_nwet.text = individualGoalNote.yearNWET
                            percentNWETGoal = (Integer.parseInt(individualGoalNote.yearNWET))
                        } else {
                            my_goal_nwet.text = "0"
                            percentNWETGoal = 0
                        }
                    }
                }
            }
        }

        }

    fun res(){
        var sumMmbk = 0
        var sumAppr = 0
        var sumCont = 0
        var sumStr = 0
        var sumIntro = 0
        var sumOneD1 = 0
        var sumTwoD1 = 0
        var sumAct = 0
        var sumTwent1 = 0
        var sumNwet = 0
        var sumCenterCh = 0
        var sumLectInCenter = 0
        var supLectOnStr = 0
        var sumEduMat = 0
        var sumDpKor = 0.0
        var sumDpUkr = 0.0
        var sumMob = 0
        var sumHDH = 0

        val notesCollectionRef = firestoreInstance.collection("NewCity")
        clickByFilterIndividualResult(notesCollectionRef, period).addOnSuccessListener { queryDocumentSnapshots ->


            if (!queryDocumentSnapshots.isEmpty) {

                queryDocumentSnapshots.forEach { documentSnapshot ->

                    val resultNote = documentSnapshot.toObject(City::class.java)

                    if (!resultNote.dp.isNullOrEmpty()) {
                        val dp = Integer.parseInt(resultNote.dp)
                        sumDpUkr += dp
                    }
                    if (!resultNote.dpKor.isNullOrEmpty()) {
                        val dpKor = Integer.parseInt(resultNote.dpKor)
                        sumDpKor += dpKor
                    }


                    if (!resultNote.gradeIntRes.isNullOrEmpty()) {
                        my_intern_res.text = resultNote.gradeIntRes
                    }
                    if (!resultNote.descriptionGoal.isNullOrEmpty()) {
                        goal_description.setText(resultNote.descriptionGoal)
                    }

                    if (!resultNote.mobilis.isNullOrEmpty()) {
                        val mob = (Integer.parseInt(resultNote.mobilis))
                        sumMob += mob
                    }

                    if (!resultNote.hdh.isNullOrEmpty()) {
                        val hdh = (Integer.parseInt(resultNote.hdh))
                        sumHDH += hdh
                    }
                    if (!resultNote.mmbk.isNullOrEmpty()) {
                        val mmbk = (Integer.parseInt(resultNote.mmbk))
                        sumMmbk += mmbk
                    }

                    if (!resultNote.timeStr.isNullOrEmpty()) {
                        val timestr = (Integer.parseInt(resultNote.timeStr))
                        sumStr += timestr
                    }

                    if (!resultNote.approach.isNullOrEmpty()) {
                        val approachTime = (Integer.parseInt(resultNote.approach))
                        sumAppr += approachTime
                    }
                    if (!resultNote.contact.isNullOrEmpty()) {
                        val contact = (Integer.parseInt(resultNote.contact))
                        sumCont += contact
                    }
                    if (!resultNote.intro.isNullOrEmpty()) {
                        val intro = (Integer.parseInt(resultNote.intro))
                        sumIntro += intro
                    }
                    if (!resultNote.onedayWS.isNullOrEmpty()) {
                        val onaDay = Integer.parseInt(resultNote.onedayWS)
                        sumOneD1 += onaDay
                    }
                    if (!resultNote.twoDayWS.isNullOrEmpty()) {
                        val twoDay = Integer.parseInt(resultNote.twoDayWS)
                        sumTwoD1 += twoDay
                    }
                    if (!resultNote.actionaiser.isNullOrEmpty()) {
                        val act = Integer.parseInt(resultNote.actionaiser)
                        sumAct += act
                    }
                    if (!resultNote.twOneDay.isNullOrEmpty()) {
                        val twOneDay = Integer.parseInt(resultNote.twOneDay)
                        sumTwent1 += twOneDay
                    }
                    if (!resultNote.nwet.isNullOrEmpty()) {
                        val nwet = Integer.parseInt(resultNote.nwet)
                        sumNwet += nwet
                    }
                    if (!resultNote.timeCenter.isNullOrEmpty()) {
                        val timeCen = Integer.parseInt(resultNote.timeCenter)
                        sumCenterCh += timeCen
                    }
                    if (!resultNote.lectCentr.isNullOrEmpty()) {
                        val lectC = Integer.parseInt(resultNote.lectCentr)
                        sumLectInCenter += lectC
                    }
                    if (!resultNote.lectOnStr.isNullOrEmpty()) {
                        val lectStr = Integer.parseInt(resultNote.lectOnStr)
                        supLectOnStr += lectStr
                    }
                    if (!resultNote.eduMat.isNullOrEmpty()) {
                        val edu = Integer.parseInt(resultNote.eduMat)
                        sumEduMat += edu
                    }
                    if (!resultNote.dpKor.isNullOrEmpty()) {
                        val dpK = Integer.parseInt(resultNote.dpKor)
                        sumDpKor += dpK
                    }

                    if (!resultNote.mobilis.isNullOrEmpty()) {
                        val mob = Integer.parseInt(resultNote.mobilis)
                        sumMob += mob
                    }

                    if (!resultNote.hdh.isNullOrEmpty()) {
                        val mob = Integer.parseInt(resultNote.hdh)
                        sumHDH += mob
                    }
                }
            }


            if (percentIntrioGoal != 0) {
                my_result_intr.text = ((sumIntro.toFloat() / percentIntrioGoal.toFloat() * 100).roundToInt()).toString()

            } else {
                my_result_intr.text = "0"
            }
            if (percentOneDayGoal != 0) {
                my_res_one_day_perc.text = ((sumOneD1.toFloat() / percentOneDayGoal.toFloat() * 100).roundToInt()).toString()
            } else {
                my_res_one_day_perc.text = "0"
            }

            if (percentTwoDayGoal != 0) {
                my_res_teo_day_perc.text = ((sumTwoD1.toFloat() / percentTwoDayGoal.toFloat() * 100).roundToInt()).toString()
            } else {
                my_res_teo_day_perc.text = "0"
            }

            if (percentActGoal != 0) {
                my_res_act_perc.text = ((sumAct.toFloat() / percentActGoal.toFloat() * 100).roundToInt()).toString()
            } else {
                my_res_act_perc.text = "0"
            }

            if (percentTwoOneDayGoal != 0) {
                my_res_21_perc.text = ((sumTwent1.toFloat() / percentTwoOneDayGoal.toFloat() * 100).roundToInt()).toString()
            } else {
                my_res_21_perc.text = "0"
            }

            if (percentNWETGoal != 0) {
                my_res_nwet_perc.text = ((sumNwet.toFloat() / percentNWETGoal.toFloat() * 100).roundToInt()).toString()
            } else {
                my_res_nwet_perc.text = "0"
            }

            if (percentDPGoal != 0) {
                sumInDouble = ((sumDpUkr / 455) / percentDPGoal.toFloat()) * 100
                df = DecimalFormat("#.##")
                dpYear = df.format(sumInDouble)
                my_res_dp_ukr_perc.text = dpYear
            } else my_res_dp_ukr_perc.text = "0"

            if (dpKoreanGoal != 0) {
                sumInDouble = ((sumDpKor / 455) / dpKoreanGoal.toFloat()) * 100
                df = DecimalFormat("#.##")
                dpYear = df.format(sumInDouble)
                perc_dp_cor.text = dpYear
            } else perc_dp_cor.text = "0"

            my_res_mmbk.text = sumMmbk.toString()
            my_res_time_str.text = sumStr.toString()
            my_res_appr.text = sumAppr.toString()
            my_res_cont.text = sumCont.toString()
            my_res_intr.text = sumIntro.toString()
            my_res_1_day.text = sumOneD1.toString()
            my_res_2_day.text = sumTwoD1.toString()
            my_res_act.text = sumAct.toString()
            my_res_21_day.text = sumTwent1.toString()
            my_res_nwet.text = sumNwet.toString()
            my_res_center_ch.text = sumCenterCh.toString()
            my_res_center_lect.text = sumLectInCenter.toString()
            my_res_str_lect.text = supLectOnStr.toString()
            my_res_edu_mat.text = sumEduMat.toString()
            my_res_dp_kor.text = sumDpKor.toString()
            my_res_dp_ukr.text = sumDpUkr.toString()
            my_res_mob.text = sumMob.toString()
            my_res_hdh.text = sumHDH.toString()
        }

    }

        private fun bottomMenuInit(rootView: View) {
            val layoutParams = rootView.bottom_navigation_person.layoutParams as CoordinatorLayout.LayoutParams
            layoutParams.behavior = BottomNavigationViewBehavior()
            rootView.bottom_navigation_person.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    com.zaleksandr.aleksandr.myapplication.R.id.menu_year -> {
                        perioSelected(ClickByFilter.YEAR)
                    }
                    com.zaleksandr.aleksandr.myapplication.R.id.menu_month -> {
                        perioSelected(ClickByFilter.MONTH)
                    }
                    com.zaleksandr.aleksandr.myapplication.R.id.menu_week -> {
                        perioSelected(ClickByFilter.WEEK)
                    }

                    else -> {
                    }
                }
                true
            }
        }

    }