package com.example.hclee.soundwatcher.SoundWatchService

import android.os.Looper
import android.os.Message
import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

/**
 * Created by hclee on 2019-04-23.
 */

/**
 * Pull top hundred chart from Melon.
 * Use main looper for handle message.
 */
class TopHundredPullingThread: Thread() {
    private val TAG: String = TopHundredPullingThread::class.java.simpleName
    private val CHART_URL: String = "https://www.melon.com/chart/index.htm"
    private val CHART_QUERY: String = "div.ellipsis>span>a"
    private var count: Int = 1

    private lateinit var mHandler: SoundWatchHandler

    override fun run() {
        Log.d(TAG, "run()")

        mHandler = SoundWatchHandler(Looper.getMainLooper())

        showTopHundred()
    }

    private fun showTopHundred() {
        Log.d(TAG, "showTopHundred()")

        val document: Document = Jsoup.connect(CHART_URL).get()
        val elements: Elements = document.select(CHART_QUERY)

        for(element in elements) {
            val song: String = element.text()
            val isContainsString: Boolean = song.contains("장범준")

            Log.d(TAG, "${count++}번째 $song")

            if(isContainsString) {
                Log.d(TAG, "contains!")

                val message: Message = mHandler.obtainMessage(SoundWatchHandler.MSG_TOP_HUNDRED, "장범준")

                mHandler.sendMessage(message)
            }
            else {
                Log.d(TAG, "not contains!")
            }
        }
    }
}