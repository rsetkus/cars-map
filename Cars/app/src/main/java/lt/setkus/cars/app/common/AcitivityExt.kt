package lt.setkus.cars.app.common

import android.app.Activity
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import lt.setkus.cars.BuildConfig
import lt.setkus.cars.R

const val ERROR_DIALOG_REQUEST = 9901

inline fun Activity.executeIfGooglePlayServicesAvailable(block: () -> Unit) {
    if (!BuildConfig.DEBUG) {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val result = googleApiAvailability.isGooglePlayServicesAvailable(this)
        when (result) {
            ConnectionResult.SUCCESS -> block()
            else -> {
                if (googleApiAvailability.isUserResolvableError(result)) {
                    googleApiAvailability
                        .getErrorDialog(this, result, ERROR_DIALOG_REQUEST)
                        .show()
                } else {
                    Toast.makeText(this, getString(R.string.google_maps_not_available), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
