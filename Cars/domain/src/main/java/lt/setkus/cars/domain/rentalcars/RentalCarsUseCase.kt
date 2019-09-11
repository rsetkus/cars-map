package lt.setkus.cars.domain.rentalcars

import lt.setkus.cars.domain.PostExecutionThread
import lt.setkus.cars.domain.SingleUseCase

class RentalCarsUseCase(
    private val repository: CarsRepository,
    private val postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Car>>(postExecutionThread) {

    override fun buildSingle() = repository.getCars()
}