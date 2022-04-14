package ru.gb.weatherproject.repository

interface Repository {
    fun getWeatherFromServer():Weather
    fun getWeatherFromLocalStorage():Weather
}