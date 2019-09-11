package lt.setkus.cars.app

import android.app.Application
import lt.setkus.cars.app.common.di.applicationModule
import lt.setkus.cars.app.common.di.httpModule
import lt.setkus.cars.app.rentalcars.rentalCarsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(
                applicationModule,
                httpModule,
                rentalCarsModule
            ))
        }
    }
}