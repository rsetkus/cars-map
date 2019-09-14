package lt.setkus.cars.app.rentalcars

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class CarViewData(
    val id: String,
    val carModelName: CharSequence,
    val ownerName: CharSequence,
    @DrawableRes val fuelIconId: Int,
    @StringRes val fuelTankLevel: Int,
    val imageUrl: String,
    @StringRes val fuelType: Int
)