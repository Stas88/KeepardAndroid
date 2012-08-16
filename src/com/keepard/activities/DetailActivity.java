package com.keepard.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.keepard.R;



public class DetailActivity extends SherlockFragmentActivity {
	
	
	private ImageView image;
	private final static String TAG = "DetailActivity";
	

	
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
		inflater.inflate(R.menu.main_list_menu,  menu);
		return super.onCreateOptionsMenu(menu);
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_search) {
			onSearchRequested();
			Log.d(TAG, "onSearchRequested");
			return true;
		}
		return false;
	}
	

	

} 