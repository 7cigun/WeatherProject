package ru.gb.weatherproject.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import ru.gb.weatherproject.MyApp
import ru.gb.weatherproject.R
import ru.gb.weatherproject.lesson10.MapsFragment
import ru.gb.weatherproject.lesson9.WorkWithContentProviderFragment
import ru.gb.weatherproject.utils.KEY_SP_FILE_LOCATION
import ru.gb.weatherproject.utils.KEY_SP_IS_RUSSIAN
import ru.gb.weatherproject.view.weatherList.HistoryWeatherListFragment
import ru.gb.weatherproject.view.weatherList.WeatherListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherListFragment.newInstance()).commit()
        }

        MyApp.getHistoryDao().getAll()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_history -> {
                val historyFragment = supportFragmentManager.findFragmentByTag("history_fragment_tag")
                if (historyFragment == null) {
                    supportFragmentManager.apply {
                        beginTransaction()
                            .replace(R.id.container, HistoryWeatherListFragment.newInstance(), "history_fragment_tag")
                            .addToBackStack("")
                            .commit()
                    }
                }
            }
            R.id.action_work_with_content_provider -> {
                val contactsFragment = supportFragmentManager.findFragmentByTag("contacts_fragment_tag")
                if (contactsFragment == null) {
                    supportFragmentManager.apply {
                        beginTransaction()
                            .replace(R.id.container, WorkWithContentProviderFragment.newInstance(), "contacts_fragment_tag")
                            .addToBackStack("")
                            .commit()
                    }
                }
            }
            R.id.action_menu_google_maps -> {
                val mapsFragment = supportFragmentManager.findFragmentByTag("maps_fragment_tag")
                if (mapsFragment == null) {
                    supportFragmentManager.apply {
                        beginTransaction()
                            .replace(R.id.container, MapsFragment(), "maps_fragment_tag")
                            .addToBackStack("")
                            .commit()
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}