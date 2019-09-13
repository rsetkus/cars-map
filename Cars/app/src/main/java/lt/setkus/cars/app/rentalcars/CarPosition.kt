package lt.setkus.cars.app.rentalcars

import lt.setkus.cars.domain.rentalcars.Car

data class CarPosition(val latitude: Double, val longitude: Double)

val carPositionMapperFromDomain: (List<Car>) -> List<CarPosition> = { cars ->
    cars.map { CarPosition(it.latitude, it.longitude) }
}