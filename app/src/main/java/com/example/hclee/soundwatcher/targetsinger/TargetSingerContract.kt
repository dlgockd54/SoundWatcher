package com.example.hclee.soundwatcher.targetsinger

/**
 * Created by hclee on 2019-04-27.
 */

interface TargetSingerContract {
    interface View {
        var mPresenter: Presenter

        fun updateTargetSingerAdapter()
    }

    interface Presenter {
        fun addTargetSinger(singer: String): Boolean
        fun removeTargetSinger(singer: String): Boolean
    }
}