package util;

import android.database.Cursor;
import android.util.Log;

import com.keepard.models.Company;

public class ParsingUtil {
	
	private final static String TAG = "ParsingUtil";

	
	public static Company getCompanyFromCursor(Cursor c) {
		Company company = new Company();
		Log.d(TAG, "name = " + c.getString(c.getColumnIndex("name")));
		company.setName(c.getString(c.getColumnIndex("name")));
		
		Log.d(TAG, "description = " + c.getString(c.getColumnIndex("description")));
		company.setDescription(c.getString(c.getColumnIndex("description")));
		Log.d(TAG, "code = " + c.getInt(c.getColumnIndex("code")));
		company.setCode(c.getInt(c.getColumnIndex("code")));
		return company;
	} 
}
