package com.zaleksandr.aleksandr.myapplication.ui.chart

import android.content.ContentValues
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.zaleksandr.aleksandr.myapplication.BottomNavigationViewBehavior
import com.zaleksandr.aleksandr.myapplication.model.City
import com.zaleksandr.aleksandr.myapplication.util.*
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_chart.*
import kotlinx.android.synthetic.main.fragment_chart.view.*
import java.text.DecimalFormat


class ChartFragment : Fragment() {

    var toolbar: Toolbar? = null
    private val rain: ArrayList<Float> = ArrayList()
    private val monthNames: ArrayList<String> = ArrayList()
    private var pairList: List<Pair<String?, Int>>? = null
    private val noteRefCollection = firestoreInstance.collection("NewCity")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.zaleksandr.aleksandr.myapplication.R.layout.fragment_chart, container, false)


        rain.add(98.6F)
        rain.add(94.6F)
        rain.add(28.6F)
        rain.add(198.6F)
        rain.add(8.6F)
        rain.add(98.6F)
        rain.add(98.6F)

        monthNames.add("Kyiv")
        monthNames.add("Kharkiv")
        monthNames.add("Dnepr")
        monthNames.add("Zhytomyr")
        monthNames.add("Lviv")
        monthNames.add("Odessa")
        monthNames.add("Chernigov")

        bottomMenuInit(rootView)

        noteRefCollection.addSnapshotListener { queryDocumentSnapshots, _ ->

            cityResult(queryDocumentSnapshots!!, rootView)
        }

        setupPieChart(rootView)

        return rootView
    }

    private fun cityResult(queryDocumentSnapshots: QuerySnapshot, rootView: View) {

        var sumIntro = 0
        var sumOneD1 = 0
        var sumTwoD1 = 0
        var sumTwent1 = 0
        var sumNwet = 0

        if (!queryDocumentSnapshots.isEmpty) {
            queryDocumentSnapshots.forEach { documentSnapshot ->

                val resultNote = documentSnapshot.toObject(City::class.java)

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

                if (!resultNote.twOneDay.isNullOrEmpty()) {
                    val twOneDay = Integer.parseInt(resultNote.twOneDay)
                    sumTwent1 += twOneDay
                }

                if (!resultNote.nwet.isNullOrEmpty()) {
                    val nwet = Math.round(Integer.parseInt(resultNote.nwet).toFloat())
                    sumNwet += nwet
                }
            }
            val df = DecimalFormat("#.##")
            rootView.chart_common_result.text = (sumIntro + sumOneD1 * 3 + sumTwoD1 * 12 + sumTwent1 * 40 + sumNwet * 80).toString()

            rootView.result_percent.text = ((df.format((sumIntro + sumOneD1 * 3 + sumTwoD1 * 12 + sumTwent1 * 40 + sumNwet * 80F) * 100F / 12620F)).toString())

            for (change in queryDocumentSnapshots.documentChanges) {
                if (change.type == DocumentChange.Type.MODIFIED) {
                    Log.d(ContentValues.TAG, "data:" + change.document.data)
                }
                val source = if (queryDocumentSnapshots.metadata.isFromCache)
                    "local cache"
                else
                    "server"
                Log.d(ContentValues.TAG, "Data fetched from $source")
            }
        }
    }


    fun setupPieChart(rootView: View) {

        rootView.chart.visibility = View.VISIBLE
        rootView.common_goal_zero.visibility = View.VISIBLE
        rootView.chart_common_result_zero.visibility = View.VISIBLE
        rootView.common_score_percent_zero.visibility = View.VISIBLE

        noteRefCollection
                .orderBy("time", Query.Direction.DESCENDING)
                .get().addOnCompleteListener { querydocumentSnapshot ->
                    if (querydocumentSnapshot.isSuccessful) {

                        val items = querydocumentSnapshot.result!!
                                .map { it.toObject<City>(City::class.java) }
//                                .filterNot {
//                                    (it.name == "Kyiv Chondoso" || it.name == "Daniela Aldasoro ")
//
//                                }

                        val itemsCity = items.groupBy {
                            it.centers
                        }
                                .mapValues {
                                    it.value.sumBy {

                                        Integer.parseInt(if (it.intro.isNullOrEmpty() || it.intro.isNullOrBlank() || it.intro == "") {
                                            ("0").toString()
                                        } else it.intro.toString())
                                                .plus(Integer.parseInt(if (it.onedayWS.isNullOrEmpty() || it.onedayWS.isNullOrBlank() || it.onedayWS == "") {
                                                    ("0").toString()
                                                } else it.onedayWS) * 3)
                                                .plus(Integer.parseInt(if (it.twoDayWS.isNullOrEmpty() || it.twoDayWS.isNullOrBlank() || it.twoDayWS == "") {
                                                    ("0").toString()
                                                } else it.twoDayWS) * 12)
                                                .plus(Integer.parseInt(if (it.twOneDay.isNullOrEmpty() || it.twOneDay.isNullOrBlank() || it.twOneDay == "") {
                                                    ("0").toString()
                                                } else it.twOneDay) * 40)
                                                .plus(Integer.parseInt(if (it.nwet.isNullOrEmpty() || it.nwet.isNullOrBlank() || it.nwet == "") {
                                                    ("0").toString()
                                                } else it.nwet) * 80)
                                    }
                                }
                                .toList()
                                .filterNot { it.second == 0 }
                                .sortedByDescending { it.second }


                        setList(itemsCity)

                        val pieEntries = ArrayList<PieEntry>()
                        for (i in 0 until rain.size) {
                            val item = pairList?.get(i)
                            pieEntries.add(PieEntry(item?.second?.toFloat()!!, item.first.toString()))
                        }
                        val dataSet = PieDataSet(pieEntries, "Each centers result")


                        val colors = ArrayList<Int>()

                        for (c in ColorTemplate.VORDIPLOM_COLORS)
                            colors.add(c)

                        for (c in ColorTemplate.JOYFUL_COLORS)
                            colors.add(c)

                        for (c in ColorTemplate.COLORFUL_COLORS)
                            colors.add(c)

                        for (c in ColorTemplate.LIBERTY_COLORS)
                            colors.add(c)

                        for (c in ColorTemplate.PASTEL_COLORS)
                            colors.add(c)

                        colors.add(ColorTemplate.getHoloBlue())

                        dataSet.colors = colors


                        val data = PieData(dataSet)
                        rootView.chart.data = data
                        rootView.chart.invalidate()
                        rootView.chart.setHoleColor(Color.WHITE)
                        rootView.chart.setEntryLabelColor(Color.BLACK)


                        val tf = Typeface.createFromAsset(context?.assets, "OpenSans-Light.ttf")

                        rootView.chart.setCenterTextTypeface(tf)
                        rootView.chart.setCenterTextSize(14f)
                        rootView.chart.setCenterTextTypeface(tf)
                        chart.holeRadius = 45f
                        chart.transparentCircleRadius = 50f

                        chart!!.animateXY(700, 700)
                        chart.centerText = generateCenterText()
                        data.setValueTextSize(16f)
                        data.setValueTextColor(Color.BLACK)


                    }
                }
    }


    private fun bottomMenuInit(rootView: View) {
        val layoutParams = rootView.bottom_navigation_chart.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.behavior = BottomNavigationViewBehavior()
        rootView.bottom_navigation_chart.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                com.zaleksandr.aleksandr.myapplication.R.id.menu_year -> {
                    noteRefCollection
                            .whereGreaterThanOrEqualTo("time", startOfYear())
                            .whereLessThanOrEqualTo("time", endOfYear())
                            .orderBy("time", Query.Direction.DESCENDING)
                            .get().addOnCompleteListener { querydocumentSnapshot ->
                                if (querydocumentSnapshot.isSuccessful) {

                                    val items = querydocumentSnapshot.result!!
                                            .map { it.toObject<City>(City::class.java) }

                                    val itemsCity = items.groupBy {
                                        it.centers
                                    }
                                            .mapValues {
                                                it.value.sumBy {

                                                    Integer.parseInt(if (it.intro.isNullOrEmpty() || it.intro.isNullOrBlank() || it.intro == "") {
                                                        ("0").toString()
                                                    } else it.intro.toString())
                                                            .plus(Integer.parseInt(if (it.onedayWS.isNullOrEmpty() || it.onedayWS.isNullOrBlank() || it.onedayWS == "") {
                                                                ("0").toString()
                                                            } else it.onedayWS) * 3)
                                                            .plus(Integer.parseInt(if (it.twoDayWS.isNullOrEmpty() || it.twoDayWS.isNullOrBlank() || it.twoDayWS == "") {
                                                                ("0").toString()
                                                            } else it.twoDayWS) * 12)
                                                            .plus(Integer.parseInt(if (it.twOneDay.isNullOrEmpty() || it.twOneDay.isNullOrBlank() || it.twOneDay == "") {
                                                                ("0").toString()
                                                            } else it.twOneDay) * 40)
                                                            .plus(Integer.parseInt(if (it.nwet.isNullOrEmpty() || it.nwet.isNullOrBlank() || it.nwet == "") {
                                                                ("0").toString()
                                                            } else it.nwet) * 80)
                                                }
                                            }
                                            .toList()
                                            .filterNot { it.second == 0 }
                                            .sortedByDescending { it.second }


                                    setList(itemsCity)

                                    val pieEntries = ArrayList<PieEntry>()
                                    for (i in 0 until itemsCity.size) {
                                        val item = pairList?.get(i)
                                        pieEntries.add(PieEntry(item?.second?.toFloat()!!, item.first.toString()))
                                    }
                                    val dataSet = PieDataSet(pieEntries, "Each centers result")


                                    val colors = ArrayList<Int>()

                                    for (c in ColorTemplate.VORDIPLOM_COLORS)
                                        colors.add(c)

                                    for (c in ColorTemplate.JOYFUL_COLORS)
                                        colors.add(c)

                                    for (c in ColorTemplate.COLORFUL_COLORS)
                                        colors.add(c)

                                    for (c in ColorTemplate.LIBERTY_COLORS)
                                        colors.add(c)

                                    for (c in ColorTemplate.PASTEL_COLORS)
                                        colors.add(c)

                                    colors.add(ColorTemplate.getHoloBlue())

                                    dataSet.colors = colors


                                    val data = PieData(dataSet)
                                    chart.data = data
                                    chart.invalidate()
                                    chart.setHoleColor(Color.WHITE)
                                    chart.setEntryLabelColor(Color.BLACK)


                                    val tf = Typeface.createFromAsset(context?.assets, "OpenSans-Light.ttf")

                                    chart.setCenterTextTypeface(tf)
                                    chart.setCenterTextSize(14f)
                                    chart.setCenterTextTypeface(tf)
                                    chart.holeRadius = 45f
                                    chart.transparentCircleRadius = 50f

                                    chart!!.animateXY(700, 700)
                                    chart.centerText = generateCenterText()
                                    data.setValueTextSize(16f)
                                    data.setValueTextColor(Color.BLACK)

                                    rootView.chart.visibility = View.VISIBLE
                                    rootView.common_goal_zero.visibility = View.VISIBLE
                                    rootView.chart_common_result_zero.visibility = View.VISIBLE
                                    rootView.common_score_percent_zero.visibility = View.VISIBLE
                                }
                            }


                    var sumIntro = 0
                    var sumOneD1 = 0
                    var sumTwoD1 = 0
                    var sumTwent1 = 0
                    var sumNwet = 0
                    noteRefCollection.addSnapshotListener { queryDocumentSnapshots, _ ->

                        cityResult(queryDocumentSnapshots!!, rootView)
                        if (!queryDocumentSnapshots.isEmpty) {
                            queryDocumentSnapshots.forEach { documentSnapshot ->

                                val resultNote = documentSnapshot.toObject(City::class.java)

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

                                if (!resultNote.twOneDay.isNullOrEmpty()) {
                                    val twOneDay = Integer.parseInt(resultNote.twOneDay)
                                    sumTwent1 += twOneDay
                                }

                                if (!resultNote.nwet.isNullOrEmpty()) {
                                    val nwet = Integer.parseInt(resultNote.nwet)
                                    sumNwet += nwet
                                }
                            }
                            val df = DecimalFormat("#.##")
                            rootView.chart_common_result.text = (sumIntro + sumOneD1 * 3 + sumTwoD1 * 12 + sumTwent1 * 40 + sumNwet * 80).toString()

                            rootView.result_percent.text = ((df.format((sumIntro + sumOneD1 * 3 + sumTwoD1 * 12 + sumTwent1 * 40 + sumNwet * 80F) * 100F / 12620F)).toString())

                            rootView.chart_common_goal.text = "12620"
                            for (change in queryDocumentSnapshots.documentChanges) {
                                if (change.type == DocumentChange.Type.MODIFIED) {
                                    Log.d(ContentValues.TAG, "data:" + change.document.data)
                                }
                                val source = if (queryDocumentSnapshots.metadata.isFromCache)
                                    "local cache"
                                else
                                    "server"
                                Log.d(ContentValues.TAG, "Data fetched from $source")
                            }
                        }
                    }

                }
                com.zaleksandr.aleksandr.myapplication.R.id.menu_month -> {
                    noteRefCollection
                            .whereGreaterThanOrEqualTo("time", startOfMonth())
                            .whereLessThanOrEqualTo("time", endOfMonth())
                            .orderBy("time", Query.Direction.DESCENDING)
                            .get().addOnCompleteListener { querydocumentSnapshot ->
                                if (querydocumentSnapshot.isSuccessful) {

                                    val items = querydocumentSnapshot.result!!
                                            .map { it.toObject<City>(City::class.java) }
//                                .filterNot {
//                                    (it.name == "Kyiv Chondoso" || it.name == "Daniela Aldasoro ")
//
//                                }

                                    val itemsCity = items.groupBy {
                                        it.centers
                                    }
                                            .mapValues {
                                                it.value.sumBy {

                                                    Integer.parseInt(if (it.intro.isNullOrEmpty() || it.intro.isNullOrBlank() || it.intro == "") {
                                                        ("0").toString()
                                                    } else it.intro)
                                                            .plus(Integer.parseInt(if (it.onedayWS.isNullOrEmpty() || it.onedayWS.isNullOrBlank() || it.onedayWS == "") {
                                                                ("0").toString()
                                                            } else it.onedayWS) * 3)
                                                            .plus(Integer.parseInt(if (it.twoDayWS.isNullOrEmpty() || it.twoDayWS.isNullOrBlank() || it.twoDayWS == "") {
                                                                ("0").toString()
                                                            } else it.twoDayWS) * 12)

                                                }
                                            }
                                            .toList()
                                            .filterNot { it.second == 0 }
                                            .sortedByDescending { it.second }


                                    setList(itemsCity)

                                    val pieEntries = ArrayList<PieEntry>()
                                    for (i in 0 until itemsCity.size) {
                                        val item2 = pairList?.get(i)
                                        pieEntries.add(PieEntry(item2?.second?.toFloat()!!, item2.first.toString()))
                                    }
                                    val dataSet = PieDataSet(pieEntries, "Each centers result")


                                    val colors = ArrayList<Int>()

                                    for (c in ColorTemplate.VORDIPLOM_COLORS)
                                        colors.add(c)

                                    for (c in ColorTemplate.JOYFUL_COLORS)
                                        colors.add(c)

                                    for (c in ColorTemplate.COLORFUL_COLORS)
                                        colors.add(c)

                                    for (c in ColorTemplate.LIBERTY_COLORS)
                                        colors.add(c)

                                    for (c in ColorTemplate.PASTEL_COLORS)
                                        colors.add(c)

                                    colors.add(ColorTemplate.getHoloBlue())

                                    dataSet.colors = colors


                                    val data = PieData(dataSet)
                                    chart.data = data
                                    chart.invalidate()
                                    chart.setHoleColor(Color.WHITE)
                                    chart.setEntryLabelColor(Color.BLACK)


                                    val tf = Typeface.createFromAsset(context?.assets, "OpenSans-Light.ttf")

                                    chart.setCenterTextTypeface(tf)
                                    chart.setCenterTextSize(14f)
                                    chart.setCenterTextTypeface(tf)
                                    chart.holeRadius = 45f
                                    chart.transparentCircleRadius = 50f

                                    chart!!.animateXY(700, 700)
                                    chart.centerText = generateCenterText()
                                    data.setValueTextSize(16f)
                                    data.setValueTextColor(Color.BLACK)
                                    rootView.chart.visibility = View.VISIBLE
                                    rootView.common_goal_zero.visibility = View.VISIBLE
                                    rootView.chart_common_result_zero.visibility = View.VISIBLE
                                    rootView.common_score_percent_zero.visibility = View.VISIBLE

                                    if (itemsCity.isEmpty()) {
                                        rootView.common_goal_zero.visibility = View.GONE
                                        rootView.chart_common_result_zero.visibility = View.GONE
                                        rootView.common_score_percent_zero.visibility = View.GONE
                                        rootView.empty_chart.visibility = View.VISIBLE
                                        rootView.chart.visibility = View.GONE
                                    }

                                }

                            }


                    var sumIntro = 0
                    var sumOneD1 = 0
                    var sumTwoD1 = 0

                    noteRefCollection
                            .whereGreaterThanOrEqualTo("time", startOfMonth())
                            .whereLessThanOrEqualTo("time", endOfMonth())
                            .addSnapshotListener { queryDocumentSnapshots, _ ->

                                cityResult(queryDocumentSnapshots!!, rootView)
                                if (!queryDocumentSnapshots.isEmpty) {
                                    queryDocumentSnapshots.forEach { documentSnapshot ->

                                        val resultNote = documentSnapshot.toObject(City::class.java)

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
                                    }

                                    val df = DecimalFormat("#.##")
                                    rootView.chart_common_result.text = (sumIntro + sumOneD1 * 3 + sumTwoD1 * 12).toString()

                                    rootView.result_percent.text = ((df.format((sumIntro + sumOneD1 * 3 + sumTwoD1 * 12) * 100F / 620F)).toString())

                                    rootView.chart_common_goal.text = "620"
                                    for (change in queryDocumentSnapshots.documentChanges) {
                                        if (change.type == DocumentChange.Type.MODIFIED) {
                                            Log.d(ContentValues.TAG, "data:" + change.document.data)
                                        }
                                        val source = if (queryDocumentSnapshots.metadata.isFromCache)
                                            "local cache"
                                        else
                                            "server"
                                        Log.d(ContentValues.TAG, "Data fetched from $source")
                                    }
                                }
                            }
                }
                com.zaleksandr.aleksandr.myapplication.R.id.menu_week -> {
                    noteRefCollection
                            .whereGreaterThanOrEqualTo("time", startOfWeek())
                            .whereLessThanOrEqualTo("time", endOfWeek())
                            .orderBy("time", Query.Direction.DESCENDING)
                            .get().addOnCompleteListener { querydocumentSnapshot ->
                                if (querydocumentSnapshot.isSuccessful) {

                                    val items = querydocumentSnapshot.result!!
                                            .map { it.toObject<City>(City::class.java) }

                                    val itemsCity = items.groupBy {
                                        it.centers
                                    }
                                            .mapValues {
                                                it.value.sumBy {

                                                    Integer.parseInt(if (it.intro.isNullOrEmpty() || it.intro.isNullOrBlank() || it.intro == "") {
                                                        ("0").toString()
                                                    } else it.intro.toString())
                                                            .plus(Integer.parseInt(if (it.onedayWS.isNullOrEmpty() || it.onedayWS.isNullOrBlank() || it.onedayWS == "") {
                                                                ("0").toString()
                                                            } else it.onedayWS) * 3)
                                                            .plus(Integer.parseInt(if (it.twoDayWS.isNullOrEmpty() || it.twoDayWS.isNullOrBlank() || it.twoDayWS == "") {
                                                                ("0").toString()
                                                            } else it.twoDayWS) * 12)


                                                }
                                            }
                                            .toList()
                                            .filterNot { it.second == 0 }
                                            .sortedByDescending { it.second }


                                    setList(itemsCity)

                                    val pieEntries = ArrayList<PieEntry>()
                                    for (i in 0 until itemsCity.size) {
                                        val item3 = pairList?.get(i)
                                        pieEntries.add(PieEntry(item3?.second?.toFloat()!!, item3.first.toString()))
                                    }
                                    val dataSet = PieDataSet(pieEntries, "Each centers result")


                                    val colors = ArrayList<Int>()

                                    for (c in ColorTemplate.VORDIPLOM_COLORS)
                                        colors.add(c)

                                    for (c in ColorTemplate.JOYFUL_COLORS)
                                        colors.add(c)

                                    for (c in ColorTemplate.COLORFUL_COLORS)
                                        colors.add(c)

                                    for (c in ColorTemplate.LIBERTY_COLORS)
                                        colors.add(c)

                                    for (c in ColorTemplate.PASTEL_COLORS)
                                        colors.add(c)

                                    colors.add(ColorTemplate.getHoloBlue())

                                    dataSet.colors = colors


                                    val data = PieData(dataSet)
                                    chart.data = data
                                    chart.invalidate()
                                    chart.setHoleColor(Color.WHITE)
                                    chart.setEntryLabelColor(Color.BLACK)


                                    val tf = Typeface.createFromAsset(context?.assets, "OpenSans-Light.ttf")

                                    chart.setCenterTextTypeface(tf)
                                    chart.setCenterTextSize(14f)
                                    chart.setCenterTextTypeface(tf)
                                    chart.holeRadius = 45f
                                    chart.transparentCircleRadius = 50f

                                    chart!!.animateXY(700, 700)
                                    chart.centerText = generateCenterText()
                                    data.setValueTextSize(16f)
                                    data.setValueTextColor(Color.BLACK)
                                    rootView.empty_chart.visibility = View.GONE
                                    rootView.chart.visibility = View.VISIBLE
                                    rootView.common_goal_zero.visibility = View.VISIBLE
                                    rootView.chart_common_result_zero.visibility = View.VISIBLE
                                    rootView.common_score_percent_zero.visibility = View.VISIBLE


                                    if (itemsCity.isEmpty()) {
                                        rootView.common_goal_zero.visibility = View.GONE
                                        rootView.chart_common_result_zero.visibility = View.GONE
                                        rootView.common_score_percent_zero.visibility = View.GONE
                                        rootView.empty_chart.visibility = View.VISIBLE
                                        rootView.chart.visibility = View.GONE
                                    }
                                }
                            }
                    var sumIntro = 0
                    var sumOneD1 = 0
                    var sumTwoD1 = 0

                    noteRefCollection
                            .whereGreaterThanOrEqualTo("time", startOfWeek())
                            .whereLessThanOrEqualTo("time", endOfWeek())
                            .addSnapshotListener { queryDocumentSnapshots, _ ->

                                cityResult(queryDocumentSnapshots!!, rootView)
                                if (!queryDocumentSnapshots.isEmpty) {
                                    queryDocumentSnapshots.forEach { documentSnapshot ->

                                        val resultNote = documentSnapshot.toObject(City::class.java)

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
                                    }
                                    val df = DecimalFormat("#.##")
                                    rootView.chart_common_result.text = (sumIntro + sumOneD1 * 3 + sumTwoD1 * 12).toString()

                                    rootView.result_percent.text = ((df.format((sumIntro + sumOneD1 * 3 + sumTwoD1 * 12) * 100F / 143F)).toString())
                                    rootView.chart_common_goal.text = "143"
                                    for (change in queryDocumentSnapshots.documentChanges) {
                                        if (change.type == DocumentChange.Type.MODIFIED) {
                                            Log.d(ContentValues.TAG, "data:" + change.document.data)
                                        }
                                        val source = if (queryDocumentSnapshots.metadata.isFromCache)
                                            "local cache"
                                        else
                                            "server"
                                        Log.d(ContentValues.TAG, "Data fetched from $source")
                                    }
                                }
                            }
                }

                else -> {
                }
            }
            true
        }
    }

    fun generateCenterText(): SpannableString {
        val s = SpannableString("Centers\nYear 2019")
        s.setSpan(RelativeSizeSpan(2f), 0, 8, 0)
        s.setSpan(ForegroundColorSpan(Color.GRAY), 8, s.length, 0)
        return s
    }

    fun setList(pairList: List<Pair<String?, Int>>) {
        this.pairList = pairList
    }

    override fun onResume() {
        super.onResume()
        (this.activity!!.toolbar as Toolbar).title = "Each center result"
    }
}

