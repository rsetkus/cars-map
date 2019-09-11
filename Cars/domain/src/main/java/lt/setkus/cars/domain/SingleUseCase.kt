package lt.setkus.cars.domain

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

abstract class SingleUseCase<T>(
    private val postExecutionThread: PostExecutionThread
) {

    fun build() = buildSingle()
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.getScheduler())

    abstract fun buildSingle(): Single<T>
}