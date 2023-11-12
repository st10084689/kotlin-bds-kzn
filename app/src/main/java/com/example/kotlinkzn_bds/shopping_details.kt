package com.example.kotlinkzn_bds

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


class shopping_details : AppCompatActivity(), ShoppingDetailsPopup.OnDialogDismissListener {
    private lateinit var shoppingTitle: TextView;
    private lateinit var shoppingDescription: TextView
    private lateinit var priceTxt: TextView
    private lateinit var shoppingImage: ImageView
    private lateinit var shopItem: Product
    private  var position = 0
    private lateinit var backPressedBtn: RelativeLayout
    private lateinit var purchaseButton: Button
   lateinit var scaleUp: Animation
   lateinit var scaleDown: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_details)
        val intent = intent
        position = intent.getIntExtra("Position", 1)
        init()
    }

    fun init() {
        shoppingTitle = findViewById(R.id.shoppingDetailsTitle)
        shoppingDescription = findViewById(R.id.shoppingDetailsDescription)
        priceTxt = findViewById(R.id.price)
        shoppingImage = findViewById<ImageView>(R.id.imageShopping)
        shopItem = Utility.getShoppingItems().get(position)
        shoppingTitle.setText(shopItem?.title)
        backPressedBtn = findViewById<RelativeLayout>(R.id.on_back_button)
        priceTxt.setText("R" + shopItem.price.toString())
        purchaseButton = findViewById<Button>(R.id.purchaseBtn)
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down)
        purchaseButton.setOnTouchListener(OnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                purchaseButton.startAnimation(scaleUp)
            } else if (motionEvent.action == MotionEvent.ACTION_UP) {
                purchaseButton.startAnimation(scaleDown)
            }
            false
        })
        scaleDown.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                showTextNotice()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        shoppingImage.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@shopping_details, ImageViewerActivity::class.java)
            intent.putExtra("IMAGE_PATH", shopItem.image)
            startActivity(intent)
        })
        backPressedBtn.setOnClickListener(View.OnClickListener { onBackPressed() })
        shoppingDescription.setText(shopItem?.description)
        Glide.with(shoppingImage)
            .load(shopItem.image)
            .into(shoppingImage)
    }

    fun sendEmail() {
        val toEmail = Intent(Intent.ACTION_SEND)
        toEmail.type = "message/rfc822"
        toEmail.putExtra(Intent.EXTRA_EMAIL, arrayOf("admin3@bdskzn.co.za"))
        toEmail.putExtra(Intent.EXTRA_SUBJECT, "purchase information for " + shopItem?.title)
        toEmail.putExtra(
            Intent.EXTRA_TEXT,
            "Hi, I'd like to purchase the " + shopItem?.title+ " for R" + shopItem?.price
        )
        startActivity(Intent.createChooser(toEmail, "Send Email"))
    }

    fun showTextNotice() {
        val sendPopup = ShoppingDetailsPopup()
        sendPopup.setOnDialogDismissListener(object : ShoppingDetailsPopup.OnDialogDismissListener {
            override fun onDismiss() {
                sendEmail()
            }
        })
        sendPopup.show(supportFragmentManager, "terms_dialog")
    }

    override fun onDismiss() {}

    companion object {
        private const val TAG = "shopping_details"
    }
}