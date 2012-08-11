package com.keepard.activities;

import android.os.Bundle;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.keepard.R;

public class DetailActivity extends SherlockFragmentActivity {
	
	
	ImageView image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_activity_layout);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		
		
		

	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main_list_menu,  menu);
		return super.onCreateOptionsMenu(menu);
		
		
	}
	
	
	
} 