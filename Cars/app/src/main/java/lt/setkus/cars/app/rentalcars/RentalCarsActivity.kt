package lt.setkus.cars.app.rentalcars

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import lt.setkus.cars.R

class RentalCarsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_rental_cars)
        supportFragmentManager.beginTransaction()
            .add(R.id.mainContent, RentalCarsMapFragment.newInstance()).commit()
    }
}