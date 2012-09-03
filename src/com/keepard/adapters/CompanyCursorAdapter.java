package com.keepard.adapters;

import util.Constants;
import util.ParsingUtil;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.keepard.R;
import com.keepard.activities.CardActivity;
import com.keepard.activities.CardAddingActivity;
import com.keepard.activities.dialogs.CodeReadingDialog;
import com.keepard.models.Card;
import com.keepard.models.Company;

public class CompanyCursorAdapter extends SimpleCursorAdapter {
	
	private Context context;
	private static final String TAG = "CompanyCursorAdapter";
	private int count;
	private Cursor cursor;
	static boolean isCreated = false;
	static boolean isPrepared = false;
	Animation little_up_animation;
	private int currentId;
	private int globalPosition = -1;
	
	public CompanyCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c, from, to);
		this.context = context;
		this.cursor = c;
		//little_up_animation = AnimationUtils.loadAnimation(context, R.anim.little_up_animation);
	}


	@TargetApi(14)
	public View getView(final int position, View convertView, ViewGroup parent) {
		 LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	      final Cursor c =  getCursor();
	      c.moveToPosition(position);
	      globalPosition = position;
	      if (c.getString(c.getColumnIndex("code")).equals("")) {
	    	  Log.d(TAG, "No code");
		      convertView = inflater.inflate(R.layout.list_example_entry, null);
		      convertView.setBackgroundResource(R.drawable.card_empty_selector);
		      convertView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (cursor.isClosed()) {
						 cursor =  getCursor();
					}
					cursor.moveToPosition(position);
					currentId = cursor.getInt(c.getColumnIndex("_id"));
					showCodeReadingDialog(currentId);
					Log.d(TAG, "Passed id = " + currentId);
					//Intent add_card = new Intent(context, CardAddingActivity.class);
					//context.startActivity(add_card);
				}
			});
		      
	      } else {
	    	  Log.d(TAG, "code YES ");
	    	  Log.d(TAG, "code = " + c.getInt(c.getColumnIndex("code")));
	    	  String name = c.getString(c.getColumnIndex("name")).toLowerCase();
		      Log.d(TAG, "cusorName = " + name);
		      convertView = inflater.inflate(R.layout.list_example_entry, null);
		      TextView nameV = (TextView)convertView.findViewById(R.id.name_entry);
		      nameV.setVisibility(View.GONE);
		      TextView descV = (TextView)convertView.findViewById(R.id.number_entry);
		      descV.setVisibility(View.GONE);
		      int resID = context.getResources().getIdentifier("zdb_" + name + "_mini", "drawable", "com.keepard");
		      Log.d(TAG, "resID = " + resID);
		      convertView.setBackgroundResource(resID);
		      convertView.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if (cursor.isClosed()) {
							 cursor =  getCursor();
						}
						cursor.moveToPosition(position);
						Company company = ParsingUtil.getCompanyFromCursor(cursor);
						Intent show_card = new Intent(context, CardActivity.class);
						show_card.setAction(Constants.ACTION_VIEW_CARD);
						show_card.putExtra("company", company);
						Log.d(TAG, "company to pass = " + company);
						context.startActivity(show_card);
					}
				});
	      }
	     super.getView(position, convertView, parent);
         return convertView;
	}
	
	private void showCodeReadingDialog(int id) {
        FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
        CodeReadingDialog codeReadingDialog = CodeReadingDialog.newInstance(id);
        codeReadingDialog.show(fm, context.getString(R.string.read_code));
    }

	public int getCurrentItemId() {
		return currentId;
	}
}
