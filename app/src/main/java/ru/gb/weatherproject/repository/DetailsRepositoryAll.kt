package ru.gb.weatherproject.repository

import ru.gb.weatherproject.viewmodel.HistoryViewModel

interface DetailsRepositoryAll {
    fun getAllWeatherDetails(callback: HistoryViewModel.CallbackForAll)
}