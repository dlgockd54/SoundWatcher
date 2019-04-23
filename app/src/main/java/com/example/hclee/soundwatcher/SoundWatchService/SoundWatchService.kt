package com.example.hclee.soundwatcher.SoundWatchService

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class SoundWatchService : Service() {
    private val TAG: String = SoundWatchService::class.java.simpleName

    private lateinit var mTopHundredPullingThread: TopHundredPullingThread

    override fun onCreate() {
        super.onCreate()

        Log.d(TAG, "onCreate()")

        mTopHundredPullingThread = TopHundredPullingThread(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        Log.d(TAG, "onStartCommand()")

        mTopHundredPullingThread.start()

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy()")
    }
}
