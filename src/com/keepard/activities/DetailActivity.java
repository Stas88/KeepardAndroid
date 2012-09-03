package com.keepard.activities;

import util.Constants;
import util.ParsingUtil;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.keepard.R;
import com.keepard.models.Card;
import com.keepard.models.Company;




public class DetailActivity extends FragmentActivity implements OnClickListener {
	
	
	private ImageView image;
	private final static String TAG = "DetailActivity";
	private LayoutInflater inflater;
	
	
	private LinearLayout searchLayout;
	private ImageView logo_word;
	private ImageButton searchButton;
	private ImageButton searchButton2;
	private int currentId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_activity_layout);
		inflater = (LayoutInflater)this.getSystemService(this.LAYOUT_INFLATER_SERVICE);
		setupViews();
	}
	
	
		@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_list_menu, (android.view.Menu) menu);
		//View searchView = (View)inflater.inflate(R.layout.collapsible_edittext, null);
		/*
		  menu.add("Search")
          .setIcon(R.drawable.search_icon)
          .setActionView(R.layout.collapsible_edittext)
          .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		*/
		return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected 1");
		if (item.getItemId() == R.id.menu_search) {
			Log.d(TAG, "onOptionsItemSelected 2");
			resetLayoutForSearch();
			Log.d(TAG, "onSearchRequested");
			return true;
		}
		return false;
	}
	
	
	
	private void resetLayoutForSearch() {
		searchLayout.setVisibility(View.VISIBLE);
		logo_word.setVisibility(View.GONE);
		searchButton.setVisibility(View.GONE);
		
	}
	
	private void resetLayoutForCommonView() {
		searchLayout.setVisibility(View.GONE);
		logo_word.setVisibility(View.VISIBLE);
		searchButton.setVisibility(View.VISIBLE);
		



	}

	private void setupViews() {
		searchLayout = (LinearLayout)findViewById(R.id.search_layout);
		logo_word = (ImageView)findViewById(R.id.logo_word);
		searchButton = (ImageButton)findViewById(R.id.search_button);
		searchButton2 = (ImageButton)findViewById(R.id.search_button2);
		searchButton.setOnClickListener(this);
		searchButton2.setOnClickListener(this);
		//TextView ok = (TextView)findViewById(R.id.myImageViewText1);
		//ok.setVisibility(View.GONE);
		//TextView cancel = (TextView)findViewById(R.id.myImageViewText);
		//cancel.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search_button:
			resetLayoutForSearch();
			break;
		case R.id.search_button2:	
			resetLayoutForCommonView();
			//InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
		    //imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
			break;
		}
		
	}
	
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		Log.d(TAG, "onActivityResult resultCode = " + resultCode);
	      if (resultCode == RESULT_OK) {
	    	  Log.d(TAG, "onActivityResult 1");
	         String contents = intent.getStringExtra("SCAN_RESULT");
	         String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
	         Log.d(TAG, "onActivityResult 2");
	         Log.d(TAG, "onActivityResult 3 + " + "ScanResult: " + contents + " Format: " + format);
	         Log.d(TAG, "getCurrentId =  " + String.valueOf(getCurrentId()));
	   	     Cursor cursor = this.getContentResolver().query(Card.CONTENT_URI, Card.projection,  Card.CARD_ID + " = ? ", new String [] {String.valueOf(getCurrentId())}, null);
	   	     cursor.moveToFirst();
	   	     Company company = ParsingUtil.getCompanyFromCursor(cursor);
	   	     Intent show_card = new Intent(this, CardActivity.class);
	   	     company.setCode(Long.valueOf(contents));
	   	     show_card.setAction(Constants.ACTION_NEW_CARD);
	   	     company.setCode_format(format);
			 show_card.putExtra("company", company);
			 Log.d(TAG, "company to save =  " + company);
			 cursor.deactivate();
			 startActivity(show_card);
	         // Handle successful scan
	      } else if (resultCode == RESULT_CANCELED) {
	         // Handle cancel
	      }
	   }
		
	public void startScanning(int _id) {
		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
        Log.d(TAG, "Start scanning!");
        Log.d(TAG, "setCurrentId = " + _id);
        setCurrentId(_id);
        startActivityForResult(intent, 0);
	}
	
	private int getCurrentId() {
		return currentId;
	}
	
	private void setCurrentId(int currentId) {
		this.currentId = currentId;
	}   

} 