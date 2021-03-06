package ru.gb.weatherproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gb.weatherproject.repository.RepositoryImpl

class MainViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: RepositoryImpl = RepositoryImpl()
) :
    ViewModel() {

    fun getData(): LiveData<AppState> {
        return liveData
    }

    fun getWeatherRussia() = getWeather(true)
    fun getWeatherWorld() = getWeather(false)

    fun getWeather(isRussian: Boolean) {
        Thread {
            liveData.postValue(AppState.Loading)

            if ((0..10).random() > 1) {
                val answer = if (!isRussian) repository.getWorldWeatherFromLocalStorage() else repository.getRussianWeatherFromLocalStorage()
                liveData.postValue(AppState.Success(answer))
            } else
                liveData.postValue((AppState.Error(IllegalAccessException())))
        }.start()
    }
}