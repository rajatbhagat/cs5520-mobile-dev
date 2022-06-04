package com.cs5520.mobile_dev;


import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewItemClickHelper implements RecyclerView.OnItemTouchListener {
    private final OnItemClickListener itemClickListener;
    private final GestureDetector gestureDetector;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }


    public RecyclerViewItemClickHelper(Context context, OnItemClickListener listener) {
        this.itemClickListener = listener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        View view = rv.findChildViewUnder(e.getX(), e.getY());
        if (view != null && this.itemClickListener != null && gestureDetector.onTouchEvent(e)) {
            this.itemClickListener.onItemClick(view, rv.getChildAdapterPosition(view));
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}