package com.keepard.activities.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.keepard.R;

public class CardDeletingDialog extends DialogFragment {
	
	private CardDeletingDialog() {}
	
	public static CardDeletingDialog newInstance() {
		CardDeletingDialog frag = new CardDeletingDialog();
        return frag;
    }

	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.card_deleting_question)
        .setCancelable(false)
        .setTitle(getActivity().getString(R.string.card_deleting))
        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	dialog.cancel();
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
