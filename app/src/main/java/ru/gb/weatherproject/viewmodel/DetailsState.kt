package ru.gb.weatherproject.viewmodel

import ru.gb.weatherproject.repository.Weather

sealed class DetailsState {
    object Loading:DetailsState()
    data class Success(val weather:Weather):DetailsState()
    data class Error(val error:Throwable):DetailsState()
}
