package com.keepard.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.keepard.R;
import com.keepard.adapters.CardPagerAdapter;
import com.keepard.models.Company;

public class CardActivity extends Activity implements OnClickListener{
	
	private final String TAG = "CardActivity";
	private LayoutInflater inflater;
	private View cardView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		ViewPager pager = new ViewPager(this);
		List<View> pages = new ArrayList<View>();
		CardPagerAdapter adapter = new CardPagerAdapter(pages);
		pager.setAdapter(adapter);
		pager.setCurrentItem(0);     
		setContentView(pager);
		
		inflater = LayoutInflater.from(this);
		cardView = inflater.inflate(R.layout.card_view, null);
		initCardViews(cardView);
		pages.add(cardView);
		
		cardView = inflater.inflate(R.layout.card_view, null);
		initCardInfoViews(cardView);
		pages.add(cardView);
	    
	}
	
	private void initCardViews(View cardView) {
		Company company = (Company) getIntent().getSerializableExtra("company");
		ImageView cardImage = (ImageView) cardView.findViewById(R.id.card_image);
	    int resID = this.getResources().getIdentifier("zdb_" + company.getName().toLowerCase() , "drawable", "com.keepard");
	    Log.d(TAG, "showName = " + company.getName().toLowerCase());
	    Drawable drawable = this.getResources()
                .getDrawable(R.drawable.zdb_vidivan1);
	    Bitmap bm = ((BitmapDrawable) drawable).getBitmap();
	    cardImage.setImageBitmap(bm);
	}
	
	private void initCardInfoViews(View cardView) {
		Company company = (Company) getIntent().getSerializableExtra("company");
		ImageView cardImage = (ImageView) cardView.findViewById(R.id.card_image);
	    int resID = this.getResources().getIdentifier("zdb_" + company.getName().toLowerCase() , "drawable", "com.keepard");
	    Log.d(TAG, "showName = " + company.getName().toLowerCase());
	    Drawable drawable = this.getResources()
                .getDrawable(R.drawable.zdb_vidivan1);
	    Bitmap bm = ((BitmapDrawable) drawable).getBitmap();
	    cardImage.setImageBitmap(bm);

	}

	
	@Override
    public boolean onTouchEvent(MotionEvent event) {
		Log.d(TAG, "onclick()");
		finish();
		return super.onTouchEvent(event);
    }

	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		Log.d(TAG, "onclick()");
		finish();
		
		
	}
	
}
