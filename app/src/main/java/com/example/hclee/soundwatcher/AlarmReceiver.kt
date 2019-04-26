package com.example.hclee.soundwatcher

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.hclee.soundwatcher.watchservice.newalbumwatchservice.NewAlbumWatchService
import com.example.hclee.soundwatcher.watchservice.tophundredwatchservice.TopHundredWatchService

class AlarmReceiver : BroadcastReceiver() {
    private val TAG: String = AlarmReceiver::class.java.simpleName

    companion object {
        const val TOP_HUNDRED: Int = 0
        const val NEW_ALBUM: Int = 1
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive()")
        Log.d(TAG, "type: ${intent.getIntExtra("type", -1)}")

        val soundWatchIntent: Intent = Intent()

        when(intent.getIntExtra("type", TOP_HUNDRED)) {
            TOP_HUNDRED -> {
                soundWatchIntent.component = ComponentName(context, TopHundredWatchService::class.java)
            }
            NEW_ALBUM -> {
                soundWatchIntent.component = ComponentName(context, NewAlbumWatchService::class.java)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(soundWatchIntent)
        }
        else {
            context.startService(soundWatchIntent)
        }
    }
}
