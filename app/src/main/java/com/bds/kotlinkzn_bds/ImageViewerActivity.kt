package com.bds.kotlinkzn_bds

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


class ImageViewerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_viewer_acitvity)

        val imagePath = intent.getStringExtra("IMAGE_PATH")


        val imageView = findViewById<ImageView>(R.id.imageView)
        Glide.with(imageView)
            .load(imagePath)
            .into(imageView)

    }
}