package me.jollypanda.ormcomparator.ollie

import android.content.Context
import me.jollypanda.ormcomparator.interfaces.ORMTester
import me.jollypanda.ormcomparator.utils.*
import ollie.Ollie
import ollie.query.Delete
import ollie.query.Select
import rx.Observable

/**
 * Tester for Ollie.
 * It seems that library is not work:
 * " java.lang.NullPointerException: Attempt to invoke virtual method
 * 'java.lang.Object ollie.TypeAdapter.serialize(java.lang.Object)'
 * on a null object reference
 *  10-04 01:47:48.020 1879-1938/me.jollypanda.ormcomparator W/System.err:
 *  at ollie.OllieStudentModel$$ModelAdapter.save(OllieStudentModel$$ModelAdapter.java:40) "
 *
 * Problem code:
 *  "public final Long save(OllieStudentModel entity, SQLiteDatabase db) {
 *      ContentValues values = new ContentValues();
        values.put("name", (java.lang.String) Ollie.getTypeAdapter(java.lang.String.class).serialize(entity.name));"
 *
 * @author Yamushev Igor
 * @since  03.10.16
 */
class OllieTester(context: Context) : ORMTester {

    lateinit var result: ORMResult

    override fun getTestResult() = result

    override fun getWriteObservable() = ResultObservableFactory.getWriteResultObservable(this)

    override fun getReadObservable(): Observable<ORMResult> {
        throw UnsupportedOperationException("not implemented")
    }

    override fun testWrite() {
        Delete.from(OllieStudentModel::class.java).execute()

        val collection = createStudentsCollection(OllieStudentModel::class.java, ITEM_COUNT)

        val startTime = System.currentTimeMillis()
        try {
            Ollie.getDatabase().beginTransaction()
            for (student in collection)
                student.save()
            Ollie.getDatabase().setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            Ollie.getDatabase().endTransaction()
        }
        val endTime = System.currentTimeMillis()

        result = ORMResult(OLLIE_NAME, 1, endTime - startTime, ITEM_COUNT)
    }

    override fun testRead() {
        val startTime = System.currentTimeMillis()
        val collection = Select.from(OllieStudentModel::class.java).fetch()
        val endTime = System.currentTimeMillis()
        result = ORMResult(OLLIE_NAME, 0, endTime - startTime, ITEM_COUNT)
    }

}