package me.jollypanda.ormcomparator

import android.content.Context
import io.realm.Realm
import me.jollypanda.ormcomparator.activeandroid.ActiveAndroidTester
import me.jollypanda.ormcomparator.cupboard.CupboardTester
import me.jollypanda.ormcomparator.dbflow.DBFlowTester
import me.jollypanda.ormcomparator.green_dao.GreenDaoTester
import me.jollypanda.ormcomparator.ollie.OllieTester
import me.jollypanda.ormcomparator.orm_lite.OrmLiteTester
import me.jollypanda.ormcomparator.realm.RealmTester
import me.jollypanda.ormcomparator.sugar_orm.SugarOrmTester
import me.jollypanda.ormcomparator.utils.ORMResult
import me.jollypanda.ormcomparator.utils.OrmSubscriber
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Listens to user actions from the UI ({@link MainActivity}), retrieves the data and updates
 * the UI as required.
 *
 * @author Yamushev Igor
 * @since  09.10.16
 */
object MainPresenter : MainContract.Presenter {

    override fun startWriteTests(context: Context, appContext: Context) {
        val realmObservable = RealmTester(context).getWriteObservable()
        val ormLiteObservable = OrmLiteTester(context).getWriteObservable()
        val greenDaoObservable = GreenDaoTester(appContext).getWriteObservable()
        val sugarOrmObservable = SugarOrmTester(context).getWriteObservable()
        val activeAndroidObservable = ActiveAndroidTester(context).getWriteObservable()
        val dbflowObservable = DBFlowTester(context).getWriteObservable()
        val ollieObservable = OllieTester(context).getWriteObservable() // seems that it's not work
        val cupboardObservable = CupboardTester(context).getWriteObservable()

        val resultObservable = Observable.concat(realmObservable,
                ormLiteObservable,
                greenDaoObservable,
                sugarOrmObservable,
                activeAndroidObservable,
                dbflowObservable,
                cupboardObservable)

        resultObservable.compose((context as MainActivity).bindToLifecycle<ORMResult>())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(OrmSubscriber(context))
    }

    override fun startReadTests(context: Context,  appContext: Context) {
        val realmObservable = RealmTester(context).getReadObservable()
        val ormLiteObservable = OrmLiteTester(context).getReadObservable()
        val greenDaoObservable = GreenDaoTester(appContext).getReadObservable()
        val sugarOrmObservable = SugarOrmTester(context).getReadObservable()
        val activeAndroidObservable = ActiveAndroidTester(context).getReadObservable()
        val dbflowObservable = DBFlowTester(context).getReadObservable()
//        val ollieObservable = OllieTester(this).getReadObservable() // seems that it's not work
        val cupboardObservable = CupboardTester(context).getReadObservable()

        val resultObservable = Observable.concat(realmObservable,
                ormLiteObservable,
                greenDaoObservable,
                sugarOrmObservable,
                activeAndroidObservable,
                dbflowObservable,
                cupboardObservable)

        resultObservable.compose((context as MainActivity).bindToLifecycle<ORMResult>())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(OrmSubscriber(context))
    }

    fun getAndShowLastResultsFromDB(view: MainContract.View) {
        val realm = Realm.getDefaultInstance()
        val resultsWrite: List<ORMResult>? = realm.where(ORMResult::class.java).equalTo("readOrWrite", 1).findAll().toList()
        val resultsRead: List<ORMResult>? = realm.where(ORMResult::class.java).equalTo("readOrWrite", 0).findAll().toList()

        if (resultsWrite != null)
            for (i in 0..resultsWrite.size - 1)
                view.showResult(resultsWrite.elementAt(i), i)

        if (resultsRead != null)
            for (i in 0..resultsRead.size - 1)
                view.showResult(resultsRead.elementAt(i), i)
    }
}