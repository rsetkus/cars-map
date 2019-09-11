package lt.setkus.cars.app.common.di

import lt.setkus.cars.app.common.UIThread
import org.koin.dsl.module

var applicationModule = module {
    single { UIThread() }
}