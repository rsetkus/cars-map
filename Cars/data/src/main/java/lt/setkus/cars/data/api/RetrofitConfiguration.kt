package lt.setkus.cars.data.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfiguration {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://cdn.sixt.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    fun getRentalCarsService() = retrofit.create(RentalCarsService::class.java)
}