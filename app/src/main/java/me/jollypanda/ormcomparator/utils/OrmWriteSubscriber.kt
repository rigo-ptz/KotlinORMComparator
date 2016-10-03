package me.jollypanda.ormcomparator.utils

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import me.jollypanda.ormcomparator.MainActivity
import rx.Subscriber

/**
 * Subscriber for all ORM testers in MainActivity.
 *
 * @author Yamushev Igor
 * @since  26.09.16
 * @see MainActivity
 */
class OrmWriteSubscriber(val context: Context) : Subscriber<ORMResult>() {

    var counter: Int = 0

    override fun onNext(result: ORMResult?) {
        counter++
        val tvRes = TextView(context)
        tvRes.text = result.toString()
        tvRes.layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        (context as MainActivity).llWriteInfoContainer.addView(tvRes)

        if (counter == ORM_COUNT) {
            (context as MainActivity).pbMainWrite.visibility = View.GONE
            val divider = View(context)
            divider.setBackgroundColor(Color.GRAY)
            divider.setPadding(0, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, context.resources.displayMetrics).toInt(),
                    0, 0)
            divider.layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2f, context.resources.displayMetrics).toInt())
            (context as MainActivity).llWriteInfoContainer.addView(divider)
        }
    }

    override fun onCompleted() {
    }

    override fun onError(e: Throwable?) {
        (context as MainActivity).pbMainWrite.visibility = View.GONE
    }

}