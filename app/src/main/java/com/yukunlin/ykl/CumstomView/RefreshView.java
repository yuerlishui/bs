package com.yukunlin.ykl.CumstomView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;


import com.yukunlin.ykl.R;
import com.yukunlin.ykl.utils.SimpleUtils;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by seatNumber on 15/11/23.
 */
public class RefreshView extends ImageView implements PtrUIHandler {

    private float percent = 0;
    private Bitmap bitmap;
    private boolean refreshBegin = false;
    private Paint paint;

    public RefreshView(Context context) {
        super(context);
        initView();
    }

    public RefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        paint = new Paint();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.refresh_book);
        setMinimumHeight(getResources().getDimensionPixelOffset(R.dimen.refresh_view_height));
        setScaleType(ScaleType.CENTER_INSIDE);
        setImageResource(R.drawable.loading_00000);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (refreshBegin) {
            super.onDraw(canvas);
        }else{
            if(bitmap!=null) {
                int width = getWidth();
                int height = getHeight();
                int firstLeft = width / 2 - bitmap.getWidth() - SimpleUtils.dip2px(getContext(),1.5f);
                int firstTop = height / 2 - bitmap.getHeight()/2;
                canvas.drawBitmap(bitmap,firstLeft,firstTop,paint);
                int secondLeft = width / 2 + SimpleUtils.dip2px(getContext(),1.5f);
                float truePercent = 1 - percent>0?1-percent:0;
                int secondTop = (int) (firstTop - (height/2)* truePercent);
                canvas.drawBitmap(bitmap,secondLeft,secondTop,paint);
            }
        }
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        setImageResource(R.drawable.loading_00000);
        refreshBegin=false;
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        refreshBegin = true;
        setImageResource(R.drawable.loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) getDrawable();
        animationDrawable.start();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        percent = ptrIndicator.getCurrentPercent();
        invalidate();
    }
}
