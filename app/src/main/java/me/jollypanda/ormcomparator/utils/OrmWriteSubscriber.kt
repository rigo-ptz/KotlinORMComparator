package me.jollypanda.ormcomparator.utils

import android.content.Context
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
        if (counter == ORM_COUNT)
            (context as MainActivity).pbMainWrite.visibility = View.GONE
        val tvRes = TextView(context)
        tvRes.text = result.toString()
        tvRes.layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        (context as MainActivity).llWriteInfoContainer.addView(tvRes)
    }

    override fun onCompleted() {
    }

    override fun onError(e: Throwable?) {
        (context as MainActivity).pbMainWrite.visibility = View.GONE
    }

}