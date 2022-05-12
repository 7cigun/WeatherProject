package ru.gb.weatherproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gb.weatherproject.repository.DetailsRepositoryRoomImpl
import ru.gb.weatherproject.repository.RepositoryImpl
import ru.gb.weatherproject.repository.Weather

class HistoryViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: DetailsRepositoryRoomImpl = DetailsRepositoryRoomImpl()
) :
    ViewModel() {

    fun getData(): LiveData<AppState> {
        return liveData
    }

    fun getAll(){
        repository.getAllWeatherDetails(object : CallbackForAll{
            override fun onResponse(listWeather: List<Weather>) {
                liveData.postValue(AppState.Success(listWeather))
            }

            override fun onFail() {
            }

        })
    }


    interface CallbackForAll {
        fun onResponse(listWeather: List<Weather>)

        fun onFail()
    }
}