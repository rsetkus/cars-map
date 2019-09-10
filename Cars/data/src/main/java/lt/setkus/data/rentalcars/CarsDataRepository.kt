package lt.setkus.data.rentalcars

import io.reactivex.Single
import lt.setkus.data.api.RentalCarsService
import lt.setkus.domain.rentalcars.Car
import lt.setkus.domain.rentalcars.CarsRepository

class CarsDataRepository(
    private val service: RentalCarsService,
    private val mapper: (List<CarResponse>) -> List<Car>
) : CarsRepository {

    override fun getCars(): Single<List<Car>> = service.getRentalCars().map(mapper)
}