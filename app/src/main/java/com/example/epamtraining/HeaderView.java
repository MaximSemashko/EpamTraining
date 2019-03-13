package com.example.epamtraining;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class HeaderView extends LinearLayout {

    private TextView userNameView;
    private TextView userEmailView;
    private ImageView userIconView;

    public HeaderView(Context context) {
        super(context);

        init();
    }

    public HeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public HeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }

    private void init() {
        setOrientation(VERTICAL);

        inflate(getContext(), R.layout.header_compound_view, this);

        userNameView = findViewById(R.id.user_name_view);
        userEmailView = findViewById(R.id.email_view);
        userIconView = findViewById(R.id.profile_image_view);
    }

    public void updateImage(String colorCode) {
        int color = Color.parseColor(colorCode);
        userIconView.setColorFilter(color);
    }
}
