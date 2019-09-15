package lt.setkus.cars.app.rentalcars

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arrow.core.Either
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import lt.setkus.cars.domain.rentalcars.Car
import lt.setkus.cars.domain.rentalcars.RentalCarsUseCase

class RentalCarsViewModel(
    private val useCase: RentalCarsUseCase,
    private val carDataMapper: Function1<List<Car>, List<CarViewData>>,
    private val carPositionDataMapper: (List<Car>) -> List<CarPosition>
) : ViewModel() {

    private val disposables = CompositeDisposable()
    private val carDataState = MutableLiveData<Either<Throwable, List<CarViewData>>>()
    private val carPositionState = MutableLiveData<Either<Throwable, List<CarPosition>>>()

    fun getCarPositionState(): LiveData<Either<Throwable, List<CarPosition>>> = carPositionState
    fun getCarDataState(): LiveData<Either<Throwable, List<CarViewData>>> = carDataState

    fun pullRentalCars() {
        val single = useCase.build().cache()

        val carDataDisposable = subscribeForCarData(single)
        val carPositionDataDisposable = subscribeForCarPositionData(single)

        disposables.addAll(carDataDisposable, carPositionDataDisposable)
    }

    private fun subscribeForCarData(single: Single<List<Car>>) =
        single
            .map(carDataMapper)
            .subscribe(
                { list: List<CarViewData> -> carDataState.postValue(Either.right(list)) },
                { carDataState.postValue(Either.left(it)) }
            )

    private fun subscribeForCarPositionData(single: Single<List<Car>>) =
        single
            .map(carPositionDataMapper)
            .subscribe(
                { list: List<CarPosition> -> carPositionState.postValue(Either.right(list)) },
                { carPositionState.postValue(Either.left(it)) }
            )

    @VisibleForTesting
    public override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}