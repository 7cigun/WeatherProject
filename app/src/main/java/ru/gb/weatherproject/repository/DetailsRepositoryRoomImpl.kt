package ru.gb.weatherproject.repository

import ru.gb.weatherproject.MyApp
import ru.gb.weatherproject.utils.convertHistoryEntityToWeather
import ru.gb.weatherproject.utils.convertWeatherToEntity
import ru.gb.weatherproject.viewmodel.DetailsViewModel
import ru.gb.weatherproject.viewmodel.HistoryViewModel

class DetailsRepositoryRoomImpl:DetailsRepositoryOne, DetailsRepositoryAll, DetailsRepositoryAdd{
    override fun getAllWeatherDetails(callback: HistoryViewModel.CallbackForAll) {
        Thread{
            callback.onResponse(convertHistoryEntityToWeather(MyApp.getHistoryDao().getAll()))
        }.start()
    }

    override fun getWeatherDetails(city: City, callback: DetailsViewModel.Callback) {
        val list = convertHistoryEntityToWeather(MyApp.getHistoryDao().getHistoryForCity(city.name))
        if (list.isEmpty()){
            callback.onFail()
        } else {
            callback.onResponse(list.last())
        }

    }

    override fun addWeather(weather: Weather) {
        MyApp.getHistoryDao().insert(convertWeatherToEntity(weather))
    }

}