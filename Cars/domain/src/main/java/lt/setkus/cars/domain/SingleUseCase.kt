package lt.setkus.cars.domain

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

abstract class SingleUseCase<T>(
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) {

    fun build() = buildSingle()
            .subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutionThread.getScheduler())

    abstract fun buildSingle(): Single<T>
}