package ru.gb.weatherproject.repository

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import ru.gb.weatherproject.BuildConfig
import ru.gb.weatherproject.repository.dto.WeatherDTO
import ru.gb.weatherproject.utils.*
import java.io.BufferedReader
import java.io.InputStreamReader

import java.net.URL
import javax.net.ssl.HttpsURLConnection

class DetailsService(val name: String = ""): IntentService(name)
{
    override fun onHandleIntent(intent: Intent?)
    {
        Log.d("@DetServ@", "EHLO DetailsService")
        intent?.let{
            val lat = it.getDoubleExtra(KEY_BUNDLE_LAT,0.0)
            val lon = it.getDoubleExtra(KEY_BUNDLE_LON, 0.0)
            Log.d("@DetServ@", "DetailsService got $lat $lon")

            val urlText = "$YANDEX_DOMAIN${YANDEX_ENDPOINT}lat=$lat&lon=$lon"
            //val urlText = "http://212.86.114.27/v2/informers?lat=$lat&lon=$lon"
            val uri = URL(urlText)

            val urlConnection: HttpsURLConnection =
                (uri.openConnection() as HttpsURLConnection).apply {
                    connectTimeout = 1000
                    readTimeout = 1000
                    addRequestProperty(YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
                }

            val headers = urlConnection.headerFields
            val responseCode = urlConnection.responseCode
            val responseMessage = urlConnection.responseMessage

            val serverside = 500..599
            val clientside = 400..499
            val responseOk = 200..299
            val message = Intent(KEY_WAVE_SERVICE_BROADCAST)

            try {
                when (responseCode) {
                    in serverside -> {
                    }
                    in clientside -> {
                    }
                    in responseOk -> {
                        val buffer =
                            BufferedReader(InputStreamReader(urlConnection.inputStream))
                        val weatherDTO: WeatherDTO =
                            Gson().fromJson(buffer, WeatherDTO::class.java)

                        message.putExtra(
                            KEY_BUNDLE_SERVICE_BROADCAST_WEATHER,
                            weatherDTO
                        )
                        LocalBroadcastManager.getInstance(this).sendBroadcast(message)
                    }
                    else -> {}
                }
            } catch (e: JsonSyntaxException) {
            } finally {
                urlConnection.disconnect()
            }
        }
    }
}