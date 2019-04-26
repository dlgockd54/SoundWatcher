package com.example.hclee.soundwatcher.WatchService.Top100WatchService

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.example.hclee.soundwatcher.WatchService.OnPullFinishListenerImpl

class SoundWatchService : Service() {
    private val TAG: String = SoundWatchService::class.java.simpleName

    private var mTopHundredPullingThread: TopHundredPullingThread? = null

    override fun onCreate() {
        super.onCreate()

        Log.d(TAG, "onCreate()")

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(1, Notification())
        }

        mTopHundredPullingThread = TopHundredPullingThread(this,
            OnPullFinishListenerImpl(this@SoundWatchService)
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        Log.d(TAG, "onStartCommand()")

        mTopHundredPullingThread?.let {
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

        mTopHundredPullingThread = null
    }
}
