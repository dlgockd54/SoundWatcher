package com.example.hclee.soundwatcher.SoundWatchService

import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

/**
 * Created by hclee on 2019-04-23.
 */

class TopHundredPullingThread: Thread() {
    private val TAG: String = TopHundredPullingThread::class.java.simpleName
    private val CHART_URL: String = "https://www.melon.com/chart/index.htm"
    private val CHART_QUERY: String = "div.ellipsis>span>a"
    private var count: Int = 1

    override fun run() {
        Log.d(TAG, "run()")

        showTopHundred()
    }

    private fun showTopHundred() {
        Log.d(TAG, "showTopHundred()")

        val document: Document = Jsoup.connect(CHART_URL).get()
        val elements: Elements = document.select(CHART_QUERY)

        for(element in elements) {
            val song: String = element.text()

            Log.d(TAG, "${count++}번째 $song")
        }
    }
}