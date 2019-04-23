package com.example.hclee.soundwatcher

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.hclee.soundwatcher.SoundWatchService.SoundWatchService

class MainActivity : AppCompatActivity() {
    private val TAG: String = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main) // Not to show activity

        Log.d(TAG, "onCreate()")

        val intent: Intent = Intent(this, SoundWatchService::class.java)
        startService(intent)

        finish() // Finish this activity right after start service
    }
}
