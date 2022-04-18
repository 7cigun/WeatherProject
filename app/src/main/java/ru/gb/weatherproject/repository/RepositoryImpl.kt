package ru.gb.weatherproject.repository

class RepositoryImpl : Repository {
    override fun getWeatherFromServer(): Weather {
        Thread.sleep(2000L)
        return Weather()
    }

    override fun getWorldWeatherFromLocalStorage(): List<Weather> {
        return getWorldCities()
    }

    override fun getRussianWeatherFromLocalStorage(): List<Weather> {
        return getRussianCities()
    }
}