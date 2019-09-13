package lt.setkus.cars.app.rentalcars

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class CarViewData(
    val carModelName: CharSequence,
    val ownerName: CharSequence,
    @DrawableRes val fuelIconId: Int,
    @StringRes val fuelTankLevel: Int
)