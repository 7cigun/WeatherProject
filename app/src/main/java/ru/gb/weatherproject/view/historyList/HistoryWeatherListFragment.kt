package ru.gb.weatherproject.view.weatherList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.gb.weatherproject.R
import ru.gb.weatherproject.databinding.FragmentHistoryWeatherListBinding
import ru.gb.weatherproject.databinding.FragmentWeatherListBinding
import ru.gb.weatherproject.repository.Weather
import ru.gb.weatherproject.utils.KEY_BUNDLE_WEATHER
import ru.gb.weatherproject.view.details.DetailsFragment
import ru.gb.weatherproject.viewmodel.AppState
import ru.gb.weatherproject.viewmodel.HistoryViewModel
import ru.gb.weatherproject.viewmodel.MainViewModel

class HistoryWeatherListFragment : Fragment(){

    private var _binding: FragmentHistoryWeatherListBinding? = null
    private val binding: FragmentHistoryWeatherListBinding
        get() {
            return _binding!!
        }

    private val adapter = HistoryWeatherListAdapter()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryWeatherListBinding.inflate(inflater, container, false)
        return binding.root
    }

    private var isRussian = true


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        val viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        val observer = { data: AppState -> renderData(data) }
        viewModel.getData().observe(viewLifecycleOwner, observer)
        viewModel.getAll()
    }

    fun initRecycler() {
        binding.recycleView.adapter = adapter
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.Error -> {
                //binding.loadingLayout.visibility = View.GONE
                Snackbar.make(
                    binding.root,
                    "${resources.getText(R.string.unsuccess_get)} ${data.error}",
                    Snackbar.LENGTH_LONG
                )
                    .show()
            }
            is AppState.Loading -> {
                //binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                //binding.loadingLayout.visibility = View.GONE
                adapter.setData(data.weatherList)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = HistoryWeatherListFragment()
    }
}