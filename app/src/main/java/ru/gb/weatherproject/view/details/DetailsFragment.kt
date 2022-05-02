package ru.gb.weatherproject.view.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_datails.*
import ru.gb.weatherproject.R
import ru.gb.weatherproject.databinding.FragmentDatailsBinding
import ru.gb.weatherproject.repository.OnServerResponse
import ru.gb.weatherproject.repository.Weather
import ru.gb.weatherproject.repository.WeatherDTO
import ru.gb.weatherproject.repository.WeatherLoader
import ru.gb.weatherproject.utils.KEY_BUNDLE_WEATHER

class DetailsFragment : Fragment(), OnServerResponse {

    private var _binding: FragmentDatailsBinding? = null
    private val binding: FragmentDatailsBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDatailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    lateinit var currentCityName: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Weather>(KEY_BUNDLE_WEATHER)?.let {
            currentCityName = it.city.name
            WeatherLoader(this@DetailsFragment).loadWeather(it.city.lat, it.city.lon)
        }
    }

    private fun renderData(weather: WeatherDTO) {
        with(binding) {
            loadingLayout.visibility = View.GONE
            cityName.text = currentCityName
            temperatureValue.text = weather.factDTO.temperature.toString()
            feelsLikeValue.text = weather.factDTO.feelsLike.toString()
            cityCoordinates.text =
                "${weather.infoDTO.lat} ${weather.infoDTO.lon}"
        }
        mainView.showSnackbar()
    }

    private fun View.showSnackbar() {
        Snackbar.make(mainView, "${resources.getText(R.string.success_get)}", Snackbar.LENGTH_LONG)
            .show()
    }

    companion object {

        @JvmStatic
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onResponse(weatherDTO: WeatherDTO) {
        renderData(weatherDTO)
    }
}


