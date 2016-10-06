package me.jollypanda.ormcomparator.green_dao

import android.content.Context
import com.j256.ormlite.android.apptools.OpenHelperManager
import me.jollypanda.ormcomparator.ORMApplication
import me.jollypanda.ormcomparator.interfaces.ORMTester
import me.jollypanda.ormcomparator.utils.*

/**
 * Tester for GreenDAO.
 *
 * @author Yamushev Igor
 * @since  01.10.16
 */
class GreenDaoTester(val context: Context) : ORMTester {

    lateinit var result: ORMResult

    override fun getTestResult() = result

    override fun getWriteObservable() = ResultObservableFactory.getWriteResultObservable(this)

    override fun getReadObservable() = ResultObservableFactory.getReadResultObservable(this)

    override fun testWrite() {
        val daoSession = (context as ORMApplication?)?.daoSession

        if (daoSession != null) {
            var studentsCollection = createStudentsCollection(GreenDaoStudentModel::class.java, ITEM_COUNT)

            val startTime = System.currentTimeMillis()

            daoSession.greenDaoStudentModelDao.deleteAll()

            try {
                daoSession.greenDaoStudentModelDao.insertInTx(studentsCollection)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val endTime = System.currentTimeMillis()

            OpenHelperManager.releaseHelper()

            result = ORMResult(GREENDAO_NAME, 1, endTime - startTime, ITEM_COUNT)
        }
    }

    override fun testRead() {
        val daoSession = (context as ORMApplication?)?.daoSession
        if (daoSession != null) {
            val startTime = System.currentTimeMillis()
            val collection = daoSession.greenDaoStudentModelDao.loadAll()
            val endTime = System.currentTimeMillis()
            result = ORMResult(GREENDAO_NAME, 0, endTime - startTime, collection.size)
        }
    }
}