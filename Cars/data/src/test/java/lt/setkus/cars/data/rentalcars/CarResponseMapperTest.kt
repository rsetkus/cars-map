package lt.setkus.cars.data.rentalcars

import fr.xgouchet.elmyr.junit.JUnitForger
import lt.setkus.cars.domain.rentalcars.Car
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class CarResponseMapperTest {

    @get:Rule
    val forger = JUnitForger()

    val carsResponse = listOf(
        CarResponse(
            forger.aNumericalString(size = 7),
            forger.anElementFrom(listOf("BMW7", "MercedesE320", "Renault1", "Lada5th", "MitsubishiX", "Volvo90")),
            forger.anElementFrom(listOf("7", "E270", "Scenia", "XC90")),
            forger.anElementFrom(listOf("Jonas", "Ricardas", "Elena", "Robertas")),
            forger.anElementFrom(listOf("BMW", "Mercedes", "Renault", "Lada", "Mitsubishi", "Volvo")),
            forger.anElementFrom(listOf("SEDAN", "COUPE", "ROADSTER", "MINI")),
            forger.anElementFrom(listOf("yellow", "green", "red")),
            forger.anElementFrom(listOf("SEDAN", "COUPE", "ROADSTER", "MINI")),
            forger.anElementFrom("D", "G", "P"),
            forger.aDouble(min = 0.0, max = 1.0),
            forger.anElementFrom("A", "M"),
            forger.aStringMatching("\\D{3} \\d{3}"),
            forger.aDouble(min = -18.000000, max = 66.000000),
            forger.aDouble(min = -18.000000, max = 66.000000),
            forger.anElementFrom("Clean", "Almost Clean", "Dirty"),
            forger.aWebUrl()
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