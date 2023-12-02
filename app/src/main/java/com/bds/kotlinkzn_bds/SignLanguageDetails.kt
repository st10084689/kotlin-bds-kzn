package com.bds.kotlinkzn_bds

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SignLanguageDetails : AppCompatActivity() {

    private lateinit var signTitle: TextView
    private lateinit var signImage: ImageView
    private lateinit var backPressedBtn: RelativeLayout

    private var title: String? = null
    private var images: String? = null

    companion object {
        private const val TAG = "SignDetails"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_language_details)
        init()
    }

    private fun init() {
        val getPosition: Intent = intent
        title = getPosition.getStringExtra("signTitle")
        images = Utility.getImage()


        backPressedBtn = findViewById(R.id.on_back_button)

        // Setting the textViews
        signTitle = findViewById(R.id.SignDetailsTitle)
        signImage = findViewById(R.id.sign_image)

        Glide.with(signImage)
            .load(images)
            .into(signImage)

        signTitle.text = title

//        signImage.setOnClickListener {
//            val intent = Intent(this@EventDetails, ImageViewerActivity::class.java)
//            intent.putExtra("IMAGE_PATH", images)
//            startActivity(intent)
//        }

        backPressedBtn.setOnClickListener {
            onBackPressed()
        }
    }
}