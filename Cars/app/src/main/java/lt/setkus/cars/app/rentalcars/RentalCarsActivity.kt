package lt.setkus.cars.app.rentalcars

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import arrow.core.Either
import kotlinx.android.synthetic.main.bottom_sheet.carsList
import lt.setkus.cars.R
import org.koin.androidx.scope.currentScope

class RentalCarsActivity : AppCompatActivity() {

    val viewModel: RentalCarsViewModel by currentScope.inject()
    val rentalCarsAdapter = RentalCarsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_rental_cars)

        carsList.adapter = rentalCarsAdapter
        val layoutManager = LinearLayoutManager(this)
        carsList.layoutManager = layoutManager

        val viewStateObserver = Observer<Either<Throwable, List<CarViewData>>> { viewState ->
            viewState.fold(
                { Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show() },
                { rentalCarsAdapter.submitRentalCars(it) }
            )
        }

        viewModel.viewState.observe(this, viewStateObserver)
        viewModel.pullRentalCars()
    }
}