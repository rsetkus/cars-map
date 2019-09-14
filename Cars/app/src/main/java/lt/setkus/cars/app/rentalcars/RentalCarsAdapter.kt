package lt.setkus.cars.app.rentalcars

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_car.carImage
import kotlinx.android.synthetic.main.item_car.carModelName
import kotlinx.android.synthetic.main.item_car.fuelLevel
import kotlinx.android.synthetic.main.item_car.fuelType
import kotlinx.android.synthetic.main.item_car.ownerName
import lt.setkus.cars.R

class RentalCarsAdapter(val clickListener: (CarViewData) -> Unit) :
    RecyclerView.Adapter<RentalCarsAdapter.RentalCarViewHolder>() {

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

    override fun onBindViewHolder(holder: RentalCarViewHolder, position: Int) {
        holder.bindCar(rentalCars[position])
        holder.containerView.setOnClickListener {
            clickListener(rentalCars[position])
        }
    }

    class RentalCarViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindCar(car: CarViewData) {
            carModelName.text = car.carModelName
            ownerName.text = car.ownerName
            fuelLevel.setText(car.fuelTankLevel)
            fuelLevel.setCompoundDrawablesWithIntrinsicBounds(car.fuelIconId, 0, 0, 0)
            fuelType.setText(car.fuelType)
            loadCarImage(car.imageUrl)
        }

        private fun loadCarImage(url: String) {
            Picasso.get()
                .load(url)
                .noFade()
                .fit()
                .centerInside()
                .placeholder(R.mipmap.placeholder_vehicle_angle)
                .into(carImage)
        }
    }
}