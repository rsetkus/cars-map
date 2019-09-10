package lt.setkus.domain.rentalcars

import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import lt.setkus.domain.ThreadExecutor
import org.junit.Test

class RentalCarsUseCaseTest {
    val threadExecutor = mockk<ThreadExecutor>()
    val postExecutionThread = mockk<PostExecutionThread>()
    val repository = mockk<CarsRepository>()

    val cars = listOf(
        Car(
            "id",
            "LTU1",
            "Jonas",
            "BMW",
            "SEDAN",
            "black",
            "SEDAN",
            "D",
            0.7,
            "A",
            "GMU 076",
            48.1236479,
            11.576921,
            "Clean",
            "https://image.url"
        )
    )
    var error = Throwable("Error")
    val rentalCarsUseCase = RentalCarsUseCase(repository, threadExecutor, postExecutionThread)

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