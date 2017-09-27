package com.fc.vedio.helper;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author 范超 on 2017/9/9
 */

public abstract class RecyclerTouchHelper implements RecyclerView.OnItemTouchListener {
    private RecyclerView recyclerView;
    protected GestureDetectorCompat compat;

    public RecyclerTouchHelper(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        compat = new GestureDetectorCompat(recyclerView.getContext(),new RecyclerGestureDetector());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        compat.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        compat.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public abstract void onClick(RecyclerView.ViewHolder viewHolder);

    class RecyclerGestureDetector extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childView!=null){
                onClick(recyclerView.getChildViewHolder(childView));
            }
            return true;
        }
    }
}
