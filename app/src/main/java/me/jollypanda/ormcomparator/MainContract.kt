package me.jollypanda.ormcomparator

import android.content.Context
import me.jollypanda.ormcomparator.utils.ORMResult

/**
 * This specifies the contract between the view and the presenter.
 *
 * @author Yamushev Igor
 * @since  09.10.16
 */
interface MainContract {

    interface View {
        fun showResult(result: ORMResult?, i: Int)
    }

    interface Presenter {
        fun startWriteTests(context: Context, appContext: Context)
        fun startReadTests(context: Context, appContext: Context)
    }
}