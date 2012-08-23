package com.keepard.activities.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.keepard.R;

public class CodeReadingDialog extends DialogFragment {

	private CodeReadingDialog() {}
	
	public static CodeReadingDialog newInstance() {
		CodeReadingDialog frag = new CodeReadingDialog();
        return frag;
    }

	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.read_code_question)
        .setCancelable(false)
        .setTitle(getActivity().getString(R.string.card_adding))
        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	Intent intent = new Intent("com.google.zxing.client.android.SCAN");
 	            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
 	            startActivityForResult(intent, 0);
            }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
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
