package com.keepard.views;

import util.Util;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class RoundedImageView extends ImageView {

	
	private final String TAG = "RoundedImageView";

	public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public RoundedImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public RoundedImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	

@Override
   protected void onDraw(Canvas canvas) {
       //super.onDraw(canvas);
	
           Drawable drawable = getDrawable();
           if (drawable != null) {
        	   Bitmap b =  ((BitmapDrawable)drawable).getBitmap() ;
        	   if (b !=null) {
		           Bitmap bitmap  = b.copy(Bitmap.Config.ARGB_8888, true);
	
		           int w = getWidth(), h = getHeight();
		           Bitmap roundBitmap =  Util.getRoundedCornerBitmap( getContext(), bitmap,10 , w, h , false, false,false, false);
		          
		           
		          
		           canvas.drawBitmap(roundBitmap, 0.0f, 0.0f, null);
        	   }
           }
           
          
           
   }
	


}
