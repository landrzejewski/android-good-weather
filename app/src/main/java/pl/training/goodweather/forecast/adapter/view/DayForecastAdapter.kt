package pl.training.goodweather.forecast.adapter.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.training.goodweather.R
import pl.training.goodweather.commons.view.setDrawable
import pl.training.goodweather.databinding.ItemDayForecastDetailsBinding

class DayForecastAdapter(private var dayForecast: DayForecastViewModel) : RecyclerView.Adapter<DayForecastAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemDayForecastDetailsBinding.bind(view)

        fun update(dayForecastViewModel: DayForecastViewModel) {
            binding.iconImage.setDrawable(dayForecastViewModel.icon)
            binding.descriptionTextView.text = dayForecastViewModel.description
            binding.temperatureTextView.text = dayForecastViewModel.temperature
            binding.pressureTextView.text = dayForecastViewModel.pressure
            binding.dateTextView.text = dayForecastViewModel.date
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_day_forecast_details, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.update(dayForecast)
    }

    override fun getItemCount() = 1

}