package com.keepard.activities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.keepard.R;

public class CardAddingActivity extends FragmentActivity{
	
	private static final String TAG = "CardAddingActivity";
	private ImageView imageView;
	private ImageButton  make_photo_button;
	private TextView add_photo_text;
	private ImageButton  make_code_button;
	private TextView add_code_text;
	private TextView cardImageNumber;
	private ImageView cardInfoImage;
	private LinearLayout card_view_layout;
	
	private Bitmap readyBitmap;
	private Bitmap photoCard;
	private String code;
	private String codeFormat;
	
	public Bitmap getPhotoCard() {
		return photoCard;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getCodeFormat() {
		return codeFormat;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		setContentView(R.layout.card_adding_layout);
		imageView = (ImageView) findViewById(R.id.card_image_adding);
		make_photo_button = (ImageButton)findViewById(R.id.make_photo_button);
		add_photo_text = (TextView)findViewById(R.id.add_photo_text);
		make_code_button = (ImageButton)findViewById(R.id.read_code_button);
		add_code_text = (TextView)findViewById(R.id.add_code_text);
		cardImageNumber = (TextView)findViewById(R.id.card_image_number);
		cardInfoImage = (ImageView)findViewById(R.id.card_image_data);
		card_view_layout = (LinearLayout)findViewById(R.id.card_image_linearlayout);
	}
	
	@Override
	  protected void onStart() {
	    super.onStart();
	    Log.d(TAG, "onStart()");
	  }
	
	@Override
	  protected void onResume() {
	    super.onResume();
	    Log.d(TAG, "onResume");
	    if (readyBitmap != null) {
			 cardInfoImage.setImageBitmap(readyBitmap);
			 Log.d(TAG, "onResume 1");
		 }
		 if (code != null) {
			 Log.d(TAG, "onResume 2");
			 cardImageNumber.setText(code);
		 }
	    Log.d(TAG, "onResume()");
	  }

	  @Override
	  protected void onPause() {
	    super.onPause();
	    Log.d(TAG, " onPause()");
	  }

	  @Override
	  protected void onStop() {
	    super.onStop();
	    Log.d(TAG, "onStop()");
	  }
	    
	
	public void takePhoto(View view) {
	    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
	    startActivityForResult(intent, 0);
	}
	
	public void readCode(View view) {
		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
        startActivityForResult(intent, 1);
	}
	
	 @Override
	 protected void onDestroy() {
		 Log.d(TAG, "onDestroy");
	     super.onDestroy();
	 }


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    Log.d(TAG, "onActivityResult");
	    switch (requestCode) {
	    case 0:
	        if (resultCode == Activity.RESULT_OK) {
	        	if(data.hasExtra("data"))
	            {
	        		Log.d(TAG, "camera captured");
	        		Bundle extras = data.getExtras();
	        		photoCard = ((Bitmap)extras.get("data"));
	                imageView.setImageBitmap(getResizedBitmap((Bitmap)extras.get("data"), false));
	                imageView.setVisibility(View.VISIBLE);
	                make_photo_button.setVisibility(View.GONE);
	                add_photo_text.setVisibility(View.GONE);
	            }
	        }
	        break;
	        
	    case 1:
	    	if (resultCode == Activity.RESULT_OK) {
	    		Log.d(TAG, "code captured");
	    		 code = data.getStringExtra("SCAN_RESULT");
		         codeFormat = data.getStringExtra("SCAN_RESULT_FORMAT");
		         Log.d(TAG, "onActivityResult 2");
		         Log.d(TAG, "onActivityResult 3 + " + "ScanResult: " + code + " Format: " + codeFormat);
		         
		 	    Intent intent = new Intent("com.google.zxing.client.android.ENCODE");
		 	    intent.putExtra("ENCODE_FORMAT", codeFormat);
		 	    intent.putExtra("ENCODE_DATA", code);
		 	    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
		 	    Display display = manager.getDefaultDisplay();
		 	    int width = display.getWidth();
		 	    int height = display.getHeight();
		 	    int smallerDimension = width < height ? width : height;
		 	    smallerDimension = smallerDimension * 7 / 8;
		 	    com.google.zxing.client.android.encode.QRCodeEncoder qrCodeEncoder;
				try {
					qrCodeEncoder = new com.google.zxing.client.android.encode.QRCodeEncoder(this, intent, smallerDimension, false);
			 	    Bitmap bitmapOld = qrCodeEncoder.encodeAsBitmap();
			 	    readyBitmap = getResizedBitmap(bitmapOld, true);
			 	    cardInfoImage.setImageBitmap(readyBitmap);
			 	    cardImageNumber.setText(String.valueOf(code));
			 	    make_code_button.setVisibility(View.GONE);
	                add_code_text.setVisibility(View.GONE);
			 	    card_view_layout.setVisibility(View.VISIBLE);
				} catch (WriterException e) {
					e.printStackTrace();
				}
		          
	        }
	    	break;
	    }
	    
	}
	
	public Bitmap getResizedBitmap(Bitmap bm, boolean isCode) {
		Bitmap resizedBitmap = null;
		if ( bm != null) {
	        int width = bm.getWidth();
	        int height = bm.getHeight();
	        float scaleWidth;
	        float scaleHeight;
	        if (isCode) {
	        	scaleHeight = (float)0.4;
	        	scaleWidth = ((float) width/1.3f) / width;
	        } else {
	        	scaleHeight = (float)0.7;
	        	scaleWidth = ((float) width/2) / width;
	        }
	       
	        Matrix matrix = new Matrix();
	        matrix.postScale(scaleWidth, scaleHeight);
	        resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);
		}
        return resizedBitmap;
    }
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
	    // Add variable to outState here
		super.onSaveInstanceState(outState);
		Log.d(TAG, "onSaveInstanceState");				Log.d(TAG, "code = " + code);
		if (readyBitmap != null) {
			outState.putParcelable("readyBitmap", readyBitmap);
		}
		if (code != null) {
			outState.putString("code", code);
		}
	    
	}

	 @Override
	 protected void onRestoreInstanceState (Bundle savedInstanceState) {
		 Log.d(TAG, "onRestoreInstanceState");
		 Bitmap storedBitmap = (Bitmap)savedInstanceState.getParcelable("readyBtmap");
		 String code = (String)savedInstanceState.getString("code");
		 if (storedBitmap != null) {
			 cardInfoImage.setImageBitmap(storedBitmap);
			 Log.d(TAG, "onRestoreInstanceState 1");
		 }
		 if (code != null) {
			 Log.d(TAG, "onRestoreInstanceState 2");
			 cardImageNumber.setText(code);
		 }
	 }
	 
	/*
	class DownloadImageTask extends AsyncTask<Uri, Void, Bitmap> {
		
        @Override
        protected Bitmap doInBackground(Uri... params) {
        	Bitmap bitmap = null;
            try {
            	 Log.d(TAG, "doInBackground 1");
	             ContentResolver cr = getContentResolver();
                 //bitmap = android.provider.MediaStore.Images.Media
                 //.getBitmap(cr, params[0]);
                 Log.d(TAG, "doInBackground 2");
            } catch (Exception e) {
                Log.e("Camera", e.toString());
            }
            return bitmap;
        }
       
        @Override
        protected void onPostExecute(Bitmap result) {
             super.onPostExecute(result);
             //progress.dismiss();
             Log.d(TAG, "onPostExecute 1");
             if (result != null) {
            	 Log.d(TAG, "onPostExecute 2");
            	 imageView.setImageBitmap(result);
             }
        }
    }
	*/
}


