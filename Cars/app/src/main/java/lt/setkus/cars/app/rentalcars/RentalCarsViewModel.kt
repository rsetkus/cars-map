package lt.setkus.cars.app.rentalcars

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arrow.core.Either
import io.reactivex.disposables.CompositeDisposable
import lt.setkus.cars.domain.rentalcars.Car
import lt.setkus.cars.domain.rentalcars.RentalCarsUseCase

class RentalCarsViewModel(
    private val useCase: RentalCarsUseCase,
    private val viewDataMapper: Function1<List<Car>, List<CarViewData>>
) : ViewModel() {

    private val disposables = CompositeDisposable()
    val viewState = MutableLiveData<Either<Throwable, List<CarViewData>>>()

    fun pullRentalCars() {
        val disposable = useCase.build()
            .map(viewDataMapper)
            .subscribe(
                { list: List<CarViewData> -> viewState.postValue(Either.right(list)) },
                { viewState.postValue(Either.left(it)) }
            )
        disposables.add(disposable)
    }

    @VisibleForTesting
    public override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}