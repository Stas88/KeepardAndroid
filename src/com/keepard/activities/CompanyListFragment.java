package com.keepard.activities;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter.CursorToStringConverter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.FilterQueryProvider;
import android.widget.FrameLayout;

import com.keepard.R;
import com.keepard.adapters.CompanyCursorAdapter;
import com.keepard.models.Card;

public class CompanyListFragment extends ListFragment {

	private boolean isShowed = false;
	private String TAG  = "CompanyListFragment";
	private  CompanyCursorAdapter mAdapter;
	private Animation little_up_animation;
	FrameLayout layout_MainMenu;
	private EditText filterText = null;
	ArrayAdapter<String> adapter = null;
	private View fragmentView;


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
    
        getListView().setCacheColorHint(Color.TRANSPARENT);
        //little_up_animation = AnimationUtils.loadAnimation(getActivity(), R.anim.little_up_animation);
        //layout_MainMenu = (FrameLayout) getActivity().findViewById( R.id.mainmenu);
        //layout_MainMenu.getForeground().setAlpha(0);
        filterText = (EditText) getActivity().findViewById(R.id.search_box);
	    filterText.addTextChangedListener(filterTextWatcher);
	}


	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	   Bundle savedInstanceState) {
		fragmentView =  inflater.inflate(R.layout.list_example, container, false);
	  return fragmentView;
	 }
	
	

  
  
  private void setUpAdapter() {
	  Cursor cursor1 = getActivity().getContentResolver().query(Card.CONTENT_URI, Card.projection, null, null, Card.CODE);
	  //Cursor cursor2 = getActivity().getContentResolver().query(Card.CONTENT_URI, Card.projection, Card.CODE + " IS NULL", null, null);
	  //Cursor [] cursors = new Cursor [] {cursor1, cursor2};
	  //MergeCursor merge = new MergeCursor(cursors);
	  getActivity().startManagingCursor(cursor1);
	// the desired columns to be bound
      String[] columns = new String[] { Card.NAME, Card.DESCRIPTION };
      int[] to = new int[] { R.id.name_entry, R.id.number_entry };
      // create the adapter using the cursor pointing to the desired data as well as the layout information
      mAdapter = new CompanyCursorAdapter(getActivity(), R.layout.list_example_entry, cursor1, columns, to);
      // set this adapter as your ListActivity's adapter

      this.setListAdapter(mAdapter);
      mAdapter.setCursorToStringConverter(new CursorToStringConverter() {
          public String convertToString(android.database.Cursor cursor) {
              // Get the label for this row out of the "state" column
              final int columnIndex = cursor.getColumnIndexOrThrow("name");
              final String str = cursor.getString(columnIndex);
              Log.d(TAG, "string filter  + " + str);
              return str;
          }
      });
      
      mAdapter.setFilterQueryProvider(new FilterQueryProvider() {
          public Cursor runQuery(CharSequence constraint) {
              // Search for states whose names begin with the specified letters.
        	  String con = constraint.toString();
        	  Log.d(TAG, "constraint  + " + con);
              Cursor cursor = getActivity().getContentResolver().query(Card.CONTENT_URI, Card.projection, Card.NAME + " LIKE ?" , new String[] { con+"%" }, null);
              getActivity().startManagingCursor(cursor);
              if (cursor.getCount() < 1) {
            	  Log.d(TAG, "filtering cursor is empty ");
              } else {
            	  cursor.moveToFirst();
            	  Log.d(TAG, "filtering cursor = " + cursor.getString(cursor.getColumnIndex("name")));
              }
              return cursor;
          }
      });
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
