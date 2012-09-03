package com.keepard.activities;

import util.Util;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.keepard.R;
import com.keepard.activities.dialogs.CardDeletingDialog;
import com.keepard.models.Card;
import com.keepard.models.Company;


public class CardActivity extends FragmentActivity {
	
	private final String TAG = "CardActivity";
	private LayoutInflater inflater;
	private View cardView;
	private View cardInfoView;
	private Company company;
	private String action;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.card_view);
		inflater = LayoutInflater.from(this);
		cardView = inflater.inflate(R.layout.card_view, null);
		
		Intent intent = getIntent();
		action = intent.getAction();
		Log.d(TAG, "IntentAction: " + action);
		
		try {
			initCardViews(cardView);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setContentView(cardView);
		/*
		ViewPager pager = new ViewPager(this);
		List<View> pages = new ArrayList<View>();
		CardPagerAdapter adapter = new CardPagerAdapter(pages);
		pager.setAdapter(adapter);
		pager.setCurrentItem(0);     
		setContentView(pager);
		
		
		cardView = inflater.inflate(R.layout.card_view, null);
		initCardViews(cardView);
		pages.add(cardView);
		
		
		cardInfoView = inflater.inflate(R.layout.card_info_layout, null);
		initCardInfoViews(cardInfoView);
		pages.add(cardInfoView);
	    */
	}
	
	private void initCardViews(View cardView) throws WriterException {
		company = (Company) getIntent().getSerializableExtra("company");
		ImageView cardImage = (ImageView) cardView.findViewById(R.id.card_image);
		TextView cardImageNumber = (TextView) cardView.findViewById(R.id.card_image_number);
		//TextView cardTitle = (TextView) cardView.findViewById(R.id.card_title);
		ImageView cardInfoImage = (ImageView) cardView.findViewById(R.id.card_image_data);
	    int resID = this.getResources().getIdentifier("zdb_" + company.getName().toLowerCase() , "drawable", "com.keepard");
	    Log.d(TAG, "name = " + company.getName().toLowerCase());
	    Log.d(TAG, "code = " + company.getCode());
	    Log.d(TAG, "company = " + company);
	    Log.d(TAG, "code_format = " + company.getCode_format());
	    Intent intent = new Intent("com.google.zxing.client.android.ENCODE");
	    intent.putExtra("ENCODE_FORMAT", company.getCode_format());
	    intent.putExtra("ENCODE_DATA", String.valueOf(company.getCode()));
	    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
	    Display display = manager.getDefaultDisplay();
	    int width = display.getWidth();
	    int height = display.getHeight();
	    int smallerDimension = width < height ? width : height;
	    smallerDimension = smallerDimension * 7 / 8;
	    com.google.zxing.client.android.encode.QRCodeEncoder qrCodeEncoder = new com.google.zxing.client.android.encode.QRCodeEncoder(this, intent, smallerDimension, false);
	    Bitmap bitmapOld = qrCodeEncoder.encodeAsBitmap();
	    Bitmap bitmapOld1 = getResizedBitmap(bitmapOld);
	    Bitmap bitmap = Util.getRoundedCornerBitmap(bitmapOld1, 10);
	    //View view = (View)findViewById(R.layout.encode);
	    //ImageView view1 = (ImageView)view.findViewById(R.id.image_view);
	    //Drawable drawable1 = view1.getDrawable();
	    Drawable drawable = this.getResources()
               .getDrawable(resID);
	    Bitmap bm = ((BitmapDrawable) drawable).getBitmap();
	    
	    cardImage.setImageBitmap(bm);
	    
	    //Drawable drawable1 = this.getResources()
                //.getDrawable(R.drawable.bg_grey);
	    //Bitmap bm1 = ((BitmapDrawable) drawable1).getBitmap();
	    cardInfoImage.setImageBitmap(bitmap);
	    //cardTitle.setText(company.getName());
	    cardImageNumber.setText(String.valueOf(company.getCode()));
	    Button left_view = (Button)cardView.findViewById(R.id.left_button);
	    Button right_view = (Button)cardView.findViewById(R.id.right_button);
	    left_view.setText(R.string.ok);
	    right_view.setText(R.string.delete);
	    left_view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (action.equals(util.Constants.ACTION_NEW_CARD)) {
					ContentValues values = new ContentValues();
			        values.put(Card.CODE, company.getCode());
			        values.put(Card.CODE_FORMAT, company.getCode_format());
			        Log.d(TAG, "updated_id = " + company.get_ID());
			        Log.d(TAG, "company saving = " + company);
			        getContentResolver().update(Card.CONTENT_URI, values, Card.CARD_ID + " = ? ", new String [] {String.valueOf(company.get_ID())}); 
				} 
		        CardActivity.this.finish();
			}
		});
	    
	    right_view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showCodeDeleatingDialog(company.get_ID());
			}
		});
	}
	
	
	/*
	private void initCardInfoViews(View cardView) {
		Company company = (Company) getIntent().getSerializableExtra("company");
		cardView.setOnClickListener((android.view.View.OnClickListener) this);
		TextView name = (TextView)cardView.findViewById(R.id.name_entry);
		TextView descr = (TextView)cardView.findViewById(R.id.description_entry);
		TextView code = (TextView)cardView.findViewById(R.id.code_entry);
		name.setText(company.getName());
		descr.setText(company.getDescription());
		code.setText(String.valueOf(company.getCode()));
		 RelativeLayout fr = (RelativeLayout)cardView.findViewById(R.id.fr);
		ImageView left_button = (ImageView)fr.findViewById(R.id.left_button);
	    ImageView right_button = (ImageView)fr.findViewById(R.id.right_button);
	    left_button.setOnClickListener(this);
	}
	 */
	


	private void showCodeDeleatingDialog(int _id) {
        FragmentManager fm = ((FragmentActivity) this).getSupportFragmentManager();
        CardDeletingDialog codeDeletingDialog = CardDeletingDialog.newInstance(_id, company);
        codeDeletingDialog.show(fm, this.getString(R.string.read_code));
    }
	
	public Bitmap getResizedBitmap(Bitmap bm) {
		Bitmap resizedBitmap = null;
		if ( bm != null) {
	        int width = bm.getWidth();
	        int height = bm.getHeight();
	        float scaleWidth = ((float) width) / width;
	        float scaleHeight = ((float)(height/2))  / height;
	        // create a matrix for the manipulation
	        Matrix matrix = new Matrix();
	        // resize the bit map
	        matrix.postScale(scaleWidth, scaleHeight);
	        // recreate the new Bitmap
	        resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);
		}
        return resizedBitmap;
        

    }

	
}
