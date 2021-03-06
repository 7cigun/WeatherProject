package ru.gb.weatherproject.repository

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.gb.weatherproject.BuildConfig
import ru.gb.weatherproject.repository.dto.WeatherDTO
import ru.gb.weatherproject.utils.YANDEX_DOMAIN
import ru.gb.weatherproject.utils.convertDtoToModel
import ru.gb.weatherproject.viewmodel.DetailsViewModel

class DetailsRepositoryOneRetrofit2Impl:DetailsRepositoryOne {
    override fun getWeatherDetails(city: City, callbackMy: DetailsViewModel.Callback) {
        val weatherAPI = Retrofit.Builder().apply {
            baseUrl(YANDEX_DOMAIN)
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        }.build().create(WeatherAPI::class.java)

        weatherAPI.getWeather(BuildConfig.WEATHER_API_KEY,city.lat,city.lon).enqueue(object :
            Callback<WeatherDTO> {
            override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
                if(response.isSuccessful){
                    response.body()?.let {
                        val weather = convertDtoToModel(it)
                        weather.city = city
                        callbackMy.onResponse(weather)
                    }
                }else{
                    callbackMy.onFail()
                }
            }
            override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                callbackMy.onFail()
            }
        })
    }
}