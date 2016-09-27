package me.jollypanda.ormcomparator.realm

import android.content.Context
import io.realm.Realm
import me.jollypanda.ormcomparator.utils.*
import rx.Observable

/**
 * Tester for Realm DB
 *
 * @author Yamushev Igor
 * @since  24.09.16
 */
class RealmTester(val context: Context) : ORMTester {

     lateinit var result: ORMResult

    override fun getTestResult(): ORMResult {
        return result
    }

    fun getObservable(): Observable<ORMResult> {
        return ResultObservableFactory.getWriteResultObservable(context, this)
    }

    override fun testWrite() {
        val realm = Realm.getDefaultInstance()

        realm.executeTransaction { realm ->
            realm.delete(RealmStudentModel::class.java)
        }

        val studentsCollection = createStudentsCollection(RealmStudentModel::class.java, ITEM_COUNT)

        val startTime = System.currentTimeMillis()
        realm.executeTransaction { realm ->
            realm.copyToRealm(studentsCollection)
        }
        val endTime = System.currentTimeMillis()

        realm.close()

        result = ORMResult(REALM_NAME, 1, endTime - startTime, ITEM_COUNT)
    }

    override fun testRead() {
        val realm = Realm.getDefaultInstance()

        val startTime = System.currentTimeMillis()
        val collection = realm.where(RealmStudentModel::class.java).findAll()
        val endTime = System.currentTimeMillis()

        realm.close()

        result = ORMResult(REALM_NAME, 0, endTime - startTime, ITEM_COUNT)
    }

}