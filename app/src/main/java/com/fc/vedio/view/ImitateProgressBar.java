package com.fc.vedio.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.fc.vedio.R;

/**
 * @author 范超 on 2017/10/11
 */

public class ImitateProgressBar extends View {
    private int height, width;//View的宽和高
    private float radius;//圆角半径
    private float sideWidth = 1.0f;
    private RectF bgRectF,fgRectF;
    private Paint sidePaint;
    private Paint srcPaint;
    private float scale;
    private Bitmap fgSrc;
    private PorterDuffXfermode mPorterDuffXfermode;


    public ImitateProgressBar(Context context) {
        this(context, null);
    }

    public ImitateProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImitateProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        sidePaint = new Paint();
        sidePaint.setColor(Color.RED);
        sidePaint.setStyle(Paint.Style.STROKE);
        sidePaint.setStrokeWidth(sideWidth);
        sidePaint.setAntiAlias(true);

        srcPaint = new Paint();
        srcPaint.setColor(Color.RED);
        srcPaint.setAntiAlias(true);

        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //获取View的宽和高
        width = getMeasuredWidth();
        height = getMeasuredHeight();

        radius = height / 2.0f;
        if (bgRectF == null) {
            bgRectF = new RectF(sideWidth, sideWidth, width - sideWidth, height - sideWidth);
        }
        if (fgRectF == null) {
            fgRectF = new RectF(new RectF(sideWidth, sideWidth, (width - sideWidth) * scale, height-sideWidth));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(bgRectF, radius, radius, sidePaint);
        drawBg(canvas);
        postInvalidate();
    }

    private void drawBg(Canvas canvas) {
        if (scale == 0.0f) {
            return;
        }
        Bitmap fgBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas fgCanvas = new Canvas(fgBitmap);
        if (fgSrc == null) {
            fgSrc = BitmapFactory.decodeResource(getResources(), R.drawable.fg);
        }
        fgCanvas.drawRoundRect(
                new RectF(sideWidth, sideWidth, (width - sideWidth) * scale, height - sideWidth),
                radius, radius, srcPaint);

        srcPaint.setXfermode(mPorterDuffXfermode);
        fgCanvas.drawBitmap(fgSrc, null, bgRectF, srcPaint);

        canvas.drawBitmap(fgBitmap, 0, 0, null);
        srcPaint.setXfermode(null);
    }

    public void setScale(float scale){
        this.scale = scale;
    }
}
