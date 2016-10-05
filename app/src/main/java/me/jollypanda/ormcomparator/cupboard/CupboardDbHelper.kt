package me.jollypanda.ormcomparator.cupboard

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import nl.qbusict.cupboard.CupboardFactory

/**
 * Utils for Cupboard.
 *
 * @author Yamushev Igor
 * @since  04.10.16
 */
const val DB_NAME: String = "db_cupboard"
const val DB_VERSION: Int = 1

class CupboardDbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        init {
            CupboardFactory.cupboard().register(CupboardStudentModel::class.java)
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        CupboardFactory.cupboard().withDatabase(db).createTables()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        CupboardFactory.cupboard().withDatabase(db).upgradeTables()
    }
}