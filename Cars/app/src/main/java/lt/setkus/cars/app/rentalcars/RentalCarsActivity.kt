package lt.setkus.cars.app.rentalcars

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import lt.setkus.cars.R
import lt.setkus.cars.app.common.Error
import lt.setkus.cars.app.common.Finished
import lt.setkus.cars.app.common.Loading
import lt.setkus.cars.app.common.ViewState
import org.koin.androidx.scope.currentScope

class RentalCarsActivity : AppCompatActivity() {

    val viewModel: RentalCarsViewModel by currentScope.inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_rental_cars)

        val viewStateObserver = Observer<ViewState> { viewState ->
            val state = when (viewState) {
                is Loading -> "Loading"
                is Finished<*> -> "Finished"
                is Error<*> -> "Error"
            }

            Toast.makeText(this, state, Toast.LENGTH_SHORT).show()
        }

        viewModel.viewState.observe(this, viewStateObserver)
        viewModel.pullRentalCars()
    }
}