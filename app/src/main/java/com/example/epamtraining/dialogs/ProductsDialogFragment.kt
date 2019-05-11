package com.example.epamtraining.dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.widget.EditText
import com.example.epamtraining.R


class ProductsDialogFragment : DialogFragment() {

    interface addProductDialogListener {
        fun addProduct(name: String, calories: Double)
    }

    private lateinit var productName: EditText
    private lateinit var productCalories: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = activity
        val builder = AlertDialog.Builder(context!!)
        val dialogView = activity!!.layoutInflater.inflate(R.layout.products_dialog, null)
        productName = dialogView.findViewById(R.id.productNameEditText);
        productCalories = dialogView.findViewById(R.id.productCaloriesEditText);

        builder.setView(dialogView)
                .setPositiveButton("Add") { dialog, id ->
                    val name: String = productName.text.toString()
                    val calories: Double = productCalories.text.toString().toDouble()
                    val listener = activity as addProductDialogListener?
                    listener?.addProduct(name, calories)
                }
                .setNegativeButton("Cancel") { dialog, id -> dialog.dismiss() }

        val dialog = builder.create()
        dialog.show()
        return dialog
    }
}
