package com.fc.vedio.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.VideoView;

/**
 * @author 范超 on 2017/9/22
 */

public class CustomVideoView extends VideoView {
    public CustomVideoView(Context context) {
        super(context);
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    public void setOnKeyListener(OnKeyListener l) {
        super.setOnKeyListener(l);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
