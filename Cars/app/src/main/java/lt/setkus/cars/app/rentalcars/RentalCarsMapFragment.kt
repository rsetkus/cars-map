package lt.setkus.cars.app.rentalcars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import lt.setkus.cars.R
import lt.setkus.cars.app.common.Finished
import lt.setkus.cars.app.common.Loading
import lt.setkus.cars.app.common.Error
import lt.setkus.cars.app.common.ViewState
import org.koin.androidx.scope.currentScope

class RentalCarsMapFragment : Fragment() {

    val viewModel: RentalCarsViewModel by currentScope.inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_rental_cars_map, container, false)

    /**
     * Called immediately after [.onCreateView]
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     * @param view The View returned by [.onCreateView].
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewStateObserver = Observer<ViewState> { viewState ->
            val state = when (viewState) {
                is Loading -> "Loading"
                is Finished<*> -> "Finished"
                is Error<*> -> "Error"
            }

            Toast.makeText(context, state, Toast.LENGTH_SHORT).show()
        }

        viewModel.viewState.observe(this, viewStateObserver)
        viewModel.pullRentalCars()
    }

    companion object {

        fun newInstance() = RentalCarsMapFragment()
    }
}