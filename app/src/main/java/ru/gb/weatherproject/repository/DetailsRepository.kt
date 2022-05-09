package ru.gb.weatherproject.repository

import ru.gb.weatherproject.viewmodel.DetailsViewModel

interface DetailsRepository {
    fun getWeatherDetails(city:City,callback: DetailsViewModel.Callback)
}