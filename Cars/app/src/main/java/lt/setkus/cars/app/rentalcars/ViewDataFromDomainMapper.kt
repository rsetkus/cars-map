package lt.setkus.cars.app.rentalcars

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import lt.setkus.cars.R
import lt.setkus.cars.domain.rentalcars.Car

class ViewDataFromDomainMapper : Function1<List<Car>, List<CarViewData>> {

    override fun invoke(input: List<Car>) =
        input.map {
            CarViewData(
                it.id,
                it.modelName,
                it.ownerName,
                getFuelIconLevel(it.fuelLevel),
                getFuelLabelByLevel(it.fuelLevel),
                it.carImageUrl,
                getFuelType(it.fuelType)
            )
        }

    @StringRes
    private fun getFuelLabelByLevel(level: Double) =
        when (level) {
            1.0 -> R.string.fuel_level_full
            else -> R.string.fuel_level_unknown
        }

    @DrawableRes
    private fun getFuelIconLevel(level: Double) =
        when (level) {
            1.0 -> R.drawable.ic_battery_full_black_24dp
            else -> R.drawable.ic_battery_unknown_black_24dp
        }

    @StringRes
    private fun getFuelType(type: String) =
        when (type) {
            "D" -> R.string.diesel_type
            "P" -> R.string.petrol_type
            else -> R.string.unknown_fuel_type
        }
}