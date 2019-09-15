package lt.setkus.cars.app.rentalcars

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import fr.xgouchet.elmyr.junit.JUnitForger
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import lt.setkus.cars.app.asCar
import lt.setkus.cars.app.testObserver
import lt.setkus.cars.domain.rentalcars.Car
import lt.setkus.cars.domain.rentalcars.RentalCarsUseCase
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RentalCarsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val forger = JUnitForger()

    val useCase = mockk<RentalCarsUseCase>()
    val mapper = mockk<(List<Car>) -> List<CarViewData>>()
    val positionMapper = mockk<(List<Car>) -> List<CarPosition>>()

    val cars = listOf(forger.asCar())

    val viewModel = RentalCarsViewModel(useCase, mapper, positionMapper)

    @Before
    fun setUp() {
        every { mapper(any()) } returns listOf(mockk())
        every { positionMapper(any()) } returns listOf(mockk())
    }

    @Test
    fun `when requested data from view model then should emit cars data`() {
        every { useCase.build() } returns Single.just(cars)

        val emittedItems = viewModel.getCarDataState().testObserver()
        viewModel.pullRentalCars()

        assertTrue(emittedItems.observedValues.first().isRight())

        verify { useCase.build() }
        verify { mapper(cars) }
    }

    @Test
    fun `when data requested from view model and error occurs then should emit error state for cars data`() {
        every { useCase.build() } returns Single.error(Throwable())

        val emittedItems = viewModel.getCarDataState().testObserver()
        viewModel.pullRentalCars()

        assertTrue(emittedItems.observedValues.first().isLeft())

        verify { useCase.build() }
        verify(exactly = 0) { mapper(any()) }
    }

    @Test
    fun `when requested data from view then should emit position data`() {
        every { useCase.build() } returns Single.just(cars)

        val emittedItems = viewModel.getCarPositionState().testObserver()
        viewModel.pullRentalCars()

        assertTrue(emittedItems.observedValues.first().isRight())

        verify { useCase.build() }
        verify { positionMapper(cars) }
    }

    @Test
    fun `when requested data from view model and error occurs then should emit error for position data`() {
        every { useCase.build() } returns Single.error(Throwable())

        val emittedItems = viewModel.getCarPositionState().testObserver()
        viewModel.pullRentalCars()

        assertTrue(emittedItems.observedValues.first().isLeft())

        verify { useCase.build() }
        verify(exactly = 0) { positionMapper(any()) }
    }

    @Test
    fun `when view model lifecycle ends then should dispose Disposable`() {
        val carViewDataDisposable = prepareCarViewDataDisposable()

        viewModel.pullRentalCars()
        viewModel.onCleared()

        verify { carViewDataDisposable.dispose() }
    }

    private fun prepareCarViewDataDisposable(): Disposable {
        val disposable = mockk<Disposable>(relaxed = true)
        val singleDomain = mockk<Single<List<Car>>>()
        val singleDomainCached = mockk<Single<List<Car>>>()
        val singleView = mockk<Single<List<CarViewData>>>()

        every { useCase.build() } returns singleDomain
        every { singleDomain.cache() } returns singleDomainCached
        every { singleDomainCached.map(any<Function<List<Car>, List<CarViewData>>>()) } returns singleView
        every { singleView.subscribe(any(), any()) } returns disposable
        every { disposable.isDisposed } returns false
        return disposable
    }
}