package lt.setkus.cars.app

import fr.xgouchet.elmyr.Forger
import lt.setkus.cars.domain.rentalcars.Car

fun Forger.asCar(): Car {
    return Car(
        aNumericalString(size = 7),
        anElementFrom(listOf("BMW7", "MercedesE320", "Renault1", "Lada5th", "MitsubishiX", "Volvo90")),
        anElementFrom(listOf("7", "E270", "Scenia", "XC90")),
        anElementFrom(listOf("Jonas", "Ricardas", "Elena", "Robertas")),
        anElementFrom(listOf("BMW", "Mercedes", "Renault", "Lada", "Mitsubishi", "Volvo")),
        anElementFrom(listOf("SEDAN", "COUPE", "ROADSTER", "MINI")),
        anElementFrom(listOf("yellow", "green", "red")),
        anElementFrom(listOf("SEDAN", "COUPE", "ROADSTER", "MINI")),
        anElementFrom("D", "G", "P"),
        aDouble(min = 0.0, max = 1.0),
        anElementFrom("A", "M"),
        aStringMatching("\\D{3} \\d{3}"),
        aDouble(min = -18.000000, max = 66.000000),
        aDouble(min = -18.000000, max = 66.000000),
        anElementFrom("Clean", "Almost Clean", "Dirty"),
        aWebUrl()
    )
}