package com.example.hclee.soundwatcher.watchservice.newalbumwatchservice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
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
 * Created by hclee on 2019-04-26.
 */

class NewAlbumPullingThread(private val mContext: Context, private val mListener: OnPullFinishListener)
    : Thread() {
    private val TAG: String = NewAlbumPullingThread::class.java.simpleName
    private val NEW_ALBUM_URL: String = "https://www.melon.com/new/album/index.htm"
    private val ALBUM_SINGER_QUERY: String = "div.info>span.ellipsis>a"
    private val ALBUM_TITLE_QUERY: String = "div.info>span.vdo>a"

    private var mNotificationManager: NotificationManager? = null
    private var mNotificationBuilder: NotificationCompat.Builder? = null

    companion object {
        // Notification id should not depends on each thread object.
        // Because even service make new thread to pull chart data, then notify notification,
        // every notification should has different notification id.
        private var mNotificationId: Int = -1
    }

    override fun run() {
        mNotificationManager = mNotificationManager ?: (mContext.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager)
        mNotificationBuilder = mNotificationBuilder ?: NotificationCompat.Builder(mContext, "default2")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setDefaults(Notification.DEFAULT_ALL)
            .setContentTitle("SoundWatcher")
            .setAutoCancel(true)
//            .setSound()

        pullNewAlbum()
    }

    private fun pullNewAlbum() {
        Log.d(TAG, "pullNewAlbum()")

        val document: Document = Jsoup.connect(NEW_ALBUM_URL).get()
        val singerElements: Elements = document.select(ALBUM_SINGER_QUERY)
        val titleElements: Elements = document.select(ALBUM_TITLE_QUERY)

        Log.d(TAG, "singerElements.size: ${singerElements.size}")
        Log.d(TAG, "titleElements.size: ${titleElements.size}")

//        if(singerElements.size > 0 && titleElements.size > 0) {
        if(singerElements.size > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mNotificationManager?.createNotificationChannel(
                    NotificationChannel(
                        "default2", "기본 채널2",
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                )
            }

            for (i in 0 until singerElements.size) {
                val singerElement: Element = singerElements[i]
//                val titleElement: Element = titleElements[i]
                val singer: String = singerElement.text()
//                val title: String = titleElement.text()

//                Log.d(TAG, "singer: $singer, title: $title")

                if (TargetSingerSetManager.containsSinger(singer)) {
                    Log.d(TAG, "contains!")

                    makeNotification(singer, null)
                }
            }
        }

        mListener.onPullFinish()
    }

    private fun makeNotification(singer: String, title: String?) {
        val targetUrl: String = "https://www.youtube.com/results?search_query=$singer"
        val intent: Intent = Intent(Intent.ACTION_VIEW, Uri.parse(targetUrl))
        val pendingIntent: PendingIntent = PendingIntent.getActivity(mContext, 222, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        mNotificationBuilder?.setContentText("${singer}의 새 앨범이 발매 되었습니다.")
            ?.setContentIntent(pendingIntent)
        mNotificationManager?.notify(NewAlbumPullingThread::class.java.simpleName, NewAlbumPullingThread.mNotificationId--, mNotificationBuilder?.build())
    }
}