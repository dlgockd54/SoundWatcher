package com.example.hclee.soundwatcher.watchservice.newalbumwatchservice

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.example.hclee.soundwatcher.watchservice.OnPullFinishListenerImpl

class NewAlbumWatchService : Service() {
    private val TAG: String = NewAlbumWatchService::class.java.simpleName

    private var mNewAlbumPullingThread: NewAlbumPullingThread? = null

    override fun onCreate() {
        super.onCreate()

        Log.d(TAG, "onCreate()")

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(2, Notification())
        }

        mNewAlbumPullingThread = NewAlbumPullingThread(this, OnPullFinishListenerImpl(this@NewAlbumWatchService))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        Log.d(TAG, "onStartCommand()")

        mNewAlbumPullingThread?.let {
            if(it.state == Thread.State.NEW) {
                it.start()
            }
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy()")

        mNewAlbumPullingThread = null
    }
}
