package lt.setkus.cars.data.api

import io.reactivex.Single
import lt.setkus.cars.data.rentalcars.CarResponse
import retrofit2.http.GET

interface RentalCarsService {

    @GET("codingtask/cars")
    fun getRentalCars(): Single<List<CarResponse>>
}