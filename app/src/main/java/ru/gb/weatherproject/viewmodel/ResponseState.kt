package ru.gb.weatherproject.viewmodel

import ru.gb.weatherproject.repository.Weather

sealed class ResponseState {
    object Error1:ResponseState()
    data class Error2(val weatherList:List<Weather>):ResponseState(){
        fun test(){}
    }
    data class Error3(val error:Throwable):ResponseState()
}