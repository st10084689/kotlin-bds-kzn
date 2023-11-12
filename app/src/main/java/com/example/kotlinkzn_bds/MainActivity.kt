package com.example.kotlinkzn_bds

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    lateinit var homeIcon: ImageView
    lateinit var shoppingIcon: ImageView
    lateinit var eventIcon: ImageView
    lateinit var aboutIcon: ImageView
    lateinit var homeUnderLine: ImageView
    lateinit var shoppingUnderline: ImageView
    lateinit var eventUnderline: ImageView
    lateinit var aboutUnderline: ImageView
    lateinit var chosenUnderline: ImageView
    lateinit var homeText: TextView
    lateinit var shoppingText: TextView
    lateinit var eventText: TextView
    lateinit var aboutText: TextView
    lateinit var drawerLayout: DrawerLayout
    lateinit var homeBtn: RelativeLayout
    lateinit var shoppingBtn: RelativeLayout
    lateinit var eventsBtn: RelativeLayout
    lateinit var aboutBtn: RelativeLayout

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        init()
    }

    private fun init() {
        homeBtn = findViewById(R.id.home_relative)
        shoppingBtn = findViewById(R.id.shopping_relative)
        eventsBtn = findViewById(R.id.event_relative)
        aboutBtn = findViewById(R.id.about_relative)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)

        homeIcon = findViewById(R.id.home_icon)
        shoppingIcon = findViewById(R.id.shopping_icon)
        eventIcon = findViewById(R.id.event_icon)
        aboutIcon = findViewById(R.id.about_icon)

        homeText = findViewById(R.id.home_nav_txt)
        shoppingText = findViewById(R.id.shopping_nav_txt)
        eventText = findViewById(R.id.event_nav_txt)
        aboutText = findViewById(R.id.about_nav_txt)

        homeUnderLine = findViewById(R.id.home_underline)
        shoppingUnderline = findViewById(R.id.shopping_underline)
        eventUnderline = findViewById(R.id.event_underline)
        aboutUnderline = findViewById(R.id.about_underline)
        chosenUnderline = homeUnderLine

        homeBtn.setOnClickListener {
            loadFragment(HomeFragment())
            Handler().postDelayed({
                changeNavBarColors(
                    R.drawable.home_icon_selected,
                    homeIcon,
                    homeText,
                    homeUnderLine
                )
                animateUnderline(chosenUnderline, homeUnderLine)
                chosenUnderline = homeUnderLine
            }, 250)
        }

        shoppingBtn.setOnClickListener {
            loadFragment(ShoppingFragment())
            Handler().postDelayed({
                changeNavBarColors(
                    R.drawable.shopping_icon_selected,
                    shoppingIcon,
                    shoppingText,
                    shoppingUnderline
                )
                animateUnderline(chosenUnderline, shoppingUnderline)
                chosenUnderline = shoppingUnderline
            }, 250)
        }

        eventsBtn.setOnClickListener {
            loadFragment(EventsFragment())
            Handler().postDelayed({
                changeNavBarColors(
                    R.drawable.events_icon_selected,
                    eventIcon,
                    eventText,
                    eventUnderline
                )
                animateUnderline(chosenUnderline, eventUnderline)
                chosenUnderline = eventUnderline
            }, 250)
        }

        aboutBtn.setOnClickListener {
            loadFragment(AboutUsFragment())
            Handler().postDelayed({
                changeNavBarColors(
                    R.drawable.account_icon_selected,
                    aboutIcon,
                    aboutText,
                    aboutUnderline
                )
                animateUnderline(chosenUnderline, aboutUnderline)
                chosenUnderline = aboutUnderline
            }, 250)
        }

        changeNavBarColors(R.drawable.home_icon_selected, homeIcon, homeText, homeUnderLine)
        loadFragment(HomeFragment())
    }

    private fun loadFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.content_fragment, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun changeNavBarColors(
        drawableValue: Int,
        image: ImageView,
        text: TextView,
        underLine: ImageView
    ) {
        homeIcon.setImageResource(R.drawable.home_icon)
        shoppingIcon.setImageResource(R.drawable.shopping_bag_icon)
        aboutIcon.setImageResource(R.drawable.about_us_icon)
        eventIcon.setImageResource(R.drawable.events_icon)

        val baseColor = R.color.grey
        val orange = R.color.orange

        homeText.setTextColor(resources.getColor(baseColor))
        shoppingText.setTextColor(resources.getColor(baseColor))
        aboutText.setTextColor(resources.getColor(baseColor))
        eventText.setTextColor(resources.getColor(baseColor))

        homeUnderLine.setBackgroundColor(Color.parseColor("#00FFFFFF"))
        shoppingUnderline.setBackgroundColor(Color.parseColor("#00FFFFFF"))
        eventUnderline.setBackgroundColor(Color.parseColor("#00FFFFFF"))
        aboutUnderline.setBackgroundColor(Color.parseColor("#00FFFFFF"))

        image.setImageResource(drawableValue)
        underLine.setBackgroundResource(R.drawable.chosen_underline)
        text.setTextColor(resources.getColor(orange))
    }

    private fun animateUnderline(chosenUnderline: ImageView, targetUnderline: ImageView) {
        val startPosition = IntArray(2)
        val endPosition = IntArray(2)

        chosenUnderline.getLocationInWindow(startPosition)
        targetUnderline.getLocationInWindow(endPosition)

        val animation = TranslateAnimation(
            (startPosition[0] - endPosition[0]).toFloat(), 0f,
            (startPosition[1] - endPosition[1]).toFloat(), 0f
        )

        Log.d("Animation", "Start position: (${startPosition[0]}, ${startPosition[1]})")
        Log.d("Animation", "End position: (${endPosition[0]}, ${endPosition[1]})")

        Log.d(
            "Animation",
            "TranslationX: " + (endPosition[0] - startPosition[0]) + ", TranslationY: " + (endPosition[1] - startPosition[1])
        )

        animation.duration = 500
        animation.fillAfter = true

        targetUnderline.startAnimation(animation)
    }

    override fun onBackPressed() {
    }
}
