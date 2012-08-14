package com.keepard.activities;

import util.ParsingUtil;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListFragment;
import com.keepard.R;
import com.keepard.adapters.CompanyCursorAdapter;
import com.keepard.models.Card;
import com.keepard.models.Company;

public class CompanyListFragment extends SherlockListFragment {

	private boolean isShowed = false;
	private String TAG  = "CompanyListFragment";
	private  CompanyCursorAdapter mAdapter;
	private Animation little_up_animation;
	 FrameLayout layout_MainMenu;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
        setUpAdapter();
        setUpTitle();
        getListView().setCacheColorHint(Color.TRANSPARENT);
        little_up_animation = AnimationUtils.loadAnimation(getActivity(), R.anim.little_up_animation);
        layout_MainMenu = (FrameLayout) getActivity().findViewById( R.id.mainmenu);
        layout_MainMenu.getForeground().setAlpha(0);
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
	
  public static class ImageHelper {
	    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
	        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
	                .getHeight(), Config.ARGB_8888);
	        Canvas canvas = new Canvas(output);

	        final int color = 0xff424242;
	        final Paint paint = new Paint();
	        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
	        final RectF rectF = new RectF(rect);
	        final float roundPx = pixels;

	        paint.setAntiAlias(true);
	        canvas.drawARGB(0, 0, 0, 0);
	        paint.setColor(color);
	        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

	        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	        canvas.drawBitmap(bitmap, rect, rect, paint);

	        return output;
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


	
	   
}
