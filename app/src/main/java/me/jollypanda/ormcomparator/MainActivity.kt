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
            if (result.readOrWrite == 1)
                llWriteInfoContainer.addView(tvRes)
            else
                llReadInfoContainer.addView(tvRes)

            if (i == ORM_COUNT - 1) {
                if (result.readOrWrite == 1) {
                    btnStartReadTest.isEnabled = true
                    pbMainWrite.visibility = View.GONE
                } else
                    pbMainRead.visibility = View.GONE
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
                    calculateAverage(llWriteInfoContainer)
                } else {
                    llReadInfoContainer.addView(divider)
                    calculateAverage(llReadInfoContainer)
                }
            }
            invalidateChart(i, result)
        }
    }

    private fun calculateAverage(container: LinearLayout) {
        var list: MutableList<Float> = mutableListOf()
        for (i in 0..ORM_COUNT - 1)
            list.add(i, 0f)
        var counter: Float = 0f
        for (i in 0..container.childCount - 1) {
            if (container.getChildAt(i) is TextView) {
                val child = container.getChildAt(i)
                val pos = i.mod(ORM_COUNT + 1)
                list[pos] = list[pos] + child.tag.toString().toFloat()
            } else
                counter += 1
        }
        val size = list.size
        for (i in 0..size - 1) {
            list[i] = list[i] / counter
        }

        if (container.id == R.id.llWriteInfoContainer)
            addAverageToContainer(list, llWriteAverageContainer)
        else
            addAverageToContainer(list, llReadAverageContainer)

    }

    private fun addAverageToContainer(list: MutableList<Float>, container: LinearLayout) {
        container.removeAllViews()
        for (i in 0..list.size - 1) {
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