package com.bds.kotlinkzn_bds

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DonationPersonalInfo : AppCompatActivity() {
    private lateinit var termsTxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donation_personal_info)
        init()//method to initialize the vallues
    }

    private fun init() {
        termsTxt = findViewById(R.id.termsTxt)

        termsTxt.setOnClickListener {
            showTermsDialog()
        }
    }

    private fun showTermsDialog() {//show
        val dialogFragment = TermsDialogFragment()
        dialogFragment.show(supportFragmentManager, "terms_dialog")
    }
}
