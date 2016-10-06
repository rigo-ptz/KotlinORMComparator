package me.jollypanda.ormcomparator.utils

import android.content.Context
import android.view.View
import io.realm.Realm
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
class OrmSubscriber(val context: Context) : Subscriber<ORMResult>() {

    var counter: Int = 0

    override fun onNext(result: ORMResult?) {
        saveOrmResult(result, counter)
        (context as MainActivity).showResult(result, counter)
        counter++
    }

    override fun onCompleted() {
    }

    override fun onError(e: Throwable?) {
        (context as MainActivity).pbMainWrite.visibility = View.GONE
    }

    private fun saveOrmResult(result: ORMResult?, counter: Int) {
        val realm = Realm.getDefaultInstance()
        if (counter == 0)
            if (result?.readOrWrite == 1)
                realm.executeTransaction { realm ->
                    realm.where(ORMResult::class.java).equalTo("readOrWrite", 1).findAll().deleteAllFromRealm()
                }
            else
                realm.executeTransaction { realm ->
                    realm.where(ORMResult::class.java).equalTo("readOrWrite", 0).findAll().deleteAllFromRealm()
                }


        realm.executeTransaction { realm ->
            realm.copyToRealm(result)
        }
        realm.close()
    }

}