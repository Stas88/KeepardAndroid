package com.keepard.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.keepard.R;
import com.keepard.models.Card;

public class CardAddingFragment extends Fragment implements OnClickListener {
	
	private Spinner sp;
	private TextView descriptionView;
	private Button read_code;
	private ImageView right_button;
	private final String TAG = "CardAddingFragment";
	
	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	   Bundle savedInstanceState) {
		View v =  inflater.inflate(R.layout.card_adding_layout_for_fragment, container, false);
		initCardViews(v);
		setUpSpinnerAdapter();
	  return v;
	 }
	
	private void setUpSpinnerAdapter() {

		  Cursor cursor = getActivity().getContentResolver().query(Card.CONTENT_URI, new String[] {Card.CARD_ID, Card.NAME, Card.CODE, Card.DESCRIPTION, Card.CARD_IMAGE, Card.IMAGE}, null, null, null);
	      getActivity().startManagingCursor(cursor);
		  // the desired columns to be bound
	      ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
	      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	      sp.setAdapter(adapter);

	        int accountnameIndex = cursor.getColumnIndexOrThrow("name");
	        if(cursor.moveToFirst()){
	            do{
	                adapter.add(cursor.getString(accountnameIndex));
	            } while(cursor.moveToNext());
	        }
	        
	        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	            	String selected = sp.getSelectedItem().toString();
	            	Cursor cursor = getActivity().getContentResolver().query(Card.CONTENT_URI, new String[] {Card.CARD_ID, Card.NAME, Card.CODE, Card.DESCRIPTION, Card.CARD_IMAGE, Card.IMAGE}, Card.NAME + " LIKE ? ", new String[] { selected }, null);
	            	if(cursor.moveToFirst()){
	            		descriptionView.setText(cursor.getString(cursor.getColumnIndex("description")));
	            	}
	            }

				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
				}
	        });
	  }
	
	private void initCardViews(View cardView) {
		LinearLayout data_layout = (LinearLayout)cardView.findViewById(R.id.data_layout);
		sp = (Spinner)cardView.findViewById(R.id.spinner_id);
		descriptionView = (TextView)cardView.findViewById(R.id.description_result_textview);
	    RelativeLayout fr = (RelativeLayout)cardView.findViewById(R.id.fr);
	    ImageView left_button = (ImageView)fr.findViewById(R.id.left_button);
	    right_button = (ImageView)fr.findViewById(R.id.right_button);
	    right_button.setOnClickListener(this);
	    read_code = (Button)data_layout.findViewById(R.id.read_code);
	    read_code.setOnClickListener(this);
	}
	

	public void onClick(View v) {
		  Log.d(TAG, "v.getId() = " + v.getId());
		  if( read_code.getId() == ((Button)v).getId() ){
			 Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
	      }
	   
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	   Toast.makeText(getActivity(), data.getStringExtra("SCAN_RESULT"), 1000).show();
	}

	
}
