package ru.gb.weatherproject.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.gb.weatherproject.MyApp
import ru.gb.weatherproject.R
import ru.gb.weatherproject.view.weatherList.WeatherListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction().replace(R.id.container, WeatherListFragment.newInstance()).commit()
        }

        MyApp.getHistoryDao().getAll()
    }
}