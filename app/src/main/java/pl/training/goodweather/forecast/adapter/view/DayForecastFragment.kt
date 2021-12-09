package pl.training.goodweather.forecast.adapter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import pl.training.goodweather.commons.view.getProperty
import pl.training.goodweather.configuration.Values.CITY_KEY
import pl.training.goodweather.configuration.Values.DEFAULT_CITY_NAME
import pl.training.goodweather.databinding.FragmentDayForecastBinding

class DayForecastFragment : Fragment() {

    private val viewModel: ForecastViewModel by activityViewModels()
    private lateinit var binding: FragmentDayForecastBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentDayForecastBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViews()
    }

    private fun initViews() {
        binding.dayForecastDetailsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
    }

    private fun bindViews() {
        viewModel.forecast.observe(viewLifecycleOwner) { forecast ->
            binding.containerSwipeRefresh.isRefreshing = false
            viewModel.selectedDayForecastDate?.let { date ->
                updateView(forecast.first { it.date == date })
            }
        }
        binding.containerSwipeRefresh.setOnRefreshListener {
            viewModel.refreshForecast(getProperty(CITY_KEY, DEFAULT_CITY_NAME) ?: DEFAULT_CITY_NAME)
        }
    }

    private fun updateView(dayForecastViewModel: DayForecastViewModel) {
        binding.dayForecastDetailsRecyclerView.adapter = DayForecastAdapter(dayForecastViewModel)
    }

}