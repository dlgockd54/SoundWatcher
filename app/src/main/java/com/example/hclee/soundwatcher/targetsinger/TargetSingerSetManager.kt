package com.example.hclee.soundwatcher.targetsinger

import android.util.Log
import java.util.*

/**
 * Created by hclee on 2019-04-25.
 */

object TargetSingerSetManager {
    private val TAG: String = TargetSingerSetManager::class.java.simpleName
    private val mTargetSingerSet: HashSet<String> = HashSet<String>()

    fun addTargetSinger(singer: String): Boolean {
        var isAdded: Boolean = false
        Log.d(TAG, "addTargetSinger()")

        if(mTargetSingerSet.add(singer)) {
            Log.d(TAG, "adding target singer succeed")

            isAdded = true
        }
        else {
            Log.d(TAG, "adding target singer failed")
        }

        return isAdded
    }

    fun removeTargetSinger(singer: String): Boolean {
        var isRemoved: Boolean = false

        if(mTargetSingerSet.remove(singer)) {
            isRemoved = true
        }

        return isRemoved
    }

    fun containsSinger(singer: String): Boolean {
        val iterator = mTargetSingerSet.iterator()
        var contains: Boolean = false

        while(iterator.hasNext()) {
            val key: String = iterator.next()

            Log.d(TAG, "key: $key, singer: $singer")

            // Parameter "singer" which is pulled from Melon chart would be something like
            // "TWICE (트와이스)" but key "트와이스".
            // In this case, key.contains(singer) doesn't not works.
            if(singer.contains(key)) {
                contains = true

                break
            }
        }

        return contains
    }
}