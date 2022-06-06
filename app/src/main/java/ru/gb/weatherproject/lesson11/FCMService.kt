package ru.gb.weatherproject.lesson11

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.gb.weatherproject.R

class FCMService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        if(!message.data.isNullOrEmpty()){
            val title = message.data[KEY_TITLE]
            val message = message.data[KEY_MESSAGE]
            if(!title.isNullOrEmpty()&&!message.isNullOrEmpty()){
                push(title, message)
            }
        }
    }

    companion object {
        private const val NOTIFICATION_ID_HIGH = 1
        private const val CHANNEL_ID_HIGH = "channel_id_1"

        private const val KEY_TITLE = "myTitle"
        private const val KEY_MESSAGE = "myMessage"
    }

    private fun push(title:String, message:String){
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationBuilderHigh = NotificationCompat.Builder(this, CHANNEL_ID_HIGH).apply {
            setSmallIcon(R.drawable.ic_map_pin)
            setContentTitle(title)
            setContentText(message)
            priority = NotificationManager.IMPORTANCE_HIGH
        }

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

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("###Token_logs###", "Получение нового токена: $token")
    }
}