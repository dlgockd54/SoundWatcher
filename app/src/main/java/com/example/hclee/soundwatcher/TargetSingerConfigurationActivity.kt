package com.example.hclee.soundwatcher

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class TargetSingerConfigurationActivity : AppCompatActivity() {
    private val TAG: String = TargetSingerConfigurationActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_target_singer_configuration)

        Log.d(TAG, "onCreate()")
    }

    override fun onBackPressed() {
        super.onBackPressed()

        Log.d(TAG, "onBackPressed()")

        finish()
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy()")
    }
}
