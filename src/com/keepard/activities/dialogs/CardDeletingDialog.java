package com.keepard.activities.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.keepard.R;
import com.keepard.activities.CardActivity;
import com.keepard.models.Card;
import com.keepard.models.Company;

public class CardDeletingDialog extends DialogFragment {
	
	
	private int _id;
	private  CardActivity ownerActivity; 
	private Company company;
	private final String TAG = "CardDeletingDialog";
	
	private CardDeletingDialog(int _id, Company company) {
		this.company = company;
		this._id = _id;
	}
	
	public static CardDeletingDialog newInstance(int _id, Company company) {
		CardDeletingDialog frag = new CardDeletingDialog(_id, company);
        return frag;
    }

	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.card_deleting_question)
        .setCancelable(false)
        .setTitle(getActivity().getString(R.string.card_deleting))
        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	ContentValues values = new ContentValues();
		        values.put(Card.CODE, "");
		        values.put(Card.CODE_FORMAT, "");
		        getDialog().getOwnerActivity().getContentResolver().update(Card.CONTENT_URI, values, Card.CARD_ID + " = ? ", new String [] {String.valueOf(company.get_ID())}); 
            	dialog.cancel();
            	getDialog().getOwnerActivity().finish();
            }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                 dialog.cancel();
            }
        });
        return builder.create();
    }

}
