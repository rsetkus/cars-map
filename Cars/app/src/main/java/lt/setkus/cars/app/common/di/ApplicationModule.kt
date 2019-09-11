package lt.setkus.cars.app.common.di

import lt.setkus.cars.app.common.UIThread
import lt.setkus.cars.domain.PostExecutionThread
import org.koin.dsl.module

var applicationModule = module {
    single<PostExecutionThread> { UIThread() }
}