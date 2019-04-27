package com.example.hclee.soundwatcher.watchservice.tophundredwatchservice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.example.hclee.soundwatcher.R
import com.example.hclee.soundwatcher.targetsinger.TargetSingerSetManager
import com.example.hclee.soundwatcher.watchservice.OnPullFinishListener
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

/**
 * Created by hclee on 2019-04-23.
 */

/**
 * Pull top hundred chart from Melon Top100.
 */
class TopHundredPullingThread(private val mContext: Context, private val mListener: OnPullFinishListener)
    : Thread() {
    private val TAG: String = TopHundredPullingThread::class.java.simpleName
    private val CHART_URL: String = "https://www.melon.com/chart/index.htm"
    private val CHART_TITLE_QUERY: String = "div.rank01>span>a"
    private val CHART_SINGER_QUERY: String = "div.rank02>span>a"

    private var mNotificationManager: NotificationManager? = null
    private var mNotificationBuilder: NotificationCompat.Builder? = null
    private var mPowerManager: PowerManager? = null
    private var mWakeLock: PowerManager.WakeLock? = null

    companion object {
        // Notification id should not depends on each thread object.
        // Because even service make new thread to pull chart data, then notify notification,
        // every notification should has different notification id.
        private var mNotificationId: Int = 1
    }

    private fun acquireWakeLock() {
        mPowerManager = mPowerManager ?: (mContext.getSystemService(Context.POWER_SERVICE) as PowerManager)
        mPowerManager?.let {
            mWakeLock = mWakeLock ?: it.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG)
        }

        mWakeLock?.acquire(3000)
    }

    private fun releaseWakeLock() {
        mWakeLock?.let {
            if(it.isHeld) {
                mWakeLock?.release()
            }
        }
    }

    override fun run() {
        Log.d(TAG, "run()")

        mNotificationManager = mNotificationManager ?: (mContext.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager)
        mNotificationBuilder = mNotificationBuilder ?: NotificationCompat.Builder(mContext, "default")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // 아이콘 설정하지 않으면 오류남
            .setDefaults(Notification.DEFAULT_ALL)
            .setContentTitle("SoundWatcher") // 제목 설정
            .setAutoCancel(true)
//            .setSound()

        pullTopHundred()
    }

    private fun pullTopHundred() {
        Log.d(TAG, "pullTopHundred()")

        val document: Document = Jsoup.connect(CHART_URL).get()
        val singerElements: Elements = document.select(CHART_SINGER_QUERY)
        val titleElements: Elements = document.select(CHART_TITLE_QUERY)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager?.createNotificationChannel(NotificationChannel("default", "기본 채널",
                NotificationManager.IMPORTANCE_DEFAULT))
        }

        for(i in 0 until titleElements.size) {
            val titleElement: Element = titleElements[i]
            val singerElement: Element = singerElements[i]
            val title: String = titleElement.text()
            val singer: String = singerElement.text()

            Log.d(TAG, "singer: $singer, title: $title")

            if(TargetSingerSetManager.containsSinger(singer)) {
                Log.d(TAG, "contains!")

                makeNotification(singer, title)
            }
        }

        mListener.onPullFinish()
    }

    private fun makeNotification(singer: String, title: String) {
        val targetUrl: String = "https://www.youtube.com/results?search_query=$singer+$title"
        val intent: Intent = Intent(Intent.ACTION_VIEW, Uri.parse(targetUrl))
        val pendingIntent: PendingIntent = PendingIntent.getActivity(mContext, 222, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        mNotificationBuilder?.setContentText("멜론 Top100 차트에 $singer - $title 포함되어 있습니다.")
            ?.setContentIntent(pendingIntent)
        mNotificationManager?.notify(mNotificationId++, mNotificationBuilder?.build())
    }
}