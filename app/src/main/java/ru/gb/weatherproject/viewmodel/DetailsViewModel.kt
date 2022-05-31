package ru.gb.weatherproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gb.weatherproject.repository.*

class DetailsViewModel(
    private val liveData: MutableLiveData<DetailsState> = MutableLiveData(),
    private val repositoryAdd: DetailsRepositoryAdd = DetailsRepositoryRoomImpl()
) :ViewModel() {

    private var repositoryOne: DetailsRepositoryOne = DetailsRepositoryOneRetrofit2Impl()

    fun getLiveData()=liveData

    fun getWeather(city: City){
        liveData.postValue(DetailsState.Loading)
        if (isInternet()){
            repositoryOne = DetailsRepositoryOneRetrofit2Impl()
        } else {
            repositoryOne = DetailsRepositoryRoomImpl()
        }

        repositoryOne.getWeatherDetails(city,object :Callback{
            override fun onResponse(weather: Weather) {
                liveData.postValue(DetailsState.Success(weather))
                if (isInternet()) {
                    Thread{
                        repositoryAdd.addWeather(weather)
                    }.start()

                }
            }
            override fun onFail() {
            }
        })

    }

    private fun isInternet(): Boolean {
        return true
    }

    interface Callback {
        fun onResponse(weather: Weather)

        fun onFail()
    }

}