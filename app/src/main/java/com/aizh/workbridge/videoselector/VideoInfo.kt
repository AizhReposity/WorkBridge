package com.aizh.workbridge.videoselector

data class VideoInfo(val path: String, val id: Long, val duration: Long, val size: Long) {

    override fun toString(): String {
        return "VideoInfo(path='$path', id=$id, duration=$duration, size=$size)"
    }
}