package lt.setkus.cars.app.common

import io.reactivex.android.schedulers.AndroidSchedulers
import lt.setkus.cars.domain.PostExecutionThread

class UIThread : PostExecutionThread {
    override fun getScheduler() = AndroidSchedulers.mainThread()
}