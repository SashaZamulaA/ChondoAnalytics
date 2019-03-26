package com.zaleksandr.aleksandr.myapplication.ui.chart

import android.graphics.Color
import android.os.Build.VERSION_CODES.P
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_chart.*
import kotlinx.android.synthetic.main.fragment_chart.view.*


class ChartFragment : Fragment() {

    var toolbar: Toolbar? = null
    private val rain: ArrayList<Float> = ArrayList()
    private val monthNames: ArrayList<String> = ArrayList()

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
        rain.add(58.6F)
        rain.add(68.6F)


        monthNames.add("Kyiv")
        monthNames.add("Kharkiv")
        monthNames.add("Dnepr")
        monthNames.add("Zhytomyr")
        monthNames.add("Lviv")
        monthNames.add("Odessa")
        monthNames.add("Chernigov")
        monthNames.add("Other")


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

    fun setupPieChart(rootView: View){


        val pieEntries = ArrayList<PieEntry>()
            for (i in 0 until rain.size) {
            pieEntries.add(PieEntry(rain[i], monthNames[i])) }

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

//        var i = 0
//       val pieEntries: ArrayList<PieEntry>? =null
//       pieEntries?.forEach { rain ->
//
//           pieEntries.add()

    }

    override fun onResume() {
        super.onResume()
        (this.activity!!.toolbar as Toolbar).title = "Each center result"
    }
}

