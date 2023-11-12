package com.example.kotlinkzn_bds

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import java.util.UUID
import kotlin.coroutines.CoroutineContext



class DonationActivity : AppCompatActivity() {

    private lateinit var tenDonation: AppCompatButton
    private lateinit var fiftyDonation: AppCompatButton
    private lateinit var hundredDonation: AppCompatButton
    private lateinit var twoHundredDonation: AppCompatButton
    private lateinit var continueButton: AppCompatButton
    private lateinit var onBackButton: RelativeLayout
    private lateinit var customAmountEditText: AppCompatEditText
    private lateinit var scaleUp: Animation
    private lateinit var scaleDown: Animation
    private lateinit var selectedPayment: String
    private lateinit var donationButtons: Array<AppCompatButton>
    private lateinit var reference: String

    companion object {
        private const val TAG = "DonationActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donation)
        reference = generateUniqueReference()
        init()
    }

    private fun init() {
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down)

        tenDonation = findViewById(R.id.tenDonation)
        fiftyDonation = findViewById(R.id.FiftyDonation)
        hundredDonation = findViewById(R.id.HundredDonation)
        twoHundredDonation = findViewById(R.id.TwoHundredDonation)
        continueButton = findViewById(R.id.continueButton)
        onBackButton = findViewById(R.id.on_back_button)
        customAmountEditText = findViewById(R.id.customDonation)

        donationButtons = arrayOf(tenDonation, fiftyDonation, hundredDonation, twoHundredDonation)

        onBackButton.setOnClickListener {
            onBackPressed()
        }

        donationButtons.forEach { button ->
            button.setOnClickListener {
                handleButtonClick(button)
            }
        }

        customAmountEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                handleEditTextFocus()
            }
        }

        customAmountEditText.setOnClickListener {
            handleEditTextFocus()
        }

        continueButton.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                continueButton.startAnimation(scaleUp)
            } else if (motionEvent.action == MotionEvent.ACTION_UP) {
                continueButton.startAnimation(scaleDown)
            }
            false
        }

        scaleDown.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                val paymentAmount: Int = when {
                    selectedPayment.isNotEmpty() -> selectedPayment.toInt()
                    customAmountEditText.text != null && customAmountEditText.text.toString()
                        .isNotEmpty() -> customAmountEditText.text.toString().toInt()
                    else -> {
                        Toast.makeText(this@DonationActivity, "Please select a donation", Toast.LENGTH_SHORT).show()
                        return
                    }
                }
                sendPaymentRequest(paymentAmount)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
    }

    private fun handleEditTextFocus() {
        customAmountEditText.setText("")

        donationButtons.forEach { button ->
            button.setTextColor(ContextCompat.getColor(this, R.color.black))
            button.setBackgroundResource(R.drawable.donation_option_background)
        }
        selectedPayment = ""
    }

    private fun handleButtonClick(clickedButton: AppCompatButton) {
        if (customAmountEditText.hasFocus()) {
            customAmountEditText.clearFocus()
        }

        donationButtons.forEach { button ->
            if (button == clickedButton) {
                selectedPayment = when (button.text.toString()) {
                    "R10" -> "10"
                    "R50" -> "50"
                    "R100" -> "100"
                    "R200" -> "200"
                    else -> ""
                }
                button.setTextColor(ContextCompat.getColor(this, R.color.white))
                button.setBackgroundResource(R.drawable.is_selected_donation)
                Log.d(TAG, "handleButtonClick: $selectedPayment")
            } else {
                button.setTextColor(ContextCompat.getColor(this, R.color.black))
                button.setBackgroundResource(R.drawable.donation_option_background)
            }
        }

        customAmountEditText.setText("")
    }

    private fun sendPaymentRequest(amount: Int) {
        val lineItems: List<InvoicePayments.LineItem> = ArrayList()

        // Creates the requested object
        val request = InvoicePayments(
            "kzn_bds_App",
            "ZAR",
            amount * 100,
            "micros",
            "2023-11-03T13:30:48.9070611Z",
            "micros_129473",
            reference,
            "open"
        )

        // Converts the request object to JSON string
        val gson = Gson()
        val jsonRequest = gson.toJson(request)

        // Sends the request using OkHttp
        val client = OkHttpClient()
        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
        val requestBody = RequestBody.create(JSON, jsonRequest)

        val httpRequest = Request.Builder()
            .url("https://api.zapper.com/business/api/v1/merchants/63493/sites/80735/invoices")
            .post(requestBody)
            .addHeader("Authorization", "Bearer b19e9abc54aa46a2a56596746207368d")
            .addHeader("x-api-key", "b19e9abc54aa46a2a56596746207368d")
            .addHeader("Accept", "text/plain")
            .addHeader("Representation-Type", "deeplink/zappercode/v6")
            .build()

        client.newCall(httpRequest).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body!!.string()
                    sendPaymentGet(responseBody)
                    Log.d(TAG, "onResponse: success: $responseBody")
                    Log.d(TAG, "onResponse: success: $response")
                } else {

                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure:Call  $call exception$e")
            }
        })
    }

    private fun sendPaymentGet(code: String) {
        if (code != null) {
            val zapperCodeUri = Uri.parse(code)
            if (zapperCodeUri != null) {
                val openUrlIntent = Intent(Intent.ACTION_VIEW, zapperCodeUri)
                startActivity(openUrlIntent)

                // Opens the Zapper app
                val openZapperIntent =
                    packageManager.getLaunchIntentForPackage("com.zapper.android")
                if (openZapperIntent != null) {
                    startActivity(openZapperIntent)
                } else {

                }
            }
        }
    }

    private fun generateUniqueReference(): String {
        val randomUUID = UUID.randomUUID().toString()
        return randomUUID
    }
}
