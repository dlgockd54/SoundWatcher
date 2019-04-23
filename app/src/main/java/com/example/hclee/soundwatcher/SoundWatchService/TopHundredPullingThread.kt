package com.example.hclee.soundwatcher.SoundWatchService

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.example.hclee.soundwatcher.R
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
class TopHundredPullingThread(private val mContext: Context): Thread() {
    private val TAG: String = TopHundredPullingThread::class.java.simpleName
    private val CHART_URL: String = "https://www.melon.com/chart/index.htm"
    private val CHART_QUERY: String = "div.ellipsis>span>a"

    private lateinit var mNotificationManager: NotificationManager
    private lateinit var mNotificationBuilder: NotificationCompat.Builder

    companion object {
        // Notification id should not depends on each thread object.
        // Because even service make new thread to pull chart data, then notify notification,
        // every notification should has different notification id.
        private var mNotificationId: Int = 0
    }

    override fun run() {
        Log.d(TAG, "run()")

        mNotificationManager = (mContext.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager)
        mNotificationBuilder = NotificationCompat.Builder(mContext, "default")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // 아이콘 설정하지 않으면 오류남
            .setDefaults(Notification.DEFAULT_ALL)
            .setContentTitle("SoundWatcher") // 제목 설정
            .setAutoCancel(true)
//            .setSound()

        showTopHundred()
    }

    private fun showTopHundred() {
        var count: Int = 1

        Log.d(TAG, "showTopHundred()")

        val document: Document = Jsoup.connect(CHART_URL).get()
        val elements: Elements = document.select(CHART_QUERY)
        val target: String = "장범준"

        mNotificationBuilder.setContentText("멜론 Top100 차트에 $target _위로 포함되어 있습니다.")
//            .setContentIntent()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager.createNotificationChannel(NotificationChannel("default", "기본 채널",
                NotificationManager.IMPORTANCE_DEFAULT))
        }

        for(element in elements) {
            val music: String = element.text()

            Log.d(TAG, "${count++}번째 $music")

            if(music.contains(target)) {
                Log.d(TAG, "contains!")

                mNotificationManager.notify(mNotificationId++, mNotificationBuilder.build())
            }
        }
    }
}