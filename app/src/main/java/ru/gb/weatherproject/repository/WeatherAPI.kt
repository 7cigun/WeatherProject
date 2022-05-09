package ru.gb.weatherproject.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import ru.gb.weatherproject.repository.dto.WeatherDTO
import ru.gb.weatherproject.utils.YANDEX_API_KEY
import ru.gb.weatherproject.utils.YANDEX_ENDPOINT

interface WeatherAPI {
    @GET(YANDEX_ENDPOINT)
    fun getWeather(
        @Header(YANDEX_API_KEY) apikey: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Call<WeatherDTO>
}