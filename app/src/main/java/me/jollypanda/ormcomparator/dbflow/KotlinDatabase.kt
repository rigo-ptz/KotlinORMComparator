package me.jollypanda.ormcomparator.dbflow

import com.raizlabs.android.dbflow.annotation.Database

/**
 * Database for DBFlow
 *
 * @author Yamushev Igor
 * @since  03.10.16
 */
@Database(name = KotlinDatabase.NAME, version = KotlinDatabase.VERSION)
object KotlinDatabase {
    const val NAME: String = "db_dbflow"
    const val VERSION: Int = 1
}