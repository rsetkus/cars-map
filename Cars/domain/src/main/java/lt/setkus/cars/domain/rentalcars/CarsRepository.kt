package lt.setkus.cars.domain.rentalcars

import io.reactivex.Single

interface CarsRepository {
    fun getCars(): Single<List<Car>>
}