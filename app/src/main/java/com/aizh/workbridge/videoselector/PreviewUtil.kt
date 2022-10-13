package com.aizh.workbridge.videoselector

import android.content.Context
import android.content.Intent
import android.net.Uri

object PreviewUtil {

    fun startPreview(context: Context, info: VideoInfo) {
        val intent = Intent(Intent.ACTION_VIEW)
        val uri = Uri.parse(info.path)
        intent.setDataAndType(uri, "video/*")
        context.startActivity(intent)
    }
}