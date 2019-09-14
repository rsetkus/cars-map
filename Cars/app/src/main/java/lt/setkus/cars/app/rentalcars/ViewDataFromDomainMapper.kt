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
            in (0.90..1.00) -> R.string.fuel_level_full
            in (0.55..0.90) -> R.string.fuel_level_high
            in (0.20..0.55) -> R.string.fuel_level_low
            else -> R.string.fuel_level_unknown
        }

    @DrawableRes
    private fun getFuelIconLevel(level: Double) =
        when (level) {
            in (0.90..1.00) -> R.drawable.ic_battery_full_black_24dp
            in (0.55..0.90) -> R.drawable.ic_battery_80_black_24dp
            in (0.20..0.55) -> R.drawable.ic_battery_20_black_24dp
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