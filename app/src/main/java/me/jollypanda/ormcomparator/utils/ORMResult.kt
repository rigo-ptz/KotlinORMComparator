package me.jollypanda.ormcomparator.utils

import io.realm.RealmObject
import io.realm.annotations.RealmClass

/**
 * Result of orm work
 *
 * @author Yamushev Igor
 * @since  23.09.16
 */
@RealmClass
open class ORMResult() : RealmObject() {

    var ormName: String = ""
    var readOrWrite: Int = 0
    var workTimeMills: Long = 0
    var recordCount: Int = 0

    constructor(ormName: String,
                readOrWrite: Int,
                workTimeMills: Long,
                recordCount: Int) : this() {
        this.ormName = ormName
        this.readOrWrite = readOrWrite
        this.workTimeMills = workTimeMills
        this.recordCount = recordCount
    }

    override fun toString(): String {
        var action: String = ""
        when (readOrWrite) {
            0 -> action = "R"
            1 -> action = "W"
        }
        return "$ormName:::$action $recordCount:::$workTimeMills msec"
    }
}