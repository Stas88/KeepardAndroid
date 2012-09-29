package com.keepard.activities;

import util.SD_util;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.keepard.R;
import com.keepard.activities.dialogs.FieldsAreEmptyDialog;
import com.keepard.models.Card;
import com.keepard.views.RoundedImageView;

public class CardAddingFragment extends Fragment  {
	
	private Spinner sp;
	private TextView descriptionView;
	private Button read_code;
	private ImageView right_button;
	private final String TAG = "CardAddingFragment";
	private Uri imageUri;
	private View v;
	private EditText name;
	private EditText description;
	
	private ImageView card_image_data;
	
	
	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	   Bundle savedInstanceState) {
	   v =  inflater.inflate(R.layout.card_adding_layout_for_fragment, container, false);
	  return v;
	 }
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (v != null) {
			initCardViews(v);
		}
		super.onActivityCreated(savedInstanceState);
		
	}



	private void initCardViews(View cardView) {
		Button left_view = (Button)cardView.findViewById(R.id.left_button);
	    Button right_view = (Button)cardView.findViewById(R.id.right_button);
	    left_view.setText(R.string.cancel);
	    right_view.setText(R.string.ok);
	    name = (EditText)(cardView.findViewById(R.id.company_name_value));
	    card_image_data = (ImageView)(cardView.findViewById(R.id.card_image_data));
	    
	    left_view.setOnClickListener(new OnClickListener() {
	    
	        @Override
			public void onClick(View v) {
		        getActivity().finish();
			}
		});
	    
	    right_view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				String nameStr = name.getText().toString();

				Bitmap bitmapToSave = ((CardAddingActivity)getActivity()).getPhotoCard();
				
				String code = ((CardAddingActivity)getActivity()).getCode();
				String codeFormat = ((CardAddingActivity)getActivity()).getCodeFormat();
				Log.d(TAG, "code = " + code);
				Log.d(TAG, "bitmap = " + bitmapToSave);
				Log.d(TAG, "codeFormat = " + codeFormat);
				if (bitmapToSave == null || code == null ||
					name.getText().toString().equals("") || codeFormat == null) {
					showEmptyFieldsDialog();
					return;
				}
				
				ContentValues values = new ContentValues();
		        values.put(Card.NAME, nameStr);
		        values.put(Card.CODE, code);
		        values.put(Card.CODE_FORMAT, codeFormat);
		        getActivity().getContentResolver().insert(Card.CONTENT_URI, values); 
		        SD_util.saveCardImage(bitmapToSave, nameStr);
		        getActivity().finish();
			}
		});
	}
	
	

	private void showEmptyFieldsDialog() {
        FragmentManager fm = ((FragmentActivity) getActivity()).getSupportFragmentManager();
        FieldsAreEmptyDialog emptyFieldsDialog = FieldsAreEmptyDialog.newInstance();
        emptyFieldsDialog.show(fm, getActivity().getString(R.string.read_code));
    }

	
}
