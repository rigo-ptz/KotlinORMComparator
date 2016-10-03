package me.jollypanda.ormcomparator.dbflow

import android.content.Context
import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.list
import com.raizlabs.android.dbflow.kotlinextensions.select
import com.raizlabs.android.dbflow.sql.language.Delete
import me.jollypanda.ormcomparator.interfaces.ORMTester
import me.jollypanda.ormcomparator.utils.*
import rx.Observable

/**
 * Tester for DBFlow.
 *
 * @author Yamushev Igor
 * @since  03.10.16
 */
class DBFlowTester(context: Context) : ORMTester {

    lateinit var result: ORMResult

    override fun getTestResult() = result

    override fun getWriteObservable() = ResultObservableFactory.getWriteResultObservable(this)

    override fun getReadObservable(): Observable<ORMResult> {
        throw UnsupportedOperationException("not implemented")
    }

    override fun testWrite() {
        Delete()
                .from(DBFlowStudentModel::class.java)
                .execute()

        val studentsCollection = createStudentsCollection(DBFlowStudentModel::class.java, ITEM_COUNT)
        val startTime = System.currentTimeMillis()
        /*FastStoreModelTransaction
                .insertBuilder(FlowManager.getModelAdapter(DBFlowStudentModel::class.java))
                .addAll(studentsCollection)
                .build()*/

        FlowManager.getDatabase(KotlinDatabase::class.java).executeTransaction({ databaseWrapper ->
            for (student in studentsCollection) {
                student.save()
            }
        })
        val endTime = System.currentTimeMillis()
        result = ORMResult(DB_FLOW_NAME, 1, endTime - startTime, ITEM_COUNT)
    }

    override fun testRead() {
        val startTime = System.currentTimeMillis()
        val colletcion = (select
                            from DBFlowStudentModel::class).list
        val endTime = System.currentTimeMillis()
        result = ORMResult(DB_FLOW_NAME, 0, endTime - startTime, ITEM_COUNT)
    }

}