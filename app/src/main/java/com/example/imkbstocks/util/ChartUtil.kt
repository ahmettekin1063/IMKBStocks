package com.example.imkbstocks.util

import android.content.Context
import android.graphics.Color
import android.graphics.DashPathEffect
import com.github.mikephil.charting.components.MarkerView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.utils.MPPointF
import com.example.imkbstocks.R
import com.example.imkbstocks.model.detailmodelpackage.GraphicData
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils


fun LineChart.renderData(data: List<GraphicData?>?) {

    val llXAxis = LimitLine(10f, "Index 10")
    llXAxis.lineWidth = 4f
    llXAxis.enableDashedLine(10f, 10f, 0f)
    llXAxis.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
    llXAxis.textSize = 10f
    val xAxis: XAxis = this.xAxis
    xAxis.enableGridDashedLine(10f, 10f, 0f)
    xAxis.axisMaximum = 30f
    xAxis.axisMinimum = 0f
    xAxis.setDrawLimitLinesBehindData(true)
    val ll1 = LimitLine(data.getMaxStockValue() * 1.2f, "Upper Limit")
    ll1.lineWidth = 4f
    ll1.enableDashedLine(10f, 10f, 0f)
    ll1.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
    ll1.textSize = 10f
    val ll2 = LimitLine(0f, "Minimum Limit")
    ll2.lineWidth = 4f
    ll2.enableDashedLine(10f, 10f, 0f)
    ll2.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
    ll2.textSize = 10f
    val leftAxis: YAxis = this.axisLeft
    leftAxis.removeAllLimitLines()
    leftAxis.addLimitLine(ll1)
    leftAxis.addLimitLine(ll2)
    leftAxis.axisMaximum = data.getMaxStockValue() * 1.5f
    leftAxis.axisMinimum = 0f
    leftAxis.enableGridDashedLine(10f, 10f, 0f)
    leftAxis.setDrawZeroLine(false)
    leftAxis.setDrawLimitLinesBehindData(false)
    this.axisRight.isEnabled = false
    val set1: LineDataSet
    if (this.data != null && this.data.dataSetCount > 0) {
        set1 = this.data.getDataSetByIndex(0) as LineDataSet
        set1.values = data.getDetailDataForChart()
        this.data.notifyDataChanged()
        this.notifyDataSetChanged()
    } else {
        set1 = LineDataSet(data.getDetailDataForChart(), "Sample Data");
        set1.setDrawIcons(false)
        set1.enableDashedLine(10f, 5f, 0f)
        set1.enableDashedHighlightLine(10f, 5f, 0f)
        set1.color = Color.RED
        set1.setCircleColor(Color.RED)
        set1.lineWidth = 1f
        set1.circleRadius = 3f
        set1.setDrawCircleHole(false)
        set1.valueTextSize = 9f
        set1.setDrawFilled(true)
        set1.formLineWidth = 1f
        set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
        set1.formSize = 15f

        if (Utils.getSDKInt() >= 18) {
            val drawable = ContextCompat.getDrawable(this.context, R.drawable.fade_red)
            set1.fillDrawable = drawable
        } else {
            set1.fillColor = Color.RED
        }
        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(set1)
        val data = LineData(dataSets)
        this.data = data
    }
    this.invalidate()
}

fun List<GraphicData?>?.getMaxStockValue(): Float {
    var maxStockValue = 0f
    this?.let {
        for (temp in it) {
            temp?.value?.let { value ->
                if (value > maxStockValue) maxStockValue = value.toFloat()
            }
        }
    }
    return maxStockValue
}

fun List<GraphicData?>?.getDetailDataForChart(): List<Entry> {
    val detailDataForChart = arrayListOf<Entry>()
    this?.let {
        for (temp in it) {
            temp?.day?.let { day ->
                temp.value?.let { value ->
                    detailDataForChart += Entry(day.toFloat(), value.toFloat())
                }
            }
        }
    }
    return detailDataForChart
}

class MyMarkerView(context: Context?, layoutResource: Int) : MarkerView(context, layoutResource) {
    private val tvContent: TextView = findViewById(R.id.tvContent)

    override fun refreshContent(e: Entry, highlight: Highlight) {
        if (e is CandleEntry) {
            tvContent.text = "" + Utils.formatNumber(e.high, 0, true)
        } else {
            tvContent.text = "" + Utils.formatNumber(e.y, 0, true)
        }
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }
}

