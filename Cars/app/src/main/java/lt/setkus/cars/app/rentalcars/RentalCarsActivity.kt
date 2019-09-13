package lt.setkus.cars.app.rentalcars

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
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
        setupCarsList()
        pullCarsData()
    }

    private fun pullCarsData() {
        viewModel.carDataState.observe(this, createCarDataObserver())
        viewModel.carPositionState.observe(this, createPositionDataObserver())
        viewModel.pullRentalCars()
    }

    private fun createPositionDataObserver(): Observer<Either<Throwable, List<CarPosition>>> {
        return Observer { viewState ->
            viewState.fold(
                { Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show() },
                { Toast.makeText(this, "Got Positions", Toast.LENGTH_SHORT).show() }
            )
        }
    }

    private fun createCarDataObserver(): Observer<Either<Throwable, List<CarViewData>>> {
        return Observer { viewState ->
            viewState.fold(
                { Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show() },
                { rentalCarsAdapter.submitRentalCars(it) }
            )
        }
    }

    private fun setupCarsList() {
        carsList.adapter = rentalCarsAdapter
        val layoutManager = LinearLayoutManager(this)
        carsList.layoutManager = layoutManager

        getDrawable(R.drawable.divider)?.apply {
            val dividerItemDecoration = DividerItemDecoration(this@RentalCarsActivity, DividerItemDecoration.VERTICAL)
            dividerItemDecoration.setDrawable(this)
            carsList.addItemDecoration(dividerItemDecoration)
        }
    }
}