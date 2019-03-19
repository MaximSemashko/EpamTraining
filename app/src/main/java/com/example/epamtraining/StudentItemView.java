package com.example.epamtraining;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.epamtraining.base.CompoundRelativeLayout;
import com.example.epamtraining.entities.Student;

public class StudentItemView extends CompoundRelativeLayout {

    private TextView nameView;
    private TextView homeworkView;

    private String attributeName;
    private String attributeHomework;

    public StudentItemView(Context context) {
        super(context);
    }

    public StudentItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        parseAttributes(context,attrs);
    }

    private void parseAttributes(Context context, AttributeSet attrs) {
        final Resources.Theme theme = context.getTheme();
        final TypedArray styledAttributes = theme.obtainStyledAttributes(attrs, R.styleable.StudentItemView, 0, 0);

        try {
            attributeName = styledAttributes.getString(R.styleable.StudentItemView_name);
            attributeHomework = styledAttributes.getString(R.styleable.StudentItemView_homework_counter);

            nameView.setText(attributeName);
            homeworkView.setText(attributeHomework);
        } finally {
            styledAttributes.recycle();
        }
    }

    public StudentItemView(Context context, AttributeSet attrs, int defAttrs) {
        super(context, attrs, defAttrs);

        parseAttributes(context,attrs);
    }

    @Override
    public void onViewInflated(@NonNull Context context) {
        nameView = findViewById(R.id.student_name_view);
        homeworkView = findViewById(R.id.student_homeworks_counter_view);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.student_item_compound_view;
    }

    @UiThread
    public StudentItemView setName(final String name) {
        nameView.setText(name);
        return this;
    }

    @UiThread
    public StudentItemView setHwCounter(final String hwCounter) {
        homeworkView.setText(hwCounter);
        return this;
    }
}
