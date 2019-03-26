package com.zaleksandr.aleksandr.myapplication.ui.chart

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.firestore.Query
import com.zaleksandr.aleksandr.myapplication.model.City
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_chart.view.*


class ChartFragment : Fragment() {

    var toolbar: Toolbar? = null
    private val rain: ArrayList<Float> = ArrayList()
    private val monthNames: ArrayList<String> = ArrayList()
    private var pairList: List<Pair<String?, Int>>? = null
    private val noteRefCollection = firestoreInstance.collection("NewCity")
//    private var chart: PieChart? = null
//    private var seekBarX: SeekBar? = null
//    private var seekBarY: SeekBar? = null
//    private var tvX: TextView? = null
//    private var tvY: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.zaleksandr.aleksandr.myapplication.R.layout.fragment_chart, container, false)

//        chart?.setOnChartValueSelectedListener(this)
//        chart?.setOnChartValueSelectedListener(this)


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



        setupPieChart(rootView)


//        rootView.line_chart.apply {
//            isDragEnabled = true
//            setTouchEnabled(true)
//            setScaleEnabled(false)
//        }

//        val yValue: ArrayList<Entry> = ArrayList()
//        val set1: LineDataSet
//
//        yValue.add(Entry(0F, 60F))
//        yValue.add(Entry(1F, 40F))
//        yValue.add(Entry(2F, 60F))
//        yValue.add(Entry(3F, 70F))
//        yValue.add(Entry(4F, 20F))
//        yValue.add(Entry(5F, 30F))
//        set1 = LineDataSet(yValue, "Date Set 1")
//        set1.fillAlpha
//
//        set1.color = Color.RED
//        set1.lineWidth = 2F
//        set1.valueTextSize = 12F
//        set1.valueTextColor = Color.GREEN
//
//
//        val dateSets: ArrayList<ILineDataSet> = ArrayList()
//        dateSets.add(set1)
//        rootView.chart.data
        return rootView
    }

    fun setupPieChart(rootView: View) {


        noteRefCollection
                .orderBy("time", Query.Direction.DESCENDING)
                .get().addOnCompleteListener { querydocumentSnapshot ->
                    if (querydocumentSnapshot.isSuccessful) {

                        val items = querydocumentSnapshot.result!!
                                .map { it.toObject<City>(City::class.java) }
                                .filterNot {
                                    (it.name == "Kyiv Chondoso" || it.name == "Daniela Aldasoro ")

                                }

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
                        data.setValueTextSize(16f)
                        data.setValueTextColor(Color.BLACK)

                    }

//                    if (querydocumentSnapshot.result!!.size() != 0) {
////                            empty_individual_result_fragment.visibility = View.GONE
//                        mLastQueriedDocument = querydocumentSnapshot.result!!.documents[querydocumentSnapshot.result!!.size() - 1]
//                        adapter?.notifyDataSetChanged()
//                    } else {
////                            empty_individual_result_fragment.visibility = View.VISIBLE
//                        adapter?.notifyDataSetChanged()
//                    }
                }






//        var i = 0
//       val pieEntries: ArrayList<PieEntry>? =null
//       pieEntries?.forEach { rain ->
//
//           pieEntries.add()

    }

    fun setList(pairList: List<Pair<String?, Int>>) {
        this.pairList = pairList
    }

    override fun onResume() {
        super.onResume()
        (this.activity!!.toolbar as Toolbar).title = "Each center result"
    }
}

