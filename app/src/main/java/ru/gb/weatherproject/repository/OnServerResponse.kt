package ru.gb.weatherproject.repository

import ru.gb.weatherproject.repository.dto.WeatherDTO

fun interface OnServerResponse {
    fun onResponse(weatherDTO: WeatherDTO)
}