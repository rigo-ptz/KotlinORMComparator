package me.jollypanda.ormcomparator.utils

/**
 * Abstract class for all tester classes.
 *
 * @author Yamushev Igor
 * @since  23.09.16
 */
interface ORMTester {
    fun getTestResult(): ORMResult
    fun testWrite()
    fun testRead()
}
