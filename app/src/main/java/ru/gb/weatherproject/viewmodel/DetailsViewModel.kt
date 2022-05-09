package ru.gb.weatherproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gb.weatherproject.repository.City
import ru.gb.weatherproject.repository.DetailsRepository
import ru.gb.weatherproject.repository.DetailsRepositoryRetrofit2Impl
import ru.gb.weatherproject.repository.Weather

class DetailsViewModel(
    private val liveData: MutableLiveData<DetailsState> = MutableLiveData(),
    private val repository: DetailsRepository = DetailsRepositoryRetrofit2Impl()
) :ViewModel() {

    fun getLiveData()=liveData

    fun getWeather(city: City){
        liveData.postValue(DetailsState.Loading)
        repository.getWeatherDetails(city,object :Callback{
            override fun onResponse(weather: Weather) {
                liveData.postValue(DetailsState.Success(weather))
            }
        })
    }

    interface Callback {
        fun onResponse(weather: Weather)
    }



}