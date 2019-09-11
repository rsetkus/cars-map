package lt.setkus.cars.domain.rentalcars

data class Car(
    val id: String,
    val modelIdentifier: String,
    val modelName: String,
    val ownerName: String,
    val manufacturer: String,
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