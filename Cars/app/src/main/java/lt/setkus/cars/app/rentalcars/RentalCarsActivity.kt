package lt.setkus.cars.app.rentalcars

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import arrow.core.Either
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.bottom_sheet.carsList
import lt.setkus.cars.R
import lt.setkus.cars.app.common.drawMarkers
import lt.setkus.cars.app.common.executeIfGooglePlayServicesAvailable
import lt.setkus.cars.app.common.moveCamera
import org.koin.androidx.scope.currentScope

class RentalCarsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val viewModel: RentalCarsViewModel by currentScope.inject()
    private val rentalCarsAdapter = RentalCarsAdapter()
    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_rental_cars)
        setupCarsList()
        pullCarsData()
    }

    override fun onMapReady(map: GoogleMap?) {
        googleMap = map
        viewModel.carPositionState.observe(this, createPositionDataObserver())
    }

    private fun pullCarsData() {
        executeIfGooglePlayServicesAvailable {
            initMap()
        }
        viewModel.carDataState.observe(this, createCarDataObserver())
        viewModel.pullRentalCars()
    }

    private fun initMap() {
        val supportMapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        supportMapFragment?.apply {
            getMapAsync(this@RentalCarsActivity)
        }
    }

    private fun createPositionDataObserver(): Observer<Either<Throwable, List<CarPosition>>> {
        return Observer { viewState ->
            viewState.fold(
                { Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show() },
                { updateMap(it) }
            )
        }
    }

    private fun updateMap(positions: List<CarPosition>) {
        googleMap?.apply {
            drawMarkers(positions)
            moveCamera(positions, 300)
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
            val dividerItemDecoration =
                DividerItemDecoration(this@RentalCarsActivity, DividerItemDecoration.VERTICAL)
            dividerItemDecoration.setDrawable(this)
            carsList.addItemDecoration(dividerItemDecoration)
        }
    }
}