package com.example.hclee.soundwatcher.targetsinger

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.example.hclee.soundwatcher.R
import kotlinx.android.synthetic.main.activity_target_singer.*

class TargetSingerActivity : AppCompatActivity(), TargetSingerContract.View, View.OnClickListener {
    private val TAG: String = TargetSingerActivity::class.java.simpleName

    override lateinit var mPresenter: TargetSingerContract.Presenter

    private lateinit var mAddTargetSingerButton: Button
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: TargetSingerAdapter
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mActivityLayout: LinearLayout
    lateinit var mAddTargetSingerEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_target_singer)

        Log.d(TAG, "onCreate()")

        init()
    }

    private fun init() {
        mActivityLayout = layout_target_singer
        mAddTargetSingerButton = btn_add_singer
        mAddTargetSingerEditText = et_add_singer
        mPresenter = TargetSingerPresenter(this)
        mLayoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        mRecyclerView = rv_target_singer.apply {
            setHasFixedSize(true)
            layoutManager = mLayoutManager
        }

        updateTargetSingerAdapter()

        mActivityLayout.setOnClickListener(this)
        mAddTargetSingerButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.layout_target_singer -> {
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(mAddTargetSingerEditText.windowToken, 0)
            }
            R.id.btn_add_singer -> {
                val singer: String = mAddTargetSingerEditText.text.toString()

                Log.d(TAG, "singer: $singer")

                if(!singer.isEmpty()) {
                    if(mPresenter.addTargetSinger(singer)) {
                        Toast.makeText(this, "추가된 가수는 일정 시간이 지난 후부터 탐색 대상에 포함됩니다.",
                            Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(this, "이미 탐색 대상에 포함 되어있는 가수 입니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    Log.d(TAG, "target singer empty!")
                }
            }
        }
    }

    override fun updateTargetSingerAdapter() {
        mAdapter = TargetSingerAdapter((mPresenter as TargetSingerPresenter).mTargetSingerList)
        mRecyclerView.adapter = mAdapter
    }

    override fun onBackPressed() {
        super.onBackPressed()

        Log.d(TAG, "onBackPressed()")

        finish()
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy()")
    }
}
