package lt.setkus.cars.app.common

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import lt.setkus.cars.app.rentalcars.CarPosition
import lt.setkus.cars.app.rentalcars.toLatLngBounds

fun GoogleMap.moveCamera(positions: List<CarPosition>, padding: Int = 0) {
    moveCamera(CameraUpdateFactory.newLatLngBounds(positions.toLatLngBounds(), padding))
}

fun GoogleMap.drawMarkers(positions: List<CarPosition>): Map<String, Marker> {
    return positions
        .foldRight(mapOf()) { position, map ->
            val marker = addMarker(MarkerOptions().position(LatLng(position.latitude, position.longitude)))
            map + mapOf(position.id to marker)
        }
}

fun GoogleMap.animateCameraToNewPosition(
    target: LatLng,
    zoom: Float = 15.0f,
    bearing: Float = 90f,
    tilt: Float = 30f
) {
    val newPosition = CameraPosition.Builder()
        .target(target)
        .zoom(zoom)
        .bearing(bearing)
        .tilt(tilt)
        .build()

    animateCamera(CameraUpdateFactory.newCameraPosition(newPosition))
}

fun GoogleMap.initMapCamera(
    latitude: Double = CENTER_OF_LITHUANIA_LATITUDE,
    longitude: Double = CENTER_OF_LITHUANIA_LONGITUDE,
    zoom: Float = 6f
) {
    moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), zoom))
}

private const val CENTER_OF_LITHUANIA_LATITUDE = 55.330041
private const val CENTER_OF_LITHUANIA_LONGITUDE = 23.905423