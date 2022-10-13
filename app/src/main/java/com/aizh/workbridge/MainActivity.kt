package com.aizh.workbridge

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.aizh.workbridge.videoselector.Util
import com.aizh.workbridge.videoselector.VideoSelectActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.v("HXL", "MAIN START")
        val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            requestPermissions(permission, 1)
        }

        val intent = Intent(this, VideoSelectActivity::class.java)
        startActivity(intent)
    }
}