package me.jollypanda.ormcomparator.orm_lite

import android.content.Context
import com.j256.ormlite.android.apptools.OpenHelperManager
import me.jollypanda.ormcomparator.interfaces.ORMTester
import me.jollypanda.ormcomparator.utils.*
import rx.Observable
import java.sql.SQLException

/**
 * Tester for OrmLite.
 *
 * @author Yamushev Igor
 * @since  27.09.16
 */
class OrmLiteTester(val context: Context) : ORMTester {

    lateinit var result: ORMResult

    override fun getTestResult() = result

    fun getWriteObservable(): Observable<ORMResult> {
        return ResultObservableFactory.getWriteResultObservable(this)
    }

    override fun testWrite() {
        val dbHelper = OpenHelperManager.getHelper(context, OrmLiteDBHelper::class.java)

        try {
            dbHelper.studentDAO.deleteBuilder().delete()
        } catch (e: SQLException) {

        }

        val studentsCollection = createStudentsCollection(OrmLiteStudentModel::class.java, ITEM_COUNT)

        val startTime = System.currentTimeMillis()

        try {
            val dao = dbHelper.studentDAO
            var db = dbHelper.writableDatabase

            try {
                db.beginTransaction()
                for (student in studentsCollection)
                    dao.create(student)
                db.setTransactionSuccessful()
            } finally {
                db.endTransaction()
            }
        } catch (e: SQLException) {

        }

        val endTime = System.currentTimeMillis()

        OpenHelperManager.releaseHelper()

        result = ORMResult(ORMLITE_NAME, 1, endTime - startTime, ITEM_COUNT)
    }

    override fun testRead() {
        val dbHelper = OpenHelperManager.getHelper(context, OrmLiteDBHelper::class.java)

        var startTime: Long = 0
        var endTime: Long = 0

        try {
            val dao = dbHelper.studentDAO
            startTime = System.currentTimeMillis()
            val collection = dao.queryForAll()
            endTime = System.currentTimeMillis()
        } catch (e: SQLException) {

        }

        result = ORMResult(ORMLITE_NAME, 0, endTime - startTime, ITEM_COUNT)
    }
}