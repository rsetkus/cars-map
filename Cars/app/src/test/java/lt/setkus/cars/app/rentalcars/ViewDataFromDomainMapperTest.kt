package lt.setkus.cars.app.rentalcars

import fr.xgouchet.elmyr.junit.JUnitForger
import lt.setkus.cars.R
import lt.setkus.cars.domain.rentalcars.Car
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ViewDataFromDomainMapperTest {

    @get:Rule
    val forger = JUnitForger()

    val mapper = ViewDataFromDomainMapper()

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

    @Test
    fun `test car mapping from response to view data`() {
        val carsViewData = mapper(cars)
        val matchAll = cars.zip(carsViewData).all {
            verifyViewDataAgainstDomain(it.second, it.first)
        }
        assertTrue(matchAll)
    }

    private fun verifyViewDataAgainstDomain(viewData: CarViewData, domain: Car) =
        viewData.carModelName == domain.modelName && viewData.ownerName == domain.ownerName &&
                viewData.fuelIconId == verifyFuelIcon(domain.fuelLevel) &&
                viewData.fuelTankLevel == verifyFuelLabel(domain.fuelLevel)

    /**
     * Not sure if it is good idea because tests reveals concrete implementation
     * but because test data is generated I don't see other way testing correct mapping
     */
    private fun verifyFuelIcon(level: Double) =
        when (level) {
            1.0 -> R.drawable.ic_battery_full_black_24dp
            else -> R.drawable.ic_battery_unknown_black_24dp
        }

    private fun verifyFuelLabel(level: Double) =
        when (level) {
            1.0 -> R.string.fuel_level_full
            else -> R.string.fuel_level_unknown
        }
}