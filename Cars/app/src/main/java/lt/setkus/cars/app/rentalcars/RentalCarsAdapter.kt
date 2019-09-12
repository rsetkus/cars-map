package lt.setkus.cars.app.rentalcars

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_car.carModelName
import lt.setkus.cars.R

class RentalCarsAdapter : RecyclerView.Adapter<RentalCarsAdapter.RentalCarViewHolder>() {

    private val rentalCars = mutableListOf<CarViewData>()

    fun submitRentalCars(cars: List<CarViewData>) {
        rentalCars.addAll(cars)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentalCarViewHolder {
        val containerView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_car, parent, false)
        return RentalCarViewHolder(containerView)
    }

    override fun getItemCount() = rentalCars.size

    override fun onBindViewHolder(holder: RentalCarViewHolder, position: Int) =
        holder.bindCar(rentalCars[position])

    class RentalCarViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindCar(car: CarViewData) {
            carModelName.text = car.carModelName
        }
    }
}