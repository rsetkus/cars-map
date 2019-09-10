package lt.setkus.domain

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import lt.setkus.domain.rentalcars.PostExecutionThread

abstract class SingleUseCase<T>(
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) {

    fun subscribe(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit) =
        buildSingle()
            .subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutionThread.getScheduler())
            .subscribe(
                onSuccess,
                onError
            )

    abstract fun buildSingle(): Single<T>
}