package com.aizh.workbridge.videoselector

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.aizh.workbridge.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class VideoInfoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), View.OnClickListener {

    private val thumbnailIv: ImageView
    private val durationTv: TextView
    private val sizeTv: TextView
    private val checkBox: CheckBox
    private var videoInfo: VideoInfo? = null
    private var videoIndex: Int = -1
    private var isVideoSelected = false
    private var isVideoSelectAble = false

    var selectListener: OnSelectListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.video_info, this)
        thumbnailIv = findViewById(R.id.iv_thumbnail)
        durationTv = findViewById(R.id.tv_duration)
        sizeTv = findViewById(R.id.tv_size)
        checkBox = findViewById(R.id.checkbox)
        thumbnailIv.setOnClickListener(this)
        checkBox.setOnClickListener(this)
    }

    fun bindData(info: VideoInfo, isSelected: Boolean, isSelectAble: Boolean, index: Int) {
        videoInfo = info
        isVideoSelected = isSelected
        isVideoSelectAble = isSelectAble
        videoIndex = index

        sizeTv.text = info.size.toString()
        durationTv.text = info.duration.toString()
        checkBox.isChecked = isSelected
        checkBox.isClickable = isSelectAble
        thumbnailIv.isClickable = isSelectAble
        if (isSelectAble) {
            alpha = 1f
        } else {
            alpha = 0.5f
        }
        loadImage(info)
    }

    private fun loadImage(info: VideoInfo) {
        val uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, info.id)
        val requestOptions = RequestOptions()
        Glide.with(context).load(uri).apply(requestOptions).into(thumbnailIv)
    }

    override fun onClick(v: View?) {
        if (v == thumbnailIv) {
            videoInfo?.let { PreviewUtil.startPreview(context, it) }
        } else if (v == checkBox) {
            if (isVideoSelectAble) {
                selectListener?.onSelected(videoIndex, checkBox.isChecked)
            } else {
                Toast.makeText(context, "不可点击", Toast.LENGTH_SHORT).show()
            }
        }
    }

    interface OnSelectListener {
        fun onSelected(index: Int, isSelected: Boolean)
    }

}