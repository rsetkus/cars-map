package lt.setkus.data.rentalcars

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import lt.setkus.data.api.RentalCarsService
import lt.setkus.domain.rentalcars.Car
import org.junit.Test

class CarsDataRepositoryTest {

    val service = mockk<RentalCarsService>()
    val mapper = mockk<(List<CarResponse>) -> List<Car>>()
    val response = mockk<CarResponse>()

    val dataRepository = CarsDataRepository(service, mapper)

    @Test
    fun `when data requested from repository then should invoke service and mapper`() {
        every { service.getRentalCars() } returns Single.just(listOf(response))

        dataRepository.getCars().test()

        verify { service.getRentalCars() }
        verify { mapper(listOf(response)) }
    }
}