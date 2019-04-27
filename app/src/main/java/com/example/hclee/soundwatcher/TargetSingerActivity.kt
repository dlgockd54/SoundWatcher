package com.example.hclee.soundwatcher

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class TargetSingerActivity : AppCompatActivity() {
    private val TAG: String = TargetSingerActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_target_singer)

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
