package lt.setkus.cars.app.rentalcars

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import lt.setkus.cars.domain.rentalcars.Car

data class CarPosition(val id: String, val latitude: Double, val longitude: Double)

val carPositionMapperFromDomain: (List<Car>) -> List<CarPosition> = { cars ->
    cars.map { CarPosition(it.id, it.latitude, it.longitude) }
}

/**
 * Finally, those sleepless nights learning FP are paying off!
 *
 * Basically, this is {@code foldRight} implementation.
 * If you'd replace function body with code bellow, you'll get the same result
 *
 * <pre><code>
 *     foldRight(LatLngBounds.builder(), { c, b -> b.include(LatLng(c.latitude, c.longitude)) }).build()
 * </code></pre>
 */
fun List<CarPosition>.toLatLngBounds(): LatLngBounds {
    // Need an inner function because have to accumulate result into Builder and then build
    tailrec fun _toLatLngBounds(builder: LatLngBounds.Builder, list: List<CarPosition>): LatLngBounds.Builder {
        return if (list.isEmpty()) builder else _toLatLngBounds(builder.include(LatLng(list.first().latitude, list.first().longitude)), list.drop(1))
    }
    return _toLatLngBounds(LatLngBounds.builder(), this).build()
}
