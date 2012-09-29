package util;

import android.database.Cursor;
import android.util.Log;

import com.keepard.models.Company;

public class ParsingUtil {
	
	private final static String TAG = "ParsingUtil";

	
	public static Company getCompanyFromCursor(Cursor c) {
		Company company = new Company();
		Log.d(TAG, "id = " + c.getInt(c.getColumnIndex("_id")));
		company.set_ID(c.getInt(c.getColumnIndex("_id")));
		Log.d(TAG, "name = " + c.getString(c.getColumnIndex("name")));
		company.setName(c.getString(c.getColumnIndex("name")));
		Log.d(TAG, "eng_name = " + c.getString(c.getColumnIndex("eng_name")));
		company.setEngName(c.getString(c.getColumnIndex("eng_name")));
		
		Log.d(TAG, "description = " + c.getString(c.getColumnIndex("description")));
		company.setDescription(c.getString(c.getColumnIndex("description")));
		Log.d(TAG, "code = " + c.getLong(c.getColumnIndex("code")));
		company.setCode(c.getLong(c.getColumnIndex("code")));
		Log.d(TAG, "code_format = " + c.getString(c.getColumnIndex("code_format")));
		company.setCode_format(c.getString(c.getColumnIndex("code_format")));
		
		return company;
	} 
}
