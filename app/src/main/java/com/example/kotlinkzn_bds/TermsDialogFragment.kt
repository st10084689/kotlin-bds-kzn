package com.example.kotlinkzn_bds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment


class TermsDialogFragment : DialogFragment() {
    private lateinit var dismissBtn: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.custom_pop_up_layout, container, false)
        dismissBtn = view.findViewById(R.id.dismissButton)

        dismissBtn.setOnClickListener {
            dismiss() // Close the dialog
        }

        return view
    }
}