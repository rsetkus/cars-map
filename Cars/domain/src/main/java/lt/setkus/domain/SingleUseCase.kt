package lt.setkus.domain

import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.schedulers.Schedulers
import lt.setkus.domain.rentalcars.PostExecutionThread

abstract class SingleUseCase<T>(
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) {

    fun subscribe(observer: SingleObserver<T>) {
        buildSingle()
            .subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutionThread.getScheduler())
            .subscribe(observer)
    }

    abstract fun buildSingle(): Single<T>
}