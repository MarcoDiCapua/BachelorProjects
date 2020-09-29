package com.example.filip.mytirecenter.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.example.filip.mytirecenter.R;

/**
 * Implementation of a {@link FloatingActionButton} that shows the tire center province code
 *
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @version 1.0
 */
public class TireCenterTextFAB extends FloatingActionButton {

    // The Paint to use for text
    private static Paint sTextPaint;
    // The Text to show in this class
    private String mText;

    private Rect result = new Rect();

    /**
     * The creation of the TireCenterTextFAB with the following parameters
     *
     * @param context The context
     */
    public TireCenterTextFAB(Context context) {
        super(context);
        init();
    }

    /**
     * The creation of the TireCenterTextFAB with the following parameters
     *
     * @param context The context
     * @param attrs   The attributeSet
     */
    public TireCenterTextFAB(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * The creation of the TireCenterTextFAB with the following parameters
     *
     * @param context      The context
     * @param attrs        The attributeSet
     * @param defStyleAttr The Style Attribute
     */
    public TireCenterTextFAB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * Set the Text showed inside the button
     *
     * @param text The province code of the tire center
     */
    public void setText(final String text) {
        this.mText = text;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!TextUtils.isEmpty(mText)) {
            sTextPaint.getTextBounds(mText, 0, mText.length(), result);
            final float textX = -result.left + (getWidth() - result.width()) / 2;
            final float textY = -result.top + (getHeight() - result.height()) / 2;
            canvas.drawText(mText, textX, textY, sTextPaint);
        }
    }


    // init method states the initialization of the component
    private void init() {
        if (sTextPaint == null) {
            sTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            sTextPaint.setTextSize(getResources().getDimension(R.dimen.indicator_text_size));
            sTextPaint.setColor(Color.WHITE);
            sTextPaint.setFakeBoldText(true);
        }
        if (isInEditMode()) {
            mText = "";
        }
    }
}
