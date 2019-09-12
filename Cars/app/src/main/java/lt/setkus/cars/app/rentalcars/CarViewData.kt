package lt.setkus.cars.app.rentalcars

import lt.setkus.cars.domain.rentalcars.Car

data class CarViewData(val carModelName: CharSequence)

val viewDataFromDomainMapper: (List<Car>) -> List<CarViewData> = { cars ->
    cars.map { CarViewData(it.modelName) }
}