package ru.gb.weatherproject.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.NotificationCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import ru.gb.weatherproject.MyApp
import ru.gb.weatherproject.R
import ru.gb.weatherproject.lesson10.MapsFragment
import ru.gb.weatherproject.lesson11.FCMService
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

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("###Token_logs###", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            Log.d("###Token_logs###", "Наш токен: $token")
        })

        Thread{
            MyApp.getHistoryDao().getAll()
        }.start()
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