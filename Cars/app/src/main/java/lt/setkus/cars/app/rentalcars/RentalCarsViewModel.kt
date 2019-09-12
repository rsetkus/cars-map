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

class RentalCarsViewModel(
    private val useCase: RentalCarsUseCase,
    private val viewDataMapper: (List<Car>) -> List<CarViewData>
) : ViewModel() {

    private val disposables = CompositeDisposable()
    val viewState = MutableLiveData<ViewState>()

    fun pullRentalCars() {
        viewState.postValue(Loading)
        val disposable = useCase.build()
            .map(viewDataMapper)
            .subscribe(
                { list: List<CarViewData> -> viewState.postValue(Finished(list)) },
                { viewState.postValue(Error(it)) }
            )
        disposables.add(disposable)
    }

    @VisibleForTesting
    public override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}