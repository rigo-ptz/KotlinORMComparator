package me.jollypanda.ormcomparator.activeandroid

import android.content.Context
import com.activeandroid.ActiveAndroid
import com.activeandroid.query.Delete
import com.activeandroid.query.Select
import me.jollypanda.ormcomparator.interfaces.ORMTester
import me.jollypanda.ormcomparator.utils.*

/**
 * Tester for ActiveAndroid
 *
 * @author Yamushev Igor
 * @since  03.10.16
 */
class ActiveAndroidTester(context: Context) : ORMTester {

    lateinit var result: ORMResult

    override fun getTestResult() = result

    override fun getWriteObservable() = ResultObservableFactory.getWriteResultObservable(this)

    override fun getReadObservable() = ResultObservableFactory.getReadResultObservable(this)

    override fun testWrite() {
        val studentsCollection = createStudentsCollection(ActiveAndroidStudentModel::class.java, ITEM_COUNT)
        Delete().from(ActiveAndroidStudentModel::class.java).execute<ActiveAndroidStudentModel>()
        val starTime = System.currentTimeMillis()
        ActiveAndroid.beginTransaction()
        try {
            for (student in studentsCollection)
                student.save()
            ActiveAndroid.setTransactionSuccessful()
        } finally {
            ActiveAndroid.endTransaction()
        }
        val endTime = System.currentTimeMillis()
        result = ORMResult(ACTIVE_ANDROID_NAME, 1, endTime - starTime, ITEM_COUNT)
    }

    override fun testRead() {
        val starTime = System.currentTimeMillis()
        val collection = Select().all().from(ActiveAndroidStudentModel::class.java).execute<ActiveAndroidStudentModel>()
        val endTime = System.currentTimeMillis()
        result = ORMResult(ACTIVE_ANDROID_NAME, 0, endTime - starTime, collection.size)
    }
}