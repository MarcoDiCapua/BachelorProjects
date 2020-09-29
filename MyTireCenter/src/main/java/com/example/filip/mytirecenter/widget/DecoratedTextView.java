package com.example.filip.mytirecenter.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.example.filip.mytirecenter.R;

/**
 * An implementation of a {@link AppCompatTextView} used to contain details
 *
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @version 1.0
 */
public class DecoratedTextView extends AppCompatTextView {

    // The offset we apply to the text
    private static float sOffset;

    // The Paint we use to draw the image
    private static Paint sPaint;

    // The Path for the arrow
    private static Path sPath;

    /**
     * The creation of the DecoratedTextView with the following parameters
     *
     * @param context The Context
     */
    public DecoratedTextView(Context context) {
        super(context);
        init();
    }

    /**
     * The creation of the DecoratedTextView with the following parameters
     *
     * @param context The Context
     * @param attrs   The AttributeSet
     */
    public DecoratedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * The creation of the DecoratedTextView with the following parameters
     *
     * @param context      The Context
     * @param attrs        The AttributeSet
     * @param defStyleAttr The StyleAttribute
     */
    public DecoratedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (sPath == null) {
            final float size = getResources().getDimension(R.dimen.tire_center_destination_icon_size);
            final float offset = (getHeight() - size) / 2;
            sPath = new Path();
            sPath.moveTo(0, offset);
            sPath.lineTo(0, size + offset);
            sPath.lineTo(size, offset + size / 2);
            sPath.close();
        }
        canvas.save();
        canvas.translate(sOffset, 0);
        super.onDraw(canvas);
        canvas.translate(-sOffset, 0);
        canvas.drawPath(sPath, sPaint);
        canvas.restore();
    }

    // init method states the initialization of the component
    private void init() {
        if (sPaint == null) {
            sOffset = getResources().getDimension(R.dimen.tire_center_destination_offset);
            sPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            sPaint.setStyle(Paint.Style.FILL);
            sPaint.setColor(Color.BLUE);
        }
    }

}
