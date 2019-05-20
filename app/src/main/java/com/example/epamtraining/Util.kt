package com.example.epamtraining

import android.view.View
import android.widget.ProgressBar

class Util {
    companion object {
         fun hideProgress(progressBar: ProgressBar) {
            if (progressBar.visibility != View.GONE) {
                progressBar.visibility = View.GONE
            }
        }

         fun showProgress(progressBar: ProgressBar) {
            if (progressBar.visibility != View.VISIBLE) {
                progressBar.visibility = View.VISIBLE
            }
        }
    }
}