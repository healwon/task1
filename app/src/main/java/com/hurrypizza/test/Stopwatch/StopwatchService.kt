package com.hurrypizza.test.Stopwatch

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.media.app.NotificationCompat
import com.hurrypizza.test.MainActivity
import com.hurrypizza.test.R
import java.util.*
import kotlin.collections.ArrayList
import androidx.core.app.NotificationManagerCompat as NotificationManagerCompat1

class StopwatchService: Service() {
    val CHANNEL_ID = "StopwatchForegroundServiceChannel"

    private var time = 0
    private var isNotZero = false
    private var isRunning = false
    private var timerTask: Timer? = null
    private var id: Int = 0

    val records: ArrayList<Int> = ArrayList<Int>()

    private val myBinder = MyBinder()

    inner class MyBinder: Binder() {
        fun getService(): StopwatchService = this@StopwatchService
    }

    override fun onCreate() {
        id = Random().nextInt(100)
        Log.d("service($id)","onCreate()")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.d("service($id)","onStartCommand()")
        createNotificationChannel()
        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("service($id)","onBind()")
        return myBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("service($id)","onUnbind()")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.d("service($id)","onDestroy()")
        super.onDestroy()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "name"
            val descriptionText = "description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun startForegroundService() {
        //val notification = androidx.core.app.NotificationCompat.Builder(this, CHANNEL_ID)
        //startForeground(1, notification)
    }

    fun startAndPause() {
        when (isRunning) {
            false -> timerTask = kotlin.concurrent.timer(period = 10) {
                time++ }
            true -> timerTask?.cancel()
        }
        isRunning = !isRunning
        isNotZero = true
    }

    fun reset() {
        isRunning = false
        isNotZero = false
        time = 0
        records.clear()
    }

    fun addRecord(time: Int) {
        records.add(time)
    }

    fun getRecordSize(): Int {
        return records.size
    }

    fun getRecord(index: Int): Int {
        return records[index]
    }

    fun getIsRunning(): Boolean {
        return isRunning
    }

    fun getIsNotZero(): Boolean {
        return isNotZero
    }

    fun getTime(): Int {
        return time
    }
}