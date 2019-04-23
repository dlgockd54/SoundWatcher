package com.example.hclee.soundwatcher.SoundWatchService

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class SoundWatchService : Service() {
    private val TAG: String = SoundWatchService::class.java.simpleName

    override fun onCreate() {
        super.onCreate()

        Log.d(TAG, "onCreate()")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        Log.d(TAG, "onStartCommand()")

        showTopHundred()

        return START_STICKY
    }

    private fun showTopHundred() {
        val url: String = "https://www.melon.com/chart/index.htm"
        val cssQuery: String = "div.ellipsis>span>a"

        tryJsoup(url, cssQuery)
    }

    private fun tryJsoup(url: String, cssQuery: String) {
        var count: Int = 1

        Thread(Runnable {
            val document: Document = Jsoup.connect(url).get()
            val elements: Elements = document.select(cssQuery)

            for(element in elements) {
                val song: String = element.text()

                Log.d(TAG, "${count++}번째 $song")
            }
        }).start()
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy()")
    }
}
