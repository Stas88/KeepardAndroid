package com.keepard.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keepard.R;
import com.keepard.adapters.CardPagerAdapter;
import com.keepard.models.Company;

public class CardActivity extends Activity implements OnClickListener{
	
	private final String TAG = "CardActivity";
	private LayoutInflater inflater;
	private View cardView;
	private View cardInfoView;
	
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
		
		cardInfoView = inflater.inflate(R.layout.card_info_layout, null);
		initCardInfoViews(cardInfoView);
		pages.add(cardInfoView);
	    
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
	    cardImage.setOnClickListener((android.view.View.OnClickListener) this);
	    RelativeLayout fr = (RelativeLayout)cardView.findViewById(R.id.fr);
	    ImageView left_button = (ImageView)fr.findViewById(R.id.left_button);
	    ImageView right_button = (ImageView)fr.findViewById(R.id.right_button);
	    left_button.setOnClickListener(this);
	}
	
	private void initCardInfoViews(View cardView) {
		Company company = (Company) getIntent().getSerializableExtra("company");
		cardView.setOnClickListener((android.view.View.OnClickListener) this);
		TextView name = (TextView)cardView.findViewById(R.id.name_entry);
		TextView descr = (TextView)cardView.findViewById(R.id.description_entry);
		TextView code = (TextView)cardView.findViewById(R.id.code_entry);
		name.setText(company.getName());
		descr.setText(company.getDescription());
		code.setText(String.valueOf(company.getCode()));
		 RelativeLayout fr = (RelativeLayout)cardView.findViewById(R.id.fr);
		ImageView left_button = (ImageView)fr.findViewById(R.id.left_button);
	    ImageView right_button = (ImageView)fr.findViewById(R.id.right_button);
	    left_button.setOnClickListener(this);
	}

	


	public void onClick(View arg0) {
		finish();
	}


	
}
