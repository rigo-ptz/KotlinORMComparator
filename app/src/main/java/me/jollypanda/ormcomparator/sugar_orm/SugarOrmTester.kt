package me.jollypanda.ormcomparator.sugar_orm

import android.content.Context
import com.orm.SugarRecord
import me.jollypanda.ormcomparator.interfaces.ORMTester
import me.jollypanda.ormcomparator.utils.*
import rx.Observable

/**
 * Tester for SugarOrm
 *
 * @author Yamushev Igor
 * @since  03.10.16
 */
class SugarOrmTester(context: Context) : ORMTester {

    lateinit var result: ORMResult

    override fun getTestResult() = result

    override fun getWriteObservable() = ResultObservableFactory.getWriteResultObservable(this)

    override fun getReadObservable(): Observable<ORMResult> {
        throw UnsupportedOperationException("not implemented")
    }

    override fun testWrite() {
        val studentsCollection = createStudentsCollection(SugarOrmStudentModel::class.java, ITEM_COUNT)
        SugarRecord.deleteAll(SugarOrmStudentModel::class.java)
        val startTime = System.currentTimeMillis()
        SugarRecord.saveInTx(studentsCollection)
        val endTime = System.currentTimeMillis()
        result = ORMResult(SUGAR_ORM_NAME, 1, endTime - startTime, ITEM_COUNT)
    }

    override fun testRead() {
        val startTime = System.currentTimeMillis()
        val collection = SugarRecord.listAll(SugarOrmStudentModel::class.java)
        val endTime = System.currentTimeMillis()
        result = ORMResult(SUGAR_ORM_NAME, 0, endTime - startTime, ITEM_COUNT)
    }
}