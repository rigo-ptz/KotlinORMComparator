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
import me.jollypanda.ormcomparator.utils.OrmWriteSubscriber
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
    }

    private fun getAndShowLastResultsFromDB() {
        val realm = Realm.getDefaultInstance()
        var results: List<ORMResult>? = realm.where(ORMResult::class.java).equalTo("readOrWrite", 1).findAll().toList()

        if (results != null)
            for (i in 0..results.size - 1)
                showResult(results.elementAt(i), i)
    }

    fun showResult(result: ORMResult?, i: Int) {
        if (result != null) {
            val tvRes = TextView(this)
            tvRes.text = result.toString()
            tvRes.layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
            llWriteInfoContainer.addView(tvRes)

            if (i == ORM_COUNT - 1) {
                pbMainWrite.visibility = View.GONE
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
                llWriteInfoContainer.addView(divider)
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
                .subscribe(OrmWriteSubscriber(this))
    }

}