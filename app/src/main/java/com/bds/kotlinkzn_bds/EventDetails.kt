package com.bds.kotlinkzn_bds


import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EventDetails : AppCompatActivity() {

    private lateinit var eventTitle: TextView
    private lateinit var eventDescription: TextView
    private lateinit var eventDate: TextView
    private lateinit var eventImage: ImageView
    private lateinit var backPressedBtn: RelativeLayout

    private var title: String? = null
    private var description: String? = null
    private var images: String? = null
    private var date: String? = null
    private var time: String? = null

    companion object {
        private const val TAG = "EventDetails"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        init()
    }

    private fun init() {
        val getPosition: Intent = intent
        title = getPosition.getStringExtra("eventTitle")
        description = getPosition.getStringExtra("eventDescription")
        images = getPosition.getStringExtra("eventImages")
        date = getPosition.getStringExtra("eventDate")
        time = getPosition.getStringExtra("eventTime")

        backPressedBtn = findViewById(R.id.on_back_button)

        // Setting the textViews
        eventTitle = findViewById(R.id.EventDetailsTitle)
        eventDescription = findViewById(R.id.EventDetailsDescription)
        eventDate = findViewById(R.id.event_date)
        eventImage = findViewById(R.id.event_image)

        Glide.with(eventImage)
            .load(images)
            .into(eventImage)

        eventTitle.text = title
        eventDescription.text = description


        eventDate.text = getDate(date)

        eventImage.setOnClickListener {
            val intent = Intent(this@EventDetails, ImageViewerActivity::class.java)
            intent.putExtra("IMAGE_PATH", images)
            startActivity(intent)
        }

        backPressedBtn.setOnClickListener {
            onBackPressed()
        }
    }

    //method to for the date to be formatted
    fun getDate(date:String?): String {
        val inputDateString = date
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
        val dateTime = LocalDateTime.parse(inputDateString, inputFormatter)
        val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return dateTime.format(outputFormatter)//returns the formatted date
    }
}
