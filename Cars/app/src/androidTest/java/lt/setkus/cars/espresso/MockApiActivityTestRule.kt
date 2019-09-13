package lt.setkus.cars.espresso

import androidx.appcompat.app.AppCompatActivity
import androidx.test.rule.ActivityTestRule
import lt.setkus.cars.data.api.RentalCarsService
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MockApiActivityTestRule<T : AppCompatActivity>(activityClass: Class<T>?) :
    ActivityTestRule<T>(activityClass, false, false) {

    override fun beforeActivityLaunched() {
        super.beforeActivityLaunched()

        loadKoinModules(
            module {
                single(override = true) { provideMockRetrofit().create(RentalCarsService::class.java) }
            }
        )
    }

    private fun provideMockRetrofit() =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("http://localhost:8080")
            .build()
}
