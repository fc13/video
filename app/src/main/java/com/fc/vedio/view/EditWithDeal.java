package com.fc.vedio.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.fc.vedio.R;

/**
 * @author 范超 on 2017/9/22
 */

public class EditWithDeal extends EditText {
    private Drawable imgIcon;
    private Context context;

    public EditWithDeal(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public EditWithDeal(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public EditWithDeal(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    public void initView() {
        imgIcon = context.getResources().getDrawable(R.drawable.ic_delete);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                setDrawable();
            }
        });
        setDrawable();
    }

    private void setDrawable() {
        if (length() < 1) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        } else {
            imgIcon.setBounds(0,0,10,0);
            setCompoundDrawablesWithIntrinsicBounds(null, null, imgIcon, null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (imgIcon != null && event.getAction() == MotionEvent.ACTION_UP) {
            int x = (int) event.getRawX();
            int y = (int) event.getRawY();
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 100;
            if (rect.contains(x, y)) {
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }
}
