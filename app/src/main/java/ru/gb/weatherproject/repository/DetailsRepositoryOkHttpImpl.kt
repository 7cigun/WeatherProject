package ru.gb.weatherproject.repository

import ru.gb.weatherproject.BuildConfig
import ru.gb.weatherproject.repository.dto.WeatherDTO
import ru.gb.weatherproject.utils.YANDEX_API_KEY
import ru.gb.weatherproject.utils.YANDEX_DOMAIN
import ru.gb.weatherproject.utils.YANDEX_ENDPOINT
import ru.gb.weatherproject.utils.convertDtoToModel
import ru.gb.weatherproject.viewmodel.DetailsViewModel
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class DetailsRepositoryOkHttpImpl:DetailsRepository {
    override fun getWeatherDetails(city: City,callback: DetailsViewModel.Callback) {
        val client = OkHttpClient()
        val builder = Request.Builder()
        builder.addHeader(YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
        builder.url("$YANDEX_DOMAIN${YANDEX_ENDPOINT}lat=${city.lat}&lon=${city.lon}")
        val request = builder.build()
        val call = client.newCall(request)
        Thread{
            val response = call.execute()
            if(response.isSuccessful){
                val serverResponse = response.body()!!.string()
                val weatherDTO: WeatherDTO = Gson().fromJson(serverResponse, WeatherDTO::class.java)
                val weather = convertDtoToModel(weatherDTO)
                weather.city = city
                callback.onResponse(weather)
            }else{
            }
        }.start()
    }
}