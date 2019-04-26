package com.example.hclee.soundwatcher.WatchService.Top100WatchService

import android.util.Log

/**
 * Created by hclee on 2019-04-24.
 */

class OnPullFinishListenerImpl(private val service: SoundWatchService): OnPullFinishListener {
    private val TAG: String = OnPullFinishListenerImpl::class.java.simpleName

    override fun onPullFinish() {
        Log.d(TAG, "onPullFinish()")

        service.stopSelf()
    }
}