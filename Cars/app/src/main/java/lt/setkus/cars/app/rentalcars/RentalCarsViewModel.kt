package lt.setkus.cars.app.rentalcars

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import lt.setkus.cars.app.common.Error
import lt.setkus.cars.app.common.Finished
import lt.setkus.cars.app.common.Loading
import lt.setkus.cars.app.common.ViewState
import lt.setkus.cars.domain.rentalcars.Car
import lt.setkus.cars.domain.rentalcars.RentalCarsUseCase

class RentalCarsViewModel(private val useCase: RentalCarsUseCase) : ViewModel() {

    private val disposables = CompositeDisposable()
    val viewState = MutableLiveData<ViewState>()

    fun pullRentalCars() {
        viewState.postValue(Loading)
        disposables.add(
            useCase.build()
                .subscribe(
                    { list: List<Car> -> viewState.postValue(Finished(list)) },
                    { viewState.postValue(Error(it)) }
                )
        )
    }

    @VisibleForTesting
    public override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}