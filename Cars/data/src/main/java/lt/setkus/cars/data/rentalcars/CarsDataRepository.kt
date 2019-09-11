package lt.setkus.cars.data.rentalcars

import io.reactivex.Single
import lt.setkus.cars.data.api.RentalCarsService
import lt.setkus.cars.domain.rentalcars.Car
import lt.setkus.cars.domain.rentalcars.CarsRepository

class CarsDataRepository(
    private val service: RentalCarsService,
    private val mapper: (List<CarResponse>) -> List<Car>
) : CarsRepository {

    override fun getCars(): Single<List<Car>> = service.getRentalCars().map(mapper)
}