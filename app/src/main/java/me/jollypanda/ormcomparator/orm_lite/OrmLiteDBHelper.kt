package me.jollypanda.ormcomparator.orm_lite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils

/**
 * Description here!!!
 *
 * @author Yamushev Igor
 * @since  26.09.16
 */
open class OrmLiteDBHelper(context: Context?) :
        OrmLiteSqliteOpenHelper(context, "dbOrmLite", null, 1) {

    open var studentDAO: Dao<OrmLiteStudentModel, Int> = getDao(OrmLiteStudentModel::class.java)

    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        try {
            TableUtils.createTable(connectionSource, OrmLiteStudentModel::class.java)
        } catch (e: SQLiteException) {

        }
    }

    override fun onUpgrade(database: SQLiteDatabase?, connectionSource: ConnectionSource?, oldVersion: Int, newVersion: Int) {
        try {
            TableUtils.dropTable<OrmLiteStudentModel, Int>(connectionSource, OrmLiteStudentModel::class.java, true)
        } catch (e: SQLiteException) {

        }
    }


}