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
import com.google.android.gms.maps.model.Marker
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet.bottomSheet
import kotlinx.android.synthetic.main.bottom_sheet.carsList
import kotlinx.android.synthetic.main.bottom_sheet.carsStateLayout
import lt.setkus.cars.R
import lt.setkus.cars.app.common.animateCameraToNewPosition
import lt.setkus.cars.app.common.drawMarkers
import lt.setkus.cars.app.common.executeIfGooglePlayServicesAvailable
import lt.setkus.cars.app.common.initMapCamera
import lt.setkus.cars.app.common.moveCamera
import org.koin.androidx.scope.currentScope
import kotlin.properties.Delegates

class RentalCarsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val viewModel: RentalCarsViewModel by currentScope.inject()
    private val rentalCarsAdapter = RentalCarsAdapter() {
        zoomToCar(it)
    }

    private val bottomSheetBehavior by lazy {
        BottomSheetBehavior.from(bottomSheet)
    }

    private var googleMap: GoogleMap by Delegates.notNull()
    private val markersMap = mutableMapOf<String, Marker>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rental_cars)
        setupCarsList()
        pullCarsData()
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.apply {
            googleMap = this
            initMapCamera()
        }
        viewModel.getCarPositionState().observe(this, createPositionDataObserver())
    }

    private fun pullCarsData() {
        executeIfGooglePlayServicesAvailable {
            initMap()
        }
        viewModel.getCarDataState().observe(this, createCarDataObserver())
        carsStateLayout.loading()
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
                {
                    Toast.makeText(
                        this,
                        getString(R.string.error_loading_cars_locations),
                        Toast.LENGTH_SHORT
                    ).show()
                },
                { updateMap(it) }
            )
        }
    }

    private fun updateMap(positions: List<CarPosition>) {
        with(googleMap) {
            markersMap.putAll(drawMarkers(positions))
            moveCamera(positions, 300)
        }
    }

    private fun createCarDataObserver(): Observer<Either<Throwable, List<CarViewData>>> {
        return Observer { viewState ->
            viewState.fold(
                { carsStateLayout.error() },
                {
                    rentalCarsAdapter.submitRentalCars(it)
                    carsStateLayout.content()
                }
            )
        }
    }

    private fun setupCarsList() {
        carsList.adapter = rentalCarsAdapter
        carsList.layoutManager = LinearLayoutManager(this)
        setCarListDividerIfPresent()
    }

    private fun setCarListDividerIfPresent() {
        getDrawable(R.drawable.divider)?.apply {
            val dividerItemDecoration =
                DividerItemDecoration(this@RentalCarsActivity, DividerItemDecoration.VERTICAL)
            dividerItemDecoration.setDrawable(this)
            carsList.addItemDecoration(dividerItemDecoration)
        }
    }

    private fun zoomToCar(carData: CarViewData) {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        markersMap.get(carData.id)?.apply {
            googleMap.animateCameraToNewPosition(position)
        }
    }
}