package me.jollypanda.ormcomparator.interfaces

import me.jollypanda.ormcomparator.utils.ORMResult
import rx.Observable

/**
 * Interface for all tester classes.
 *
 * @author Yamushev Igor
 * @since  23.09.16
 */
interface ORMTester {
    fun getTestResult(): ORMResult
    fun testWrite()
    fun testRead()
    fun getWriteObservable(): Observable<ORMResult>
    fun getReadObservable(): Observable<ORMResult>
}
