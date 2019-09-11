package lt.setkus.cars.app.common

sealed class ViewState

object Loading : ViewState()
data class Finished<out T : Any>(val data: T) : ViewState()
data class Error<out T : Throwable>(val error: T) : ViewState()