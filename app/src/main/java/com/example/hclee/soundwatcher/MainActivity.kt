package com.example.hclee.soundwatcher

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class MainActivity : AppCompatActivity() {
    private val TAG: String = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showTopHundred()
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
}
