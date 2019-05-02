package com.example.hclee.soundwatcher.targetsinger

import android.util.Log
import java.util.*

/**
 * Created by hclee on 2019-04-27.
 */

class TargetSingerPresenter(private val mView: TargetSingerContract.View): TargetSingerContract.Presenter {
    private val TAG: String = TargetSingerPresenter::class.java.simpleName

    val mTargetSingerList: LinkedList<TargetSingerData> = LinkedList<TargetSingerData>()

    override fun addTargetSinger(singer: String) {
        Log.d(TAG, "addTargetSinger()")

        addTargetSingerToManager(singer)
        addTargetSingerToList(singer)
    }

    private fun addTargetSingerToManager(singer: String) {
        Log.d(TAG, "addTargetSingerToManager()")

        TargetSingerSetManager.addTargetSinger(singer)
    }

    private fun addTargetSingerToList(singer: String) {
        Log.d(TAG, "addTargetSingerToList()")

        if(!singer.isEmpty()) {
            mTargetSingerList.add(TargetSingerData(singer))
        }

        mView.updateTargetSingerAdapter()
    }

    override fun removeTargetSinger(singer: String) {
        Log.d(TAG, "removeTargetSinger()")

        removeTargetSingerFromManager(singer)
        removeTargetSingerFromList(singer)
    }

    private fun removeTargetSingerFromManager(singer: String) {
        Log.d(TAG, "removeTargetSingerFromManager()")

        TargetSingerSetManager.removeTargetSinger(singer)
    }

    private fun removeTargetSingerFromList(singer: String) {
        Log.d(TAG, "removeTargetSingerFromList()")

        for(element in mTargetSingerList) {
            if(element.mTargetSinger.contains(singer)) {
                mTargetSingerList.remove(element)
            }
        }
    }
}