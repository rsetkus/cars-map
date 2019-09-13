package lt.setkus.cars.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfiguration {

    private val httpLoggingInterceptor by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        interceptor
    }

    private val httpClient by lazy {
        val client = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
        client
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://cdn.sixt.io/")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    fun getRentalCarsService() = retrofit.create(RentalCarsService::class.java)
}