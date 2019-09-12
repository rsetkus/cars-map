package lt.setkus.cars.app.rentalcars

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import lt.setkus.cars.app.common.Finished
import lt.setkus.cars.app.common.Loading
import lt.setkus.cars.app.common.Error
import lt.setkus.cars.app.testObserver
import lt.setkus.cars.domain.rentalcars.Car
import lt.setkus.cars.domain.rentalcars.RentalCarsUseCase
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.CoreMatchers.isA
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RentalCarsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    val useCase = mockk<RentalCarsUseCase>()
    val mapper = mockk<(List<Car>) -> List<CarViewData>>()

    val viewModel = RentalCarsViewModel(useCase, mapper)

    @Before
    fun setUp() {
        every { mapper(any()) } returns listOf(mockk<CarViewData>())
    }

    @Test
    fun `when requested data from view model then should emit view state`() {
        every { useCase.build() } returns Single.just(listOf())

        val emittedItems = viewModel.viewState.testObserver()
        viewModel.pullRentalCars()

        assertThat(emittedItems.observedValues, hasItem(isA(Loading::class.java)))
        assertThat(emittedItems.observedValues, hasItem(isA(Finished::class.java)))

        verify { useCase.build() }
    }

    @Test
    fun `when data requested from view model and error occurs then should emit error state`() {
        every { useCase.build() } returns Single.error(Throwable())

        val emittedItems = viewModel.viewState.testObserver()
        viewModel.pullRentalCars()

        assertThat(emittedItems.observedValues, hasItem(isA(Loading::class.java)))
        assertThat(emittedItems.observedValues, hasItem(isA(Error::class.java)))

        verify { useCase.build() }
    }

    @Test
    fun `when view model lifecycle ends then should dispose Disposable`() {
        val disposable = mockk<Disposable>()
        val singleDomain = mockk<Single<List<Car>>>(relaxed = true)
        val singleView = mockk<Single<List<CarViewData>>>(relaxed = true)

        every { useCase.build() } returns singleDomain
        every { singleDomain.map(mapper) } returns singleView
        every { singleView.subscribe(any(), any()) } returns disposable
        every { disposable.isDisposed } returns false

        viewModel.pullRentalCars()
        viewModel.onCleared()

        verify { disposable.dispose() }
    }
}