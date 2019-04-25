package com.example.hclee.soundwatcher

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.hclee.soundwatcher.SoundWatchService.SoundWatchService

class AlarmReceiver : BroadcastReceiver() {
    private val TAG: String = AlarmReceiver::class.java.simpleName

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive()")

        val intent: Intent = Intent(context, SoundWatchService::class.java)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        }
        else {
            context.startService(intent)
        }
    }
}
