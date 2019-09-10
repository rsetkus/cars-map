package lt.setkus.domain.rentalcars

import lt.setkus.domain.SingleUseCase
import lt.setkus.domain.ThreadExecutor

class RentalCarsUseCase(
    private val repository: CarsRepository,
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Car>>(threadExecutor, postExecutionThread) {

    override fun buildSingle() = repository.getCars()
}