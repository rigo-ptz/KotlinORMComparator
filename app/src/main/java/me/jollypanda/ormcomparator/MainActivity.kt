package me.jollypanda.ormcomparator

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import me.jollypanda.ormcomparator.activeandroid.ActiveAndroidTester
import me.jollypanda.ormcomparator.cupboard.CupboardTester
import me.jollypanda.ormcomparator.dbflow.DBFlowTester
import me.jollypanda.ormcomparator.green_dao.GreenDaoTester
import me.jollypanda.ormcomparator.ollie.OllieTester
import me.jollypanda.ormcomparator.orm_lite.OrmLiteTester
import me.jollypanda.ormcomparator.realm.RealmTester
import me.jollypanda.ormcomparator.sugar_orm.SugarOrmTester
import me.jollypanda.ormcomparator.utils.ORMResult
import me.jollypanda.ormcomparator.utils.ORM_COUNT
import me.jollypanda.ormcomparator.utils.OrmSubscriber
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Activity contains button to start testWrite,
 * results of tests
 *
 * @author Yamushev Igor
 * @since  09.09.16
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getAndShowLastResultsFromDB()

        btnStartWriteTest.setOnClickListener {
            pbMainWrite.visibility = View.VISIBLE
            startWriteTests()
        }

        btnStartReadTest.setOnClickListener {
            pbMainRead.visibility = View.VISIBLE
            startReadTests()
        }
    }

    private fun getAndShowLastResultsFromDB() {
        val realm = Realm.getDefaultInstance()
        var resultsWrite: List<ORMResult>? = realm.where(ORMResult::class.java).equalTo("readOrWrite", 1).findAll().toList()
        var resultsRead: List<ORMResult>? = realm.where(ORMResult::class.java).equalTo("readOrWrite", 0).findAll().toList()

        if (resultsWrite != null)
            for (i in 0..resultsWrite.size - 1)
                showResult(resultsWrite.elementAt(i), i)

        if (resultsRead != null)
            for (i in 0..resultsRead.size - 1)
                showResult(resultsRead.elementAt(i), i)
    }

    fun showResult(result: ORMResult?, i: Int) {
        if (result != null) {
            val tvRes = TextView(this)
            tvRes.text = result.toString()
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
                if (result.readOrWrite == 1)
                    llWriteInfoContainer.addView(divider)
                else
                    llReadInfoContainer.addView(divider)
            }
        }
    }

    private fun startWriteTests() {
        val realmObservable = RealmTester(this).getWriteObservable()
        val ormLiteObservable = OrmLiteTester(this).getWriteObservable()
        val greenDaoObservable = GreenDaoTester(applicationContext).getWriteObservable()
        val sugarOrmObservable = SugarOrmTester(this).getWriteObservable()
        val activeAndroidObservable = ActiveAndroidTester(this).getWriteObservable()
        val dbflowObservable = DBFlowTester(this).getWriteObservable()
        val ollieObservable = OllieTester(this).getWriteObservable() // seems that it's not work
        val cupboardObservable = CupboardTester(this).getWriteObservable()

        val resultObservable = Observable.concat(realmObservable,
                ormLiteObservable,
                greenDaoObservable,
                sugarOrmObservable,
                activeAndroidObservable,
                dbflowObservable,
                cupboardObservable)

        resultObservable.compose(bindToLifecycle<ORMResult>())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(OrmSubscriber(this))
    }

    private fun startReadTests() {
        val realmObservable = RealmTester(this).getReadObservable()
        val ormLiteObservable = OrmLiteTester(this).getReadObservable()
        val greenDaoObservable = GreenDaoTester(applicationContext).getReadObservable()
        val sugarOrmObservable = SugarOrmTester(this).getReadObservable()
        val activeAndroidObservable = ActiveAndroidTester(this).getReadObservable()
        val dbflowObservable = DBFlowTester(this).getReadObservable()
//        val ollieObservable = OllieTester(this).getReadObservable() // seems that it's not work
        val cupboardObservable = CupboardTester(this).getReadObservable()

        val resultObservable = Observable.concat(realmObservable,
                ormLiteObservable,
                greenDaoObservable,
                sugarOrmObservable,
                activeAndroidObservable,
                dbflowObservable,
                cupboardObservable)

        resultObservable.compose(bindToLifecycle<ORMResult>())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(OrmSubscriber(this))
    }

}