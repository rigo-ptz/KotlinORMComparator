package me.jollypanda.ormcomparator.cupboard

import android.content.Context
import me.jollypanda.ormcomparator.interfaces.ORMTester
import me.jollypanda.ormcomparator.utils.*
import nl.qbusict.cupboard.CupboardFactory

/**
 * Tester for Cupboard.
 *
 * @author Yamushev Igor
 * @since  04.10.16
 */
class CupboardTester(val context: Context) : ORMTester {

    lateinit var result: ORMResult

    override fun getTestResult() = result

    override fun getWriteObservable() = ResultObservableFactory.getWriteResultObservable(this)

    override fun getReadObservable() = ResultObservableFactory.getReadResultObservable(this)

    override fun testWrite() {
        val dbHelper = CupboardDbHelper(context)
        val db = dbHelper.writableDatabase
        val dbc = CupboardFactory.cupboard().withDatabase(db)
        dbc.delete(CupboardStudentModel::class.java, null)

        val studentsCollection = createStudentsCollection(CupboardStudentModel::class.java, ITEM_COUNT)

        val startTime = System.currentTimeMillis()
        dbc.put(studentsCollection)
        val endTime = System.currentTimeMillis()
        dbHelper.close()
        result = ORMResult(СUPBOARD_NAME, 1, endTime - startTime, ITEM_COUNT)
    }

    override fun testRead() {
        val dbHelper = CupboardDbHelper(context)
        val db = dbHelper.writableDatabase
        val dbc = CupboardFactory.cupboard().withDatabase(db)
        val startTime = System.currentTimeMillis()
        val collection = dbc.query(CupboardStudentModel::class.java).list()
        val endTime = System.currentTimeMillis()
        db.close()
        result = ORMResult(СUPBOARD_NAME, 0, endTime - startTime, collection.size)
    }

}