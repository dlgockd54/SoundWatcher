package com.example.hclee.soundwatcher.watchservice

import android.app.Service
import android.util.Log
import com.example.hclee.soundwatcher.watchservice.tophundredwatchservice.TopHundredWatchService

/**
 * Created by hclee on 2019-04-24.
 */

class OnPullFinishListenerImpl(private val service: Service):
    OnPullFinishListener {
    private val TAG: String = OnPullFinishListenerImpl::class.java.simpleName

    override fun onPullFinish() {
        Log.d(TAG, "onPullFinish()")

        service.stopSelf()
    }
}