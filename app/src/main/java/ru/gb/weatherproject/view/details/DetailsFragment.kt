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
import ru.gb.weatherproject.repository.Weather
import ru.gb.weatherproject.utils.KEY_BUNDLE_WEATHER

class DetailsFragment : Fragment() {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Weather>(KEY_BUNDLE_WEATHER)?.let{
            renderData(it)
        }
    }

    private fun renderData(weather: Weather) {
        with(binding) {
            loadingLayout.visibility = View.GONE
            cityName.text = weather.city.name
            temperatureValue.text = weather.temperature.toString()
            feelsLikeValue.text = weather.feelslike.toString()
            cityCoordinates.text =
                "${weather.city.lat} ${weather.city.lon}"
        }
            //Snackbar.make(mainView, "${resources.getText(R.string.success_get)}", Snackbar.LENGTH_LONG).show()
            mainView.showSnackbar()
        }

    private fun View.showSnackbar() {
        Snackbar.make(mainView, "${resources.getText(R.string.success_get)}", Snackbar.LENGTH_LONG).show()
    }

    companion object {

        @JvmStatic
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}


