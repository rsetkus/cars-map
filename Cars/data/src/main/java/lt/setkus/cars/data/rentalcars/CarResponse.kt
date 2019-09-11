package lt.setkus.cars.data.rentalcars

import lt.setkus.cars.domain.rentalcars.Car

data class CarResponse(
    val id: String,
    val modelIdentifier: String,
    val modelName: String,
    val name: String,
    val make: String,
    val group: String,
    val color: String,
    val series: String,
    val fuelType: String,
    val fuelLevel: Double,
    val transmission: String,
    val licensePlate: String,
    val latitude: Double,
    val longitude: Double,
    val innerCleanliness: String,
    val carImageUrl: String
)

val responseToDomainMapper: (response: List<CarResponse>) -> List<Car> = { r ->
    r.map {
        Car(
            it.id,
            it.modelIdentifier,
            it.modelName,
            it.name,
            it.make,
            it.group,
            it.color,
            it.series,
            it.fuelType,
            it.fuelLevel,
            it.transmission,
            it.licensePlate,
            it.latitude,
            it.longitude,
            it.innerCleanliness,
            it.carImageUrl
        )
    }
}