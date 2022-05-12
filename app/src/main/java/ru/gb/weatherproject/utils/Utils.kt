package ru.gb.weatherproject.utils

import ru.gb.weatherproject.domain.room.HistoryEntity
import ru.gb.weatherproject.repository.City
import ru.gb.weatherproject.repository.Weather
import ru.gb.weatherproject.repository.dto.FactDTO
import ru.gb.weatherproject.repository.dto.WeatherDTO
import ru.gb.weatherproject.repository.getDefaultCity

const val KEY_BUNDLE_WEATHER = "key"
const val YANDEX_DOMAIN = "https://api.weather.yandex.ru/"
//const val YANDEX_DOMAIN_HARD_MODE = "http://212.86.114.27/"
const val YANDEX_ENDPOINT = "v2/informers?"
const val YANDEX_API_KEY = "X-Yandex-API-Key"
const val KEY_BUNDLE_LAT = "lat1"
const val KEY_BUNDLE_LON = "lon1"
const val KEY_BUNDLE_SERVICE_BROADCAST_WEATHER = "weather_s_b"
const val KEY_WAVE_SERVICE_BROADCAST = "myaction_wave"

class Utils {
}

fun convertDtoToModel(weatherDTO: WeatherDTO): Weather {
    val fact: FactDTO = weatherDTO.factDTO
    return (Weather(getDefaultCity(), fact.temperature, fact.feelsLike, fact.icon))
}

fun convertHistoryEntityToWeather(entityList: List<HistoryEntity>): List<Weather> {
    return entityList.map {
        Weather(City(it.city, 0.0, 0.0), it.temperature, it.feelsLike, it.icon)
    }
}

fun convertWeatherToEntity(weather: Weather): HistoryEntity {
    return HistoryEntity(0, weather.city.name, weather.temperature,weather.feelsLike, weather.icon)
}