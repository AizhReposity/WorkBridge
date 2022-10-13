package com.aizh.workbridge.videoselector

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaMetadataRetriever
import android.provider.MediaStore
import android.util.Log
import java.io.File

object Util {


    @SuppressLint("Range")
    fun getVideo(context: Context) : MutableList<VideoFolderInfo> {
        /*val projVideo = arrayOf(
            MediaStore.MediaColumns._ID,
            MediaStore.Video.Thumbnails.DATA,
            MediaStore.MediaColumns.DURATION,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATE_MODIFIED
            ThumbnailUtils.createVideoThumbnail()

        )*/
        val videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val mContentResolver = context.contentResolver
        val cursor = mContentResolver.query(
            videoUri,
            null,
            MediaStore.Video.Media.MIME_TYPE + " in(?, ?, ?, ?)",
            arrayOf("video/mp4", "video/3gp", "video/avi", "video/rmvb"),
            MediaStore.Video.Media.DATE_MODIFIED + " desc"
        )
        val folderMap = mutableMapOf<String, VideoFolderInfo>()
        val result = mutableListOf<VideoFolderInfo>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                // 获取视频的路径
                val path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
                if (path.isNullOrEmpty()) {
                    continue
                }
                val folderName = getParentFolderName(path)
                if (folderName.isNullOrEmpty()) {
                    continue
                }
                val duration = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                    cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns.DURATION))
                } else {
                    getVideoDuration(path)
                }
                val id = cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
                val size = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE))
                val videoInfo = VideoInfo(path, id, duration, size)

                var videoFolderInfo = folderMap[folderName]
                if (videoFolderInfo == null) {
                    videoFolderInfo = VideoFolderInfo(folderName)
                    folderMap[folderName] = videoFolderInfo
                }
                videoFolderInfo.videos.add(videoInfo)

                // 获取该视频的父路径名
                val dirPath = File(path).parentFile.absolutePath
            }
            cursor.close()
        }
        result.addAll(folderMap.values)
        return result
    }

    private fun getParentFolderName(path: String) : String? {
        val pathArray = path.split("/")
        if (pathArray.size < 2) {
            return null
        }
        return pathArray[pathArray.size - 2]
    }

    private fun getVideoDuration(path: String): Long {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(path)
        val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        return duration?.toLong() ?: 0L
    }
}