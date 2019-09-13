package lt.setkus.cars.app.rentalcars

import lt.setkus.cars.data.rentalcars.CarsDataRepository
import lt.setkus.cars.data.rentalcars.responseToDomainMapper
import lt.setkus.cars.domain.rentalcars.CarsRepository
import lt.setkus.cars.domain.rentalcars.RentalCarsUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val rentalCarsModule = module {
    scope(named<RentalCarsActivity>()) {
        scoped(named("responseMapper")) { responseToDomainMapper }
        scoped(named("viewMapper")) { ViewDataFromDomainMapper() }
        scoped<CarsRepository> { CarsDataRepository(get(), get(named("responseMapper"))) }
        scoped { RentalCarsUseCase(get(), get()) }
        viewModel { RentalCarsViewModel(get(), get(named("viewMapper"))) }
    }
}