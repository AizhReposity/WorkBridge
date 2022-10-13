package com.aizh.workbridge.videoselector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aizh.workbridge.R

class VideoSelectActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var folderName: TextView
    private lateinit var layoutManager: GridLayoutManager
    private var folderIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("HXL", "VideoSelectActivity onCreate")
        setContentView(R.layout.activity_video_select)
        recyclerView = findViewById(R.id.list)
        folderName = findViewById(R.id.folder_name)
        layoutManager = GridLayoutManager(this, 4)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(VideoDecoration())
        val adapter = VideoSelectAdapter()
        recyclerView.adapter = adapter
        val folders = Util.getVideo(this)

        adapter.update(folders[folderIndex])
        folderName.text = folders[0].folderName

        folderName.setOnClickListener {
            folderIndex = (++folderIndex) % folders.size
            adapter.update(folders[folderIndex])
            folderName.text = folders[folderIndex].folderName
        }
    }

}