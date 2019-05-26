package com.example.epamtraining.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.epamtraining.R


class HeaderView : LinearLayout {

    private var userIconView: ImageView? = null
    private var userNameView: TextView? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        orientation = VERTICAL

        View.inflate(context, R.layout.main_header_compound_view, this)
        userIconView = findViewById(R.id.headerImageView)
        userNameView = findViewById(R.id.headerNameTextView)
    }
}