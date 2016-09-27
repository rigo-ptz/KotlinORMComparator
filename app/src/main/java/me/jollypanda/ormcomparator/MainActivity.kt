package me.jollypanda.ormcomparator

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import me.jollypanda.ormcomparator.orm_lite.OrmLiteTester
import me.jollypanda.ormcomparator.realm.RealmTester
import me.jollypanda.ormcomparator.utils.ORMResult
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

        btnStartWriteTest.setOnClickListener {
            pbMainWrite.visibility = View.VISIBLE
            startTests()
        }
    }

    private fun startTests() {
        val realmObservable = RealmTester(this).getObservable()
        val ormLiteObservable = OrmLiteTester(this).getObservable()

        val resultObservable = Observable.merge(realmObservable, ormLiteObservable)

        resultObservable.compose(bindToLifecycle<ORMResult>())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(OrmWriteSubscriber(this))
    }

}