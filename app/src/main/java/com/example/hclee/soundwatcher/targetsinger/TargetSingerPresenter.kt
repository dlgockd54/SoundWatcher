package com.example.hclee.soundwatcher.targetsinger

import android.util.Log
import java.util.*

/**
 * Created by hclee on 2019-04-27.
 */

class TargetSingerPresenter(private val mView: TargetSingerContract.View): TargetSingerContract.Presenter {
    private val TAG: String = TargetSingerPresenter::class.java.simpleName

    val mTargetSingerList: LinkedList<TargetSingerData> = LinkedList<TargetSingerData>()

    override fun addTargetSinger(singer: String): Boolean {
        Log.d(TAG, "addTargetSinger()")

        return addTargetSingerToManager(singer)
    }

    private fun addTargetSingerToManager(singer: String): Boolean {
        var isAdded: Boolean = false

        Log.d(TAG, "addTargetSingerToManager()")

        isAdded = TargetSingerSetManager.addTargetSinger(singer)

        if(isAdded) {
            addTargetSingerToList(singer)
        }

        return isAdded
    }

    private fun addTargetSingerToList(singer: String) {
        Log.d(TAG, "addTargetSingerToList()")

        if(!singer.isEmpty()) {
            mTargetSingerList.add(TargetSingerData(singer))
        }

        mView.updateTargetSingerAdapter()
    }

    override fun removeTargetSinger(singer: String): Boolean {
        Log.d(TAG, "removeTargetSinger()")

        return removeTargetSingerFromManager(singer)
    }

    private fun removeTargetSingerFromManager(singer: String): Boolean {
        var isRemoved: Boolean = false

        Log.d(TAG, "removeTargetSingerFromManager()")

        isRemoved = TargetSingerSetManager.removeTargetSinger(singer)

        if(isRemoved) {
            removeTargetSingerFromList(singer)
        }

        return isRemoved
    }

    private fun removeTargetSingerFromList(singer: String) {
        Log.d(TAG, "removeTargetSingerFromList()")

        for(element in mTargetSingerList) {
            if(element.mTargetSinger.contains(singer)) {
                mTargetSingerList.remove(element)
            }
        }

        mView.updateTargetSingerAdapter()
    }
}