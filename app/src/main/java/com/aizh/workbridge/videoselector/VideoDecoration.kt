package com.aizh.workbridge.videoselector

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VideoDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(0, 0, 12, 12)
    }
}