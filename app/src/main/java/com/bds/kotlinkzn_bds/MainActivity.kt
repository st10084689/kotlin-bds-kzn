package com.bds.kotlinkzn_bds



import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.Manifest
import android.content.ActivityNotFoundException
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.TranslateAnimation
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import java.net.URLEncoder
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class MainActivity : AppCompatActivity(),  HomeFragment.FragmentInteractionListener{
    lateinit var homeIcon: ImageView
    override var shoppingIcon: ImageView? = null
    override var eventIcon: ImageView? = null
    lateinit var aboutIcon: ImageView
    lateinit var homeUnderLine: ImageView
    override var shoppingUnderline: ImageView? = null
    override var eventUnderline: ImageView? = null
    lateinit var aboutUnderline: ImageView
    override var chosenUnderline: ImageView? = null
    lateinit var homeText: TextView
    override var shoppingText: TextView? = null
    override var eventText: TextView? = null
    lateinit var aboutText: TextView
    lateinit var drawerLayout: DrawerLayout
    lateinit var homeBtn: RelativeLayout
    lateinit var shoppingBtn: RelativeLayout
    lateinit var eventsBtn: RelativeLayout
    lateinit var aboutBtn: RelativeLayout
    lateinit var signLanguageBtn : RelativeLayout
    lateinit var navSliderBtn : ImageButton
    private val REQUEST_NOTIFICATION_PERMISSION = 1
    var closestEvent:Event? = null
    lateinit var  signLanguageUnderline: ImageView



    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        init()
        createNotificationChannel()



        }

    private fun init() {
        homeBtn = findViewById(R.id.home_relative)
        shoppingBtn = findViewById(R.id.shopping_relative)
        eventsBtn = findViewById(R.id.event_relative)
        aboutBtn = findViewById(R.id.about_relative)
        signLanguageBtn = findViewById(R.id.signlanguageBtn)
        navSliderBtn = findViewById(R.id.nav_slider)

        navSliderBtn.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }

        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.menu_contact_us -> {

                    openWhatsApp("+27681872167")
                    true
                }

                else -> false
            }
        }
        val navBarbackground = findViewById<ImageView>(R.id.NavBarBottom)

        val isTablet = resources.getBoolean(R.bool.is_tablet)

        if (isTablet) {
            navBarbackground.setImageResource(R.drawable.navbarbottomtablet)
        } else {
            navBarbackground.setImageResource(R.drawable.navbarbottomgap)
        }


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
        signLanguageUnderline = findViewById(R.id.signlanguage_underline)

        chosenUnderline = homeUnderLine

        signLanguageBtn.setOnClickListener {
            loadFragment(SignLanguage())
            Handler().postDelayed({
                homeIcon.setImageResource(R.drawable.home_icon)
                shoppingIcon!!.setImageResource(R.drawable.shopping_bag_icon)
                aboutIcon.setImageResource(R.drawable.about_us_icon)
                eventIcon!!.setImageResource(R.drawable.events_icon)

                val baseColor = R.color.grey
                val orange = R.color.orange

                homeText.setTextColor(resources.getColor(baseColor))
                shoppingText!!.setTextColor(resources.getColor(baseColor))
                aboutText.setTextColor(resources.getColor(baseColor))
                eventText!!.setTextColor(resources.getColor(baseColor))

                homeUnderLine.setBackgroundColor(Color.parseColor("#00FFFFFF"))
                shoppingUnderline!!.setBackgroundColor(Color.parseColor("#00FFFFFF"))
                eventUnderline!!.setBackgroundColor(Color.parseColor("#00FFFFFF"))
                aboutUnderline.setBackgroundColor(Color.parseColor("#00FFFFFF"))

                signLanguageBtn.setBackgroundResource(R.drawable.sign_language_button_yellow)
                animateUnderline(chosenUnderline!!, signLanguageUnderline)

            }, 250)
        }

        homeBtn.setOnClickListener {
            loadFragment(HomeFragment())
            Handler().postDelayed({
                changeNavBarColors(
                    R.drawable.home_icon_selected,
                    homeIcon,
                    homeText,
                    homeUnderLine
                )
                animateUnderline(chosenUnderline!!, homeUnderLine)
                chosenUnderline = homeUnderLine
            }, 300)
        }

        shoppingBtn.setOnClickListener {
            loadFragment(ShoppingFragment())
            Handler().postDelayed({
                changeNavBarColors(
                    R.drawable.shopping_icon_selected,
                    shoppingIcon!!,
                    shoppingText!!,
                    shoppingUnderline!!
                )
                animateUnderline(chosenUnderline!!, shoppingUnderline!!)
                chosenUnderline = shoppingUnderline
            }, 250)
        }

        eventsBtn.setOnClickListener {
            loadFragment(EventsFragment())
            Handler().postDelayed({
                changeNavBarColors(
                    R.drawable.events_icon_selected,
                    eventIcon!!,
                    eventText!!,
                    eventUnderline!!
                )
                animateUnderline(chosenUnderline!!, eventUnderline!!)
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
                animateUnderline(chosenUnderline!!, aboutUnderline)
                chosenUnderline = aboutUnderline
            }, 250)
        }

        changeNavBarColors(R.drawable.home_icon_selected, homeIcon, homeText, homeUnderLine)
        loadFragment(HomeFragment())

    }

    override fun loadFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.content_fragment, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }



    private fun openWhatsApp(phoneNumber: String) {
        Log.d(TAG, "openWhatsApp: entered")
        val message = "Hello" 

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://wa.me/$phoneNumber/?text=${URLEncoder.encode(message, "UTF-8")}")

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {

            Toast.makeText(this, "WhatsApp is not installed.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun changeNavBarColors(
        drawableValue: Int,
        image: ImageView,
        text: TextView,
        underLine: ImageView
    ) {
        homeIcon.setImageResource(R.drawable.home_icon)
        shoppingIcon!!.setImageResource(R.drawable.shopping_bag_icon)
        aboutIcon.setImageResource(R.drawable.about_us_icon)
        eventIcon!!.setImageResource(R.drawable.events_icon)

        val baseColor = R.color.grey
        val orange = R.color.orange

        homeText.setTextColor(resources.getColor(baseColor))
        shoppingText!!.setTextColor(resources.getColor(baseColor))
        aboutText.setTextColor(resources.getColor(baseColor))
        eventText!!.setTextColor(resources.getColor(baseColor))
        signLanguageBtn.setBackgroundResource(R.drawable.sign_language_button_grey)

        homeUnderLine.setBackgroundColor(Color.parseColor("#00FFFFFF"))
        shoppingUnderline!!.setBackgroundColor(Color.parseColor("#00FFFFFF"))
        eventUnderline!!.setBackgroundColor(Color.parseColor("#00FFFFFF"))
        aboutUnderline.setBackgroundColor(Color.parseColor("#00FFFFFF"))


        image.setImageResource(drawableValue)
        underLine.setBackgroundResource(R.drawable.chosen_underline)
        text.setTextColor(resources.getColor(orange))
    }

    override fun animateUnderline(chosenUnderline: ImageView, targetUnderline: ImageView) {
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

    private fun createNotificationChannel(){

        val name = "Kzn Bds"
        val description = "Notifications"
        val importance = NotificationManager.IMPORTANCE_HIGH
        var channel = NotificationChannel(channelID,name,importance)
        channel.description = description

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)


        Log.d(TAG, "setNotification() called")

    }
    private fun checkAndRequestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), REQUEST_NOTIFICATION_PERMISSION)
        } else {
            setNotification()
        }
    }

    private fun setNotification() {
        val intent = Intent(applicationContext, AlarmReceiver::class.java)
        val title = closestEvent?.title
        val message =  "Event Today! Join Us"
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificatioID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or  PendingIntent.FLAG_UPDATE_CURRENT
        )


        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (PendingIntent.getBroadcast(applicationContext, notificatioID, intent,PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_NO_CREATE) == null) {
            val time = getTime()
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                time,
                pendingIntent
            )
        } else {
            Log.d(TAG, "Notification already scheduled")
        }
    }


    private fun getTime(): Long {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("Africa/Johannesburg")

        try {

            closestEvent?.date?.let {
                val date = dateFormat.parse(it)

                date?.let {
                    return date.time
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return System.currentTimeMillis()
    }
        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


            } else {

                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }




    override fun onEventLoaded(events: List<Event>) {
        if(events.isNotEmpty()){
            closestEvent = events[0]
            checkAndRequestNotificationPermission()
        }


    }

}


