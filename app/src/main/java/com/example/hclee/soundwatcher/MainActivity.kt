package com.example.hclee.soundwatcher

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    private val TAG: String = MainActivity::class.java.simpleName
    private val ALARM_START_ACTION: String = "com.example.hclee.ALARM_START"
    private val TOP_HUNDRED_INTERVAL_TIME_MILLIS: Long = 500 * 60 * 1
    private val NEW_ALBUM_INTERVAL_TIME_MILLIS: Long = 100 * 60 * 1

    private lateinit var mAlarmManager: AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main) // Not to show activity

        Log.d(TAG, "onCreate()")

        val componentName: ComponentName = ComponentName(this, AlarmReceiver::class.java)
        val topHundredAlarmIntent: Intent = Intent(ALARM_START_ACTION).apply {
            component = componentName
            putExtra("type", AlarmReceiver.TOP_HUNDRED)
        }
        val topHundredPendingIntent: PendingIntent = PendingIntent.getBroadcast(this,
            111,
            topHundredAlarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)
        val newAlbumAlarmIntent: Intent = Intent(ALARM_START_ACTION).apply {
            component = componentName
            putExtra("type", AlarmReceiver.NEW_ALBUM)
        }
        val newAlbumPendingIntent: PendingIntent = PendingIntent.getBroadcast(this,
            222,
            newAlbumAlarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        mAlarmManager = (getSystemService(Context.ALARM_SERVICE) as AlarmManager).apply {
            Log.d(TAG, "alarm start!!!!")
            setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, TOP_HUNDRED_INTERVAL_TIME_MILLIS, topHundredPendingIntent)
            setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, NEW_ALBUM_INTERVAL_TIME_MILLIS, newAlbumPendingIntent)
        }

        finish() // Finish this activity right after start alarm
    }
}
