package com.example.filip.mytirecenter.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * This class is an implementation of a line item decorator for a {@link RecyclerView}.
 *
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @version 1.0
 */
public class LineItemDecoration extends RecyclerView.ItemDecoration {

    // The default value for the line width
    private static final float DEFAULT_LINE_WIDTH = 2.0f;

    // The number of pixel for the separation line
    private final float mLineWidth;

    // The Paint to use for the line
    private final Paint mPaint;

    /**
     * Creates a LineItemDecoration for a line of default width
     */
    public LineItemDecoration() {
        this(DEFAULT_LINE_WIDTH);
    }

    /**
     * Creates a LineItemDecoration for a line of the given width
     *
     * @param lineWidth The number of pixel of the separator line
     */
    private LineItemDecoration(final float lineWidth) {
        this.mLineWidth = lineWidth;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(lineWidth);
        mPaint.setColor(Color.GRAY);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, (int) Math.floor(mLineWidth));
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        final RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        for (int i = 0; i < parent.getChildCount(); i++) {
            final View child = parent.getChildAt(i);
            c.drawLine(
                    layoutManager.getDecoratedLeft(child),
                    layoutManager.getDecoratedBottom(child),
                    layoutManager.getDecoratedRight(child),
                    layoutManager.getDecoratedBottom(child),
                    mPaint);
            c.drawLine(
                    layoutManager.getDecoratedRight(child),
                    layoutManager.getDecoratedTop(child),
                    layoutManager.getDecoratedRight(child),
                    layoutManager.getDecoratedBottom(child),
                    mPaint);
        }
    }
}
