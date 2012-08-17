package com.keepard.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.keepard.R;




public class DetailActivity extends SherlockFragmentActivity {
	
	
	private ImageView image;
	private final static String TAG = "DetailActivity";
	LayoutInflater inflater = (LayoutInflater)this.getSystemService(this.LAYOUT_INFLATER_SERVICE);

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_activity_layout);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		
		Intent intent = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	      String query = intent.getStringExtra(SearchManager.QUERY);
	      Log.d(TAG, "Search Query = " + query);
		
	    }
	}
	
	
		@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		//View searchView = (View)inflater.inflate(R.layout.collapsible_edittext, null);
		  menu.add("Search")
          .setIcon(R.drawable.search_icon)
          .setActionView(R.layout.collapsible_edittext)
          .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected 1");
		onSearchRequested();
		/*
		if (item.getItemId() == R.id.menu_search) {
			Log.d(TAG, "onOptionsItemSelected 2");
			this.getSupportActionBar().hide();
			LayoutInflater inflater = (LayoutInflater)this.getSystemService(this.LAYOUT_INFLATER_SERVICE);
			LinearLayout ll = (LinearLayout)findViewById(R.id.search_layout);
			ll.setVisibility(View.VISIBLE);
			Log.d(TAG, "onSearchRequested");
			return true;
		}
		*/
		return false;
	}
	
	
	
} 