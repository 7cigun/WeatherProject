package ru.gb.weatherproject.repository

fun interface OnServerResponse {
    fun onResponse(weatherDTO: WeatherDTO)
}