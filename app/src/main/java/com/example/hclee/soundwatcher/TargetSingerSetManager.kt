package com.example.hclee.soundwatcher

import android.util.Log

/**
 * Created by hclee on 2019-04-25.
 */

object TargetSingerSetManager {
    private val TAG: String = TargetSingerSetManager::class.java.simpleName
    private val mTargetSingerSet: HashSet<String> = HashSet<String>()

    init {
        addTargetSinger("트와이스")
        addTargetSinger("마인드유")
    }

    fun addTargetSinger(singer: String) {
        mTargetSingerSet.add(singer)
    }

    fun removeTargetSinger(singer: String) {
        mTargetSingerSet.remove(singer)
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