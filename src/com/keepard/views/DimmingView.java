package com.keepard.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.keepard.R;

public class DimmingView extends View {

    public DimmingView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
    
    }



    @Override
    protected void onDraw (Canvas canvas)
    {
        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(R.color.dimming));
        canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),paint);
    }
}
