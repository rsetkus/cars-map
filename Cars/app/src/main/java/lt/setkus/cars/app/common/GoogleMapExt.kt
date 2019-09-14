package lt.setkus.cars.app.common

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import lt.setkus.cars.app.rentalcars.CarPosition
import lt.setkus.cars.app.rentalcars.toLatLngBounds

fun GoogleMap.moveCamera(positions: List<CarPosition>, padding: Int = 0) {
    moveCamera(CameraUpdateFactory.newLatLngBounds(positions.toLatLngBounds(), padding))
}

fun GoogleMap.drawMarkers(positions: List<CarPosition>) {
    positions.map { MarkerOptions().position(LatLng(it.latitude, it.longitude)) }.forEach { addMarker(it) }
}