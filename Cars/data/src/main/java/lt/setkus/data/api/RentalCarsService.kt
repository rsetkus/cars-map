package lt.setkus.data.api

import io.reactivex.Single
import lt.setkus.data.rentalcars.CarResponse
import retrofit2.http.GET

interface RentalCarsService {

    @GET("codingtask/cars")
    fun getRentalCars(): Single<List<CarResponse>>
}