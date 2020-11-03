package com.ovrbach.mvolvochallenge.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ovrbach.mvolvochallenge.R
import com.ovrbach.mvolvochallenge.app.feature.search.SearchAlbumFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}