package com.keepard.activities.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.keepard.R;
import com.keepard.activities.DetailActivity;

public class FieldsAreEmptyDialog extends DialogFragment{

	private FieldsAreEmptyDialog() {
	}
	
	private FieldsAreEmptyDialog(int id) {}

	public static FieldsAreEmptyDialog newInstance() {
		FieldsAreEmptyDialog frag = new FieldsAreEmptyDialog();
        return frag;
    }

	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.empty_fields)
        .setCancelable(false)
        .setTitle(getActivity().getString(R.string.card_adding))
        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	dialog.cancel();
            }
        });
        
        return builder.create();
    }

	

	/*
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
        View view = inflater.inflate(R.layout.fragment_read_code, container);
        TextView tx = (TextView)view.findViewById(R.id.lbl_your_name);
        getDialog().setTitle(R.string.card_adding);
        tx.setText(R.string.read_code_question);
        getDialog().set
        return view;
    }
	*/
	
	
}
