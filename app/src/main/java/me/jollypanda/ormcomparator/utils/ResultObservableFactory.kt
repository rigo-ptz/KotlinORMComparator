package me.jollypanda.ormcomparator.utils

import me.jollypanda.ormcomparator.interfaces.ORMTester
import rx.Observable

/**
 * Object with factory methods for getting observables.
 *
 * @author Yamushev Igor
 * @since  23.09.16
 */
object ResultObservableFactory {

    fun getWriteResultObservable(ormTester: ORMTester): Observable<ORMResult> =
            Observable.create { subscriber ->
                if (subscriber.isUnsubscribed)
                    return@create

                try {
                    ormTester.testWrite()
                    val res = ormTester.getTestResult()
                    subscriber.onNext(res)
                    subscriber.onCompleted()
                } catch (e: Exception) {
                    subscriber.onError(e)
                }
            }

    fun getReadResultObservable(ormTester: ORMTester): Observable<ORMResult> =
            Observable.create { subscriber ->
                if (subscriber.isUnsubscribed)
                    return@create

                try {
                    ormTester.testRead()
                    val res = ormTester.getTestResult()
                    subscriber.onNext(res)
                    subscriber.onCompleted()
                } catch (e: Exception) {
                    subscriber.onError(e)
                }
            }
}