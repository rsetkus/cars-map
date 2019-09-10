package lt.setkus.domain.rentalcars

import io.reactivex.Scheduler

interface PostExecutionThread {
    fun getScheduler(): Scheduler
}