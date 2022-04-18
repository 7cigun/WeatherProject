package ru.gb.weatherproject.view.weatherList

import ru.gb.weatherproject.repository.Weather

interface OnItemListClickListener {
    fun onItemClick(weather: Weather)
}