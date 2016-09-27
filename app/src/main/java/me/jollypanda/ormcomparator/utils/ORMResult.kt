package me.jollypanda.ormcomparator.utils

/**
 * Result of orm work
 *
 * @author Yamushev Igor
 * @since  23.09.16
 */
class ORMResult(val ormName: String,
                val readOrWrite: Int,
                val workTimeMills: Long,
                val recordCount: Int) {

    override fun toString(): String {
        var action: String = ""
        when (readOrWrite) {
            0 -> action = "R"
            1 -> action = "W"
        }
        return "$ormName:::$action $recordCount:::$workTimeMills msec"
    }
}