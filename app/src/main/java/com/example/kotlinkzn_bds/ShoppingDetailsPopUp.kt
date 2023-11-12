package com.example.kotlinkzn_bds

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.DialogFragment

class ShoppingDetailsPopup : DialogFragment() {

    private var shoppingItems: Product? = null
    private var listener: OnDialogDismissListener? = null
    private lateinit var dismissButton: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.shopping_details_pop_up, container, false)
        dismissButton = view.findViewById(R.id.dismissButton)

        dismissButton.setOnClickListener {
            dismiss() // Close the dialog
        }

        return view
    }

    interface OnDialogDismissListener {
        fun onDismiss()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        listener?.onDismiss()
    }

    // Add a method to set the listener
    fun setOnDialogDismissListener(listener: OnDialogDismissListener) {
        this.listener = listener
    }
}
