package com.example.hclee.soundwatcher.targetsinger

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.RequestManager
import com.example.hclee.soundwatcher.R
import java.util.*

/**
 * Created by hclee on 2019-04-27.
 */

class TargetSingerAdapter(private val mGlideRequestManager: RequestManager,
                          private val mTargetSingerList: LinkedList<TargetSingerData>)
    : RecyclerView.Adapter<TargetSingerAdapter.TargetSingerViewHolder>() {
    private val TAG: String = TargetSingerAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TargetSingerViewHolder {
        val view: View = ((LayoutInflater.from(parent?.context)
            .inflate(R.layout.target_singer_item, parent, false)))
        val viewHolder: TargetSingerViewHolder = TargetSingerViewHolder(view).apply {
            itemView.setOnClickListener {

            }
        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return mTargetSingerList.size
    }

    override fun onBindViewHolder(holder: TargetSingerViewHolder?, position: Int) {
        holder?.let {
            mGlideRequestManager.load(R.raw.watch)
                .circleCrop()
                .into(it.watchImageView)
            it.targetSingerTextView.text = mTargetSingerList[position].mTargetSinger
        }
    }

    class TargetSingerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val watchImageView: ImageView = itemView.findViewById(R.id.iv_target_singer)
        val targetSingerTextView: TextView = itemView.findViewById(R.id.tv_target_singer)
    }
}