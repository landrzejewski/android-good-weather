package pl.training.goodweather.forecast.adapter.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.training.goodweather.R
import pl.training.goodweather.commons.setDrawable
import pl.training.goodweather.databinding.ItemDayForecastBinding

internal class ForecastListAdapter(private var forecast: List<DayForecastViewModel> = emptyList()) : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    internal class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemDayForecastBinding.bind(view)

        fun update(dayForecastViewModel: DayForecastViewModel) {
            binding.iconImageView.setDrawable(dayForecastViewModel.icon)
            binding.temperatureTextView.text = dayForecastViewModel.temperature
            binding.dateTextView.text = dayForecastViewModel.date
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_day_forecast, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.update(forecast[position])
    }

    override fun getItemCount() = forecast.size

    internal fun update(forecast: List<DayForecastViewModel>) {
        this.forecast = forecast
        notifyDataSetChanged()
    }

}