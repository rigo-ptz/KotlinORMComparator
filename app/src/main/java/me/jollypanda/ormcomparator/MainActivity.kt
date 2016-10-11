package me.jollypanda.ormcomparator

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import kotlinx.android.synthetic.main.activity_main.*
import me.jollypanda.ormcomparator.utils.ORMResult
import me.jollypanda.ormcomparator.utils.ORM_COUNT

/**
 * Activity contains button to start testWrite,
 * results of tests
 *
 * @author Yamushev Igor
 * @since  09.09.16
 */
class MainActivity : BaseActivity(), MainContract.View {

    /** 0 element is a counter for write test attempts */
    var mWriteSumList = FloatArray(ORM_COUNT + 1, { i -> 0f})
    /** 0 element is a counter for read test attempts */
    var mReadSumList = FloatArray(ORM_COUNT + 1, {i -> 0f})

    enum class Action {
        READ, WRITE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addChart(Action.WRITE)
        addChart(Action.READ)

        MainPresenter.getAndShowLastResultsFromDB(this)

        btnStartWriteTest.setOnClickListener {
            pbMainWrite.visibility = View.VISIBLE
            MainPresenter.startWriteTests(this@MainActivity, applicationContext)
        }

        btnStartReadTest.setOnClickListener {
            pbMainRead.visibility = View.VISIBLE
            MainPresenter.startReadTests(this@MainActivity, applicationContext)
        }
    }

    override fun showResult(result: ORMResult?, i: Int) {
        if (result != null) {
            val tvRes = TextView(this)
            tvRes.text = result.toString()
            tvRes.tag = result.workTimeMills
            tvRes.layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
            if (result.readOrWrite == 1) {
                llWriteInfoContainer.addView(tvRes)
                mWriteSumList[i + 1] = mWriteSumList[i + 1] + result.workTimeMills
            } else {
                llReadInfoContainer.addView(tvRes)
                mReadSumList[i + 1] = mReadSumList[i + 1] + result.workTimeMills
            }

            if (i == ORM_COUNT - 1) {
                if (result.readOrWrite == 1) {
                    btnStartReadTest.isEnabled = true
                    pbMainWrite.visibility = View.GONE
                    mWriteSumList[0] = mWriteSumList[0] + 1
                } else {
                    pbMainRead.visibility = View.GONE
                    mReadSumList[0] = mReadSumList[0] + 1
                }
                val divider = View(this)
                divider.setBackgroundColor(Color.GRAY)
                divider.setPadding(0, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        8f,
                        resources.displayMetrics
                ).toInt(),
                        0, 0)
                divider.layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                2f,
                                resources.displayMetrics
                        ).toInt())
                if (result.readOrWrite == 1) {
                    llWriteInfoContainer.addView(divider)
                    calculateAverage(Action.WRITE)
                } else {
                    llReadInfoContainer.addView(divider)
                    calculateAverage(Action.READ)
                }
            }
            invalidateChart(i, result)
        }
    }

    private fun calculateAverage(action: Action) {
        when (action) {
            Action.WRITE -> {
                var list = mWriteSumList.clone()
                for (i in 1..list.size - 1)
                    list[i] = list[i] / list[0]
                addAverageToContainer(list, llWriteAverageContainer)
            }
            Action.READ -> {
                var list = mReadSumList.clone()
                for (i in 1..list.size - 1)
                    list[i] = list[i] / list[0]
                addAverageToContainer(list, llReadAverageContainer)
            }
        }
    }

    private fun addAverageToContainer(list: FloatArray, container: LinearLayout) {
        container.removeAllViews()
        for (i in 1..list.size - 1) {
            val tv = TextView(this)
            tv.text = list[i].toString()
            tv.layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
            container.addView(tv)
        }
    }

    private fun addChart(action: Action) {
        val barChart = BarChart(this)
        val barData = BarData()
        barChart.data = barData
        val rightAxis = barChart.axisRight
        rightAxis.isEnabled = false
        barChart.layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        250f,
                        resources.displayMetrics
                ).toInt())
        when (action) {
            Action.WRITE -> llWriteChartContainer.addView(barChart)
            Action.READ -> llReadChartContainer.addView(barChart)
        }

        barChart.invalidate()
    }

    private fun invalidateChart(i: Int, result: ORMResult) {
        val chart: BarChart
        if (result.readOrWrite == 1)
            chart = llWriteChartContainer.getChildAt(0) as BarChart
        else
            chart = llReadChartContainer.getChildAt(0) as BarChart

        val data = chart.data
        if (data  != null) {
            var set = data.getDataSetByIndex(0)
            if (set == null) {
                set = createSet()
                data.addDataSet(set)
            }
            data.addEntry(BarEntry(i.toFloat(), result.workTimeMills.toFloat()), 0)
            data.notifyDataChanged()
            chart.notifyDataSetChanged()
            chart.invalidate()
        }
    }

    private fun createSet(): IBarDataSet? {
        val set = BarDataSet(mutableListOf(), "Orm Results")
        set.color = Color.rgb(240, 99, 99)
        set.highLightColor = Color.rgb(190, 190, 190)
        set.axisDependency = YAxis.AxisDependency.LEFT
        set.valueTextSize = 10f
        return set
    }

}