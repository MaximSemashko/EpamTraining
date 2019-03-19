package com.example.epamtraining.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.View;
import android.widget.RelativeLayout;

abstract public class CompoundRelativeLayout extends RelativeLayout implements ICompoundView {

    {
        final Context context = getContext();

        if (context == null) {
            throw new InflateException("Context was null before inflation");
        } else {
            View.inflate(context, getLayoutResId(), this);
            onViewInflated(context);
        }
    }

    public CompoundRelativeLayout(final Context context) {
        super(context);
    }

    public CompoundRelativeLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public CompoundRelativeLayout(final Context context, final AttributeSet attrs, final int defAttrs) {
        super(context, attrs, defAttrs);
    }
}
