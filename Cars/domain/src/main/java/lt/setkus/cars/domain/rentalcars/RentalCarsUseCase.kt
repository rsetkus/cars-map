package lt.setkus.cars.domain.rentalcars

import lt.setkus.cars.domain.PostExecutionThread
import lt.setkus.cars.domain.SingleUseCase
import lt.setkus.cars.domain.ThreadExecutor

class RentalCarsUseCase(
    private val repository: CarsRepository,
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Car>>(threadExecutor, postExecutionThread) {

    override fun buildSingle() = repository.getCars()
}