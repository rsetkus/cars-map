package lt.setkus.cars.app.rentalcars

import fr.xgouchet.elmyr.junit.JUnitForger
import lt.setkus.cars.domain.rentalcars.Car
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class CarPositionKtTest {

    @get:Rule
    val forger = JUnitForger()

    val cars = listOf(
        Car(
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

    val mapper = carPositionMapperFromDomain

    @Test
    fun `test successful mapping`() {
        val positions = mapper(cars)

        val allMatch = positions.zip(cars).all {
            verifyPosition(it.first, it.second)
        }

        assertTrue(allMatch)
    }

    private fun verifyPosition(position: CarPosition, domain: Car) =
        position.latitude == domain.latitude && position.longitude == domain.longitude
}