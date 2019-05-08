package com.example.epamtraining.dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.example.epamtraining.R
import kotlinx.android.synthetic.main.products_dialog.*


class ProductsDialogFragment : DialogFragment() {

    interface addProductDialogListener {
        fun addProduct(name: String, calories: Double)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = activity
        val builder = AlertDialog.Builder(context!!)
        val dialogView = activity!!.layoutInflater.inflate(R.layout.products_dialog, null)

        builder.setView(dialogView)
                .setPositiveButton("Add") { dialog, id ->
                    val name: String = productNameEditText?.text.toString()
                    val calories: Double = productCaloriesEditText?.text.toString().toDouble()
                    val listener = activity as addProductDialogListener?

                    listener?.addProduct(name,calories)
                }
                .setNegativeButton("Cancel") { dialog, id -> dialog.dismiss() }

        val dialog = builder.create()
        dialog.show()
        return dialog
    }
}
