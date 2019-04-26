package com.example.hclee.soundwatcher.WatchService.Top100WatchService

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log

/**
 * Created by hclee on 2019-04-23.
 */

class SoundWatchHandler(looper: Looper): Handler(looper) {
    private val TAG: String = SoundWatchHandler::class.java.simpleName

    init {
        Log.d(TAG, "init")
    }

    companion object {
        const val MSG_TOP_HUNDRED: Int = 222
        const val MSG_NEW_ALBUM: Int = 333
    }

    override fun handleMessage(msg: Message?) {
        Log.d(TAG, "handleMessage()")
        Log.d(TAG, "what: ${msg?.what}")

        var obj: Any

        when(msg?.what) {
            MSG_TOP_HUNDRED -> {
                msg.obj.let {
                    obj = it

                    Log.d(TAG, "${obj as String} found!")
                }
            }
            MSG_NEW_ALBUM -> {

            }
            else -> {

            }
        }
    }
}