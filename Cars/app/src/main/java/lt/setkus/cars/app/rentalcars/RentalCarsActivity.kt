package lt.setkus.cars.app.rentalcars

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.bottom_sheet.carsList
import lt.setkus.cars.R
import lt.setkus.cars.app.common.Error
import lt.setkus.cars.app.common.Finished
import lt.setkus.cars.app.common.Loading
import lt.setkus.cars.app.common.ViewState
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

        val viewStateObserver = Observer<ViewState> { viewState ->
            when (viewState) {
                is Loading -> Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                is Finished<*> -> rentalCarsAdapter.submitRentalCars(viewState.data as List<CarViewData>)
                is Error<*> -> Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.viewState.observe(this, viewStateObserver)
        viewModel.pullRentalCars()
    }
}