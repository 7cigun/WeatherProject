package ru.gb.weatherproject.view.weatherList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.gb.weatherproject.R
import ru.gb.weatherproject.databinding.FragmentWeatherListRecyclerItemBinding
import ru.gb.weatherproject.repository.Weather
import ru.gb.weatherproject.utils.KEY_BUNDLE_WEATHER
import ru.gb.weatherproject.view.MainActivity
import ru.gb.weatherproject.view.details.DetailsFragment
import ru.gb.weatherproject.viewmodel.MainViewModel

class WeatherListAdapter(private val onItemClickListener: OnItemListClickListener,
                         private var data: List<Weather> = listOf()) :
    RecyclerView.Adapter<WeatherListAdapter.CityHolder>() {

    fun setData(data: List<Weather>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val binding = FragmentWeatherListRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CityHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(data.get(position))
    }

    override fun getItemCount() = data.size

    inner class CityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(weather: Weather) {
            val binding = FragmentWeatherListRecyclerItemBinding.bind(itemView)
            binding.tvCityName.text = weather.city.name
            binding.root.setOnClickListener {
                onItemClickListener.onItemClick(weather)
            }
        }
    }
}