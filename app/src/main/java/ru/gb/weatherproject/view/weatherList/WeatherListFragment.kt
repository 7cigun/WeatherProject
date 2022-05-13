package ru.gb.weatherproject.view.weatherList

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_weather_list.*
import ru.gb.weatherproject.R
import ru.gb.weatherproject.databinding.FragmentWeatherListBinding
import ru.gb.weatherproject.repository.Weather
import ru.gb.weatherproject.repository.getRussianCities
import ru.gb.weatherproject.utils.KEY_BUNDLE_WEATHER
import ru.gb.weatherproject.utils.KEY_SP_FILE_LOCATION
import ru.gb.weatherproject.utils.KEY_SP_IS_RUSSIAN
import ru.gb.weatherproject.view.MainActivity
import ru.gb.weatherproject.view.details.DetailsFragment
import ru.gb.weatherproject.viewmodel.AppState
import ru.gb.weatherproject.viewmodel.MainViewModel

class WeatherListFragment : Fragment(), OnItemListClickListener {

    private var _binding: FragmentWeatherListBinding? = null
    private val binding: FragmentWeatherListBinding
        get() {
            return _binding!!
        }

    private val adapter = WeatherListAdapter(this) //private

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        return binding.root
    }

    private var isRussian = true //private
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        val observer = { data: AppState -> renderData(data) }
        viewModel.getData().observe(viewLifecycleOwner, observer)
        setupFub()
        loadLocation()
        viewModel.getWeather(isRussian)
    }

    fun setupFub() {
        binding.floatActionButton.setOnClickListener {
            isRussian = !isRussian

            if (isRussian) {
                viewModel.getWeatherRussia()
                binding.floatActionButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_russia
                    )
                )
                saveLocation(isRussian)
            } else {
                binding.floatActionButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_earth
                    )
                )
                viewModel.getWeatherWorld()
                saveLocation(isRussian)
            }
        }
    }

    fun saveLocation(isRussian: Boolean) {
        val sharedPref =
            requireContext().getSharedPreferences(KEY_SP_FILE_LOCATION, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean(KEY_SP_IS_RUSSIAN, true)
        editor.apply()
    }

    fun loadLocation(): Boolean {
        val sharedPref = requireContext().getSharedPreferences(KEY_SP_FILE_LOCATION, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(KEY_SP_IS_RUSSIAN, true)
    }

    fun initRecycler() {
        binding.recycleView.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(
                    binding.root,
                    "${resources.getText(R.string.unsuccess_get)} ${data.error}",
                    Snackbar.LENGTH_LONG
                )
                    .show()
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                adapter.setData(data.weatherList)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = WeatherListFragment()
    }

    override fun onItemClick(weather: Weather) {
        requireActivity().supportFragmentManager.beginTransaction().add(
            R.id.container,
            DetailsFragment.newInstance(Bundle().apply {
                putParcelable(KEY_BUNDLE_WEATHER, weather)
            })
        ).addToBackStack("").commit()
    }
}