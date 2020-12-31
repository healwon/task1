package com.hurrypizza.test.Stopwatch

import android.app.Service
import android.content.Intent
import android.os.IBinder

class StopwatchService: Service() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}