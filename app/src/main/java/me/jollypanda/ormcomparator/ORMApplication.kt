package me.jollypanda.ormcomparator

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Application class
 *
 * @author Yamushev Igor
 * @since  26.09.16
 */
class ORMApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.setDefaultConfiguration(RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build())
    }
}