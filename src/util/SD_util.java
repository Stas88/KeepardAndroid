package util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class SD_util {
	
	private final static String TAG = "SD_util";


	
	public  static void saveCardImage(Bitmap bitmap, String img_name) {
		FileOutputStream out = null;
		try {
			String pathExternal = Environment.getExternalStorageDirectory().toString();
			String pathRecipesFavorites = pathExternal + "/Keepard/KeepardImages";
			Log.d(TAG, "path of storage = " + Environment.getExternalStorageDirectory().toString());
			new File(pathRecipesFavorites).mkdirs();
			Log.d(TAG, "file path = " + pathRecipesFavorites);
			Log.d(TAG, "file path imagename= " + img_name);
			File file = new File(pathExternal, "/Keepard/KeepardImages/" +  img_name);
			file.createNewFile();
			out = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
		} catch (IOException e)  {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
			        out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static boolean deleteImageFromSD(String imageName) {
		Log.d(TAG, "Deleting image: " + imageName);
		String pathExternal = Environment.getExternalStorageDirectory().toString();
		File file = new File(pathExternal, "/Nyam/NyamRecipesFavorites/" + imageName);
		boolean deleted = file.delete();
		return deleted;
	}
	
}
