package lt.setkus.cars.data.rentalcars

import lt.setkus.cars.domain.rentalcars.Car
import org.junit.Assert.assertEquals
import org.junit.Test

class CarResponseMapperTest {
    val carsResponse = listOf(
        CarResponse(
            "id",
            "BMW7",
            "7 Series",
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

    @Test
    fun `when mapping invoked then should correctly map to domain model`() {
        val cars = responseToDomainMapper(carsResponse)

        cars.zip(carsResponse).forEach {
            verifyCarDomainModel(it.first, it.second)
        }
    }

    private fun verifyCarDomainModel(domain: Car, response: CarResponse) {
        assertEquals(response.id, domain.id)
        assertEquals(response.modelIdentifier, domain.modelIdentifier)
        assertEquals(response.name, domain.ownerName)
        assertEquals(response.make, domain.manufacturer)
        assertEquals(response.modelName, domain.modelName)
        assertEquals(response.group, domain.group)
        assertEquals(response.color, domain.color)
        assertEquals(response.series, domain.series)
        assertEquals(response.fuelLevel, domain.fuelLevel, 0.0)
        assertEquals(response.fuelType, domain.fuelType)
        assertEquals(response.transmission, domain.transmission)
        assertEquals(response.licensePlate, domain.licensePlate)
        assertEquals(response.latitude, domain.latitude, 0.0)
        assertEquals(response.longitude, domain.longitude, 0.0)
        assertEquals(response.innerCleanliness, domain.innerCleanliness)
        assertEquals(response.carImageUrl, domain.carImageUrl)
    }
}