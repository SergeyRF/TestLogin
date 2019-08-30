package com.retivov.testregistry.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.FrameLayout
import com.retivov.testregistry.R

class ProgressBarClickHunter @JvmOverloads
constructor(context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.progress_bar_click_hunter, this, true)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return true
    }
}