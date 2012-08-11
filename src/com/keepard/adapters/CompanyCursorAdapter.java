package com.keepard.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.keepard.R;

public class CompanyCursorAdapter extends SimpleCursorAdapter {
	
	private Context context;
	private static final String TAG = "CompanyCursorAdapter";
	private int count;
	private Cursor cursor;
	static boolean isCreated = false;
	static boolean isPrepared = false;
	Animation little_up_animation;
	
	public CompanyCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c, from, to);
		this.context = context;
		this.cursor = c;
		little_up_animation = AnimationUtils.loadAnimation(context, R.anim.little_up_animation);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		 LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 	 Log.d(TAG, "isCreated = " + isCreated);
	 	  if ((position == (cursor.getCount() - 1))) {
	    	  convertView = inflater.inflate(R.layout.no_card_item, null);
	    	  super.getView(position, convertView, parent);   
	          return convertView;
	      }
	      if((position == (cursor.getCount() - 2))) {
	    	  convertView = inflater.inflate(R.layout.add_list_item, null);

	    	  super.getView(position, convertView, parent);
	          return convertView;
	      }  
	      Cursor c = getCursor();
	      c.moveToPosition(position);
	      String name = c.getString(c.getColumnIndex("name")).toLowerCase();
	      Log.d(TAG, "cusorImage = " + name);
	      convertView = inflater.inflate(R.layout.list_example_entry, null);
	      int resID = context.getResources().getIdentifier("zdb_" + name + "_mini", "drawable", "com.keepard");
	      convertView.setBackgroundResource(resID);
	      
	     super.getView(position, convertView, parent);
         return convertView;
	}
	


}
