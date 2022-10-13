package com.aizh.workbridge.videoselector

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class VideoSelectAdapter: RecyclerView.Adapter<VideoSelectAdapter.InnerViewHolder>(),
    VideoInfoView.OnSelectListener {

    private val videos = mutableListOf<VideoInfo>()
    private var selectedFolder: VideoFolderInfo? = null
    private var selectedIndex = -1

    private var showingFolderInfo: VideoFolderInfo? = null

    fun update(folderInfo: VideoFolderInfo) {
        Log.v("HXL", "update:$videos")
        showingFolderInfo = folderInfo
        this.videos.clear()
        this.videos.addAll(folderInfo.videos)
        notifyDataSetChanged()
    }

    class InnerViewHolder(val infoView: VideoInfoView) : RecyclerView.ViewHolder(infoView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val infoView = VideoInfoView(parent.context)
        return InnerViewHolder(infoView)
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        val isSelectedItem = showingFolderInfo == selectedFolder && selectedIndex == position
        //是选中的item或者还没有选中的item
        val isSelectAble = isSelectedItem || selectedIndex == -1
        holder.infoView.bindData(videos[position], isSelectedItem, isSelectAble, position)
        holder.infoView.selectListener = this
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onSelected(index: Int, isSelected: Boolean) {
        if (!isSelected) {
            selectedFolder = null
            selectedIndex = -1
        } else {
            selectedFolder = showingFolderInfo
            selectedIndex = index
        }
        notifyDataSetChanged()
    }

    fun getSelectVideo() : VideoInfo? {
        if (selectedFolder == null || selectedIndex == -1) {
            return null
        }
        return selectedFolder?.videos?.get(selectedIndex)
    }

}