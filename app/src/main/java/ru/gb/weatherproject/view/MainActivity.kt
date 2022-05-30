package ru.gb.weatherproject.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.NotificationCompat
import ru.gb.weatherproject.MyApp
import ru.gb.weatherproject.R
import ru.gb.weatherproject.lesson10.MapsFragment
import ru.gb.weatherproject.lesson9.WorkWithContentProviderFragment
import ru.gb.weatherproject.utils.KEY_SP_FILE_LOCATION
import ru.gb.weatherproject.utils.KEY_SP_IS_RUSSIAN
import ru.gb.weatherproject.view.weatherList.HistoryWeatherListFragment
import ru.gb.weatherproject.view.weatherList.WeatherListFragment

class MainActivity : AppCompatActivity() {

    companion object {
        private const val NOTIFICATION_ID_LOW = 1
        private const val NOTIFICATION_ID_HIGH = 2
        private const val CHANNEL_ID_LOW = "channel_id_1"
        private const val CHANNEL_ID_HIGH = "channel_id_2"
    }

    private fun push(){
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationBuilderLow = NotificationCompat.Builder(this, CHANNEL_ID_LOW).apply {
            setSmallIcon(R.drawable.ic_map_pin)
            setContentTitle(getString((R.string.first_channel_title)))
            setContentText(getString((R.string.first_channel_text)))
            priority = NotificationManager.IMPORTANCE_LOW
        }

        val notificationBuilderHigh = NotificationCompat.Builder(this, CHANNEL_ID_HIGH).apply {
            setSmallIcon(R.drawable.ic_map_marker)
            setContentTitle(getString((R.string.second_channel_title)))
            setContentText(getString((R.string.second_channel_text)))
            priority = NotificationManager.IMPORTANCE_HIGH
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelNameLow = "Name $CHANNEL_ID_LOW"
            val channelDescriptionLow = "Description $CHANNEL_ID_LOW"
            val channelPriorityLow = NotificationManager.IMPORTANCE_LOW
            val channelLow = NotificationChannel(CHANNEL_ID_LOW, channelNameLow, channelPriorityLow).apply {
                description = channelDescriptionLow
            }
            notificationManager.createNotificationChannel(channelLow)
        }
        notificationManager.notify(NOTIFICATION_ID_LOW,notificationBuilderLow.build())

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelNameHigh = "Name $CHANNEL_ID_HIGH"
            val channelDescriptionHigh = "Description $CHANNEL_ID_HIGH"
            val channelPriorityHigh = NotificationManager.IMPORTANCE_HIGH
            val channelHigh = NotificationChannel(CHANNEL_ID_HIGH, channelNameHigh, channelPriorityHigh).apply {
                description = channelDescriptionHigh
            }
            notificationManager.createNotificationChannel(channelHigh)
        }
        notificationManager.notify(NOTIFICATION_ID_HIGH,notificationBuilderHigh.build())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherListFragment.newInstance()).commit()
        }

        MyApp.getHistoryDao().getAll()

        push()
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