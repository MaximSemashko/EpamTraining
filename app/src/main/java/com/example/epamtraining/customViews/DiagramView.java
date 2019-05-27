package com.example.epamtraining.customViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.example.epamtraining.R;

public class DiagramView extends View {

    private int caloriesNeeded;
    private int caloriesBurned;

    private Paint neededPaint = new Paint();
    private Paint burnedPaint = new Paint();

    public DiagramView(Context context) {
        this(context, null);
    }

    public DiagramView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiagramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        neededPaint.setColor(getResources().getColor(R.color.colorPrimary));
        burnedPaint.setColor(getResources().getColor(R.color.colorPrimaryDark));

        if (isInEditMode()) {
            caloriesNeeded = 3000;
            caloriesBurned = 1500;
        }
    }

    public void update(int needed, int burned) {
        this.caloriesNeeded = needed;
        this.caloriesBurned = burned;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawPieDiagram(canvas);
        } else {
            drawRectDiagram(canvas);
        }

    }

    private void drawRectDiagram(Canvas canvas) {
        if (caloriesBurned + caloriesNeeded == 0)
            return;

        long max = Math.max(caloriesBurned, caloriesNeeded);
        long burnedHeight = canvas.getHeight() * caloriesBurned / max;
        long neededHeight = canvas.getHeight() * caloriesNeeded / max;

        int w = getWidth() / 4;

        canvas.drawRect(w / 2, canvas.getHeight() - burnedHeight, w * 3 / 2, canvas.getHeight(), burnedPaint);
        canvas.drawRect(5 * w / 2, canvas.getHeight() - neededHeight, w * 7 / 2, canvas.getHeight(), neededPaint);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void drawPieDiagram(Canvas canvas) {
        if (caloriesBurned + caloriesNeeded == 0)
            return;

        float burnedAngle = 360.f * caloriesBurned / (caloriesBurned + caloriesNeeded);
        float neededAngle = 360.f * caloriesNeeded / (caloriesBurned + caloriesNeeded);

        int space = 10; // space between caloriesNeeded and caloriesBurned
        int size = Math.min(getWidth(), getHeight()) - space * 2;
        final int xMargin = (getWidth() - size) / 2;
        final int yMargin = (getHeight() - size) / 2;

        canvas.drawArc(xMargin - space, yMargin, getWidth() - xMargin - space, getHeight() - yMargin, 180 - burnedAngle / 2, burnedAngle, true, burnedPaint);
        canvas.drawArc(xMargin + space, yMargin, getWidth() - xMargin + space, getHeight() - yMargin, 360 - neededAngle / 2, neededAngle, true, neededPaint);
    }
}

