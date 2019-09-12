package lt.setkus.cars.domain.rentalcars

import fr.xgouchet.elmyr.junit.JUnitForger
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import lt.setkus.cars.domain.PostExecutionThread
import lt.setkus.cars.domain.asCar
import org.junit.Rule
import org.junit.Test

class RentalCarsUseCaseTest {

    @get:Rule
    val forger = JUnitForger()

    val postExecutionThread = mockk<PostExecutionThread>(relaxed = true)
    val repository = mockk<CarsRepository>()

    val cars = listOf(forger.asCar(), forger.asCar(), forger.asCar())

    var error = Throwable("Error")
    val rentalCarsUseCase = RentalCarsUseCase(repository, postExecutionThread)

    @Test
    fun `when rental cars use cases is built then should emit single of list of cars`() {
        every { repository.getCars() } returns Single.just(cars)
        rentalCarsUseCase.buildSingle().test()
            .assertValue(cars)
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun `when rental cars use case is built and error occurs then should emit error`() {
        every { repository.getCars() } returns Single.error(error)
        rentalCarsUseCase.buildSingle().test()
            .assertError(error)
            .assertNoValues()
    }
}