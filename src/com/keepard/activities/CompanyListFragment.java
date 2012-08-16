package com.keepard.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter.CursorToStringConverter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.FilterQueryProvider;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListFragment;
import com.keepard.R;
import com.keepard.adapters.CompanyCursorAdapter;
import com.keepard.models.Card;

public class CompanyListFragment extends SherlockListFragment {

	private boolean isShowed = false;
	private String TAG  = "CompanyListFragment";
	private  CompanyCursorAdapter mAdapter;
	private Animation little_up_animation;
	FrameLayout layout_MainMenu;
	private EditText filterText = null;
	ArrayAdapter<String> adapter = null;


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
        setUpAdapter();
        
		
		/*
		String [] toppings = new String[3];
		  toppings[0] = "Cheese";
		  toppings[1] = "Pepperoni";
		  toppings[2] = "Black Olives";
		  adapter = new ArrayAdapter<String>(getActivity(),
	                android.R.layout.simple_list_item_1, 
	                toppings);
        setListAdapter(adapter);
        */
        setUpTitle();
        getListView().setCacheColorHint(Color.TRANSPARENT);
        little_up_animation = AnimationUtils.loadAnimation(getActivity(), R.anim.little_up_animation);
        layout_MainMenu = (FrameLayout) getActivity().findViewById( R.id.mainmenu);
        layout_MainMenu.getForeground().setAlpha(0);
        filterText = (EditText) getActivity().findViewById(R.id.search_box);
	    filterText.addTextChangedListener(filterTextWatcher);
	}

	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	   Bundle savedInstanceState) {
		View v =  inflater.inflate(R.layout.list_example, container, false);
	  return v;
	 }
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		if (position != l.getCount() -1 && position != l.getCount() -2) {
				/*
				Intent cardActivity = new Intent(getActivity(), CardActivity.class);
			    Cursor c = ((CursorAdapter) l.getAdapter()).getCursor();
			    c.moveToPosition(position);
			    Company company = ParsingUtil.getCompanyFromCursor(c);
			    cardActivity.putExtra("company", company);
				startActivity(cardActivity);
				
				/*
				View spaceshipImage = l;
				Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.hyperspace_jump);
				Animation little_up_animation = AnimationUtils.loadAnimation(getActivity(), R.anim.little_up_animation);
				for (int i = 0; i < l.getChildCount(); i ++) {
					View v1 = l.getChildAt(i);
					if (!v1.equals(v)) { 
						v1.startAnimation(hyperspaceJumpAnimation);
						v1.setVisibility(View.GONE);
					}
					 Drawable drawable = getActivity().getResources()
				                .getDrawable(R.drawable.card_full);
					v.setBackgroundDrawable(drawable);
					
					v.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					
					//getActivity().getWindow().setFlags(flags, mask)
					LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					View dimmingView = inflater.inflate(R.layout.dimming_view, null);
					((ViewGroup) v.getParent().getParent()).addView(dimmingView);
				}
				*/
	    
		} else if (position == l.getCount() -2) {
			Intent add_card = new Intent(getActivity(), CardAddingActivity.class);
			startActivity(add_card);
		}
	}
	

  
  
  private void setUpAdapter() {
	  Cursor cursor = getActivity().getContentResolver().query(Card.CONTENT_URI, new String[] {Card.CARD_ID, Card.NAME, Card.CODE, Card.DESCRIPTION, Card.CARD_IMAGE, Card.IMAGE}, null, null, null);
      getActivity().startManagingCursor(cursor);
	// the desired columns to be bound
      String[] columns = new String[] { Card.NAME, Card.DESCRIPTION };
      // the XML defined views which the data will be bound to
      int[] to = new int[] { R.id.name_entry, R.id.number_entry };
      // create the adapter using the cursor pointing to the desired data as well as the layout information
      mAdapter = new CompanyCursorAdapter(getActivity(), R.layout.list_example_entry, cursor, columns, to);
      // set this adapter as your ListActivity's adapter
      this.setListAdapter(mAdapter);
      mAdapter.setFilterQueryProvider(new FilterQueryProvider() {
          public Cursor runQuery(CharSequence constraint) {
              // Search for states whose names begin with the specified letters.
        	  String con = constraint.toString();
        	  Log.d(TAG, "constraint  + " + con);
              Cursor cursor = getActivity().getContentResolver().query(Card.CONTENT_URI, new String[] {Card.CARD_ID, Card.NAME, Card.CODE, Card.DESCRIPTION, Card.CARD_IMAGE, Card.IMAGE}, Card.NAME + " LIKE ? ", new String[] { con }, null);
              
              return cursor;
          }
      });
      
      mAdapter.setCursorToStringConverter(new CursorToStringConverter() {
          public String convertToString(android.database.Cursor cursor) {
        	 
              // Get the label for this row out of the "state" column
              final int columnIndex = cursor.getColumnIndexOrThrow("name");
              final String str = cursor.getString(columnIndex);
              Log.d(TAG, "string filter  + " + str);
              return str;
          }
      });
      
  }
  
  private void setUpTitle() {
	  String number = "";
	  if (mAdapter != null && mAdapter.getCount() != 0) {
		  number = "(" + String.valueOf(mAdapter.getCount()) + ")";
	  } else {
		  number = "(0)";
	  }
	  ((SherlockFragmentActivity)getActivity()).getSupportActionBar().setTitle("Мои дисконты " + number);
  }


  private TextWatcher filterTextWatcher = new TextWatcher() {

	    public void afterTextChanged(Editable s) {
	    }

	    public void beforeTextChanged(CharSequence s, int start, int count,
	            int after) {
	    }

	    
	    public void onTextChanged(CharSequence s, int start, int before,
	            int count) {
	    	Log.d(TAG, "filtering + " + s);
	    	Filter filter = mAdapter.getFilter();
	    	filter.filter(s);
	    }
	};

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    filterText.removeTextChangedListener(filterTextWatcher);
	}
	   
}
