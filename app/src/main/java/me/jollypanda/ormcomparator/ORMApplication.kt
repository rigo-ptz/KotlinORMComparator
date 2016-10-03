package me.jollypanda.ormcomparator

import android.app.Application
import com.activeandroid.ActiveAndroid
import com.orm.SugarContext
import io.realm.Realm
import io.realm.RealmConfiguration
import me.jollypanda.ormcomparator.green_dao.DaoMaster
import me.jollypanda.ormcomparator.green_dao.DaoSession

/**
 * Application class
 *
 * @author Yamushev Igor
 * @since  26.09.16
 */
class ORMApplication : Application() {

    lateinit var daoSession: DaoSession

    override fun onCreate() {
        super.onCreate()
        Realm.setDefaultConfiguration(RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build())

        val helper = DaoMaster.DevOpenHelper(this, "db_greenDAO")
        daoSession = DaoMaster(helper.writableDb).newSession()

        SugarContext.init(this)

        ActiveAndroid.initialize(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        SugarContext.terminate()
    }

}