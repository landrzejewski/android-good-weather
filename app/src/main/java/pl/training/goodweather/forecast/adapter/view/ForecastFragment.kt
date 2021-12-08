package pl.training.goodweather.forecast.adapter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import pl.training.goodweather.GoodWeatherApplication.Companion.componentsGraph
import pl.training.goodweather.R
import pl.training.goodweather.commons.getProperty
import pl.training.goodweather.commons.hideKeyboard
import pl.training.goodweather.commons.logging.Logger
import pl.training.goodweather.commons.setDrawable
import pl.training.goodweather.commons.setProperty
import pl.training.goodweather.configuration.Values.CITY_KEY
import pl.training.goodweather.configuration.Values.DEFAULT_CITY_NAME
import pl.training.goodweather.databinding.FragmentForecastBinding
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ForecastFragment : Fragment() {

    private val viewModel: ForecastViewModel by activityViewModels()
    private val forecastListAdapter = ForecastListAdapter()
    private lateinit var binding: FragmentForecastBinding
    private val disposables = CompositeDisposable()
    @Inject
    lateinit var logger: Logger

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        componentsGraph.inject(this)
        binding = FragmentForecastBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViews()
        loadLatestForecast()
    }

    private fun initViews() {
        binding.forecastRecyclerView.layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
        binding.forecastRecyclerView.adapter = forecastListAdapter
    }

    private fun bindViews() {
        viewModel.forecast.observe(viewLifecycleOwner, ::updateView)
        binding.cityNameEditText.textChanges()
            .map { it.toString() }
            .filter { it.isNotEmpty() }
            .filter { it != getProperty(CITY_KEY)}
            .debounce(3, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::refreshForecast) { logger.log("Fetching forecast failed") }
            .addTo(disposables)
        binding.iconImage.setOnClickListener {
            findNavController().navigate(R.id.show_day_forecast_details)
        }
        forecastListAdapter.tapListener = {
            viewModel.selectedDayForecastDate = it.date
            findNavController().navigate(R.id.show_day_forecast_details)
        }
    }

    private fun refreshForecast(city: String) {
        view?.hideKeyboard()
        setProperty(CITY_KEY, city)
        viewModel.refreshForecast(city)
    }

    private fun loadLatestForecast() {
        getProperty(CITY_KEY, DEFAULT_CITY_NAME)?.let {
            viewModel.refreshForecast(it)
            //binding.cityNameEditText.setText(it)
        }
    }

    private fun updateView(forecast: List<DayForecastViewModel>) {
        with(forecast.first()) {
            viewModel.selectedDayForecastDate = date
            binding.iconImage.setDrawable(icon)
            binding.descriptionTextView.text = description
            binding.temperatureTextView.text = temperature
            binding.pressureTextView.text = pressure
        }
        forecastListAdapter.update(forecast.drop(1))
    }

    override fun onDetach() {
        super.onDetach()
        disposables.clear()
    }

}