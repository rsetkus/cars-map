package lt.setkus.cars.app.common.di

import lt.setkus.cars.data.api.RetrofitConfiguration
import org.koin.dsl.module

val httpModule = module {
    single { RetrofitConfiguration.getRentalCarsService() }
}