package com.keepard.content_providers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.keepard.models.Card;

public class CardsContentProvider extends ContentProvider {
	
	private static final String TAG = "CardsContentProvider";
	
	private static final String DATABASE_NAME = "keepard.sqlite";

	private static final String CARDS_TABLE_NAME = "cards";
	
	public static final String AUTHORITY = "com.keepard.content_providers.CardsContentProvider";
	
	private static final UriMatcher sUriMatcher;
	
	private static final int CARDS = 1;
	
	private static HashMap<String, String> cardsProjectionMap;
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		
		
	    private static final int DATABASE_VERSION = 2;
	    private static final String SP_KEY_DB_VER = "db_ver";
	    private final Context mContext;

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mContext = context;
            initialize();

        }
        
        private void initialize() {
            if (databaseExists()) {
                SharedPreferences prefs = PreferenceManager
                        .getDefaultSharedPreferences(mContext);
                int dbVersion = prefs.getInt(SP_KEY_DB_VER, 1);
                if (DATABASE_VERSION != dbVersion) {
                    File dbFile = mContext.getDatabasePath(DATABASE_NAME);
                    if (!dbFile.delete()) {
                        Log.w(TAG, "Unable to update database");
                    }
                }
            }
            if (!databaseExists()) {
                createDatabase();
            }
        }
        
        private boolean databaseExists() {
            File dbFile = mContext.getDatabasePath(DATABASE_NAME);
            return dbFile.exists();
        }

        private void createDatabase() {
            String parentPath = mContext.getDatabasePath(DATABASE_NAME).getParent();
            String path = mContext.getDatabasePath(DATABASE_NAME).getPath();

            File file = new File(parentPath);
            if (!file.exists()) {
                if (!file.mkdir()) {
                    Log.w(TAG, "Unable to create database directory");
                    return;
                }
            }

            InputStream is = null;
            OutputStream os = null;
            try {
                is = mContext.getAssets().open(DATABASE_NAME);
                os = new FileOutputStream(path);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
                os.flush();
                SharedPreferences prefs = PreferenceManager
                        .getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt(SP_KEY_DB_VER, DATABASE_VERSION);
                editor.commit();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
        
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      
        }
	
	}    
        
	private DatabaseHelper dbHelper;
	
	@Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return true;
    }
	
	@Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
            case CARDS:
                count = db.delete(CARDS_TABLE_NAME, where, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case CARDS:
                return Card.CONTENT_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        if (sUriMatcher.match(uri) != CARDS) { throw new IllegalArgumentException("Unknown URI " + uri); }

        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long rowId = db.insert(CARDS_TABLE_NAME, Card.DESCRIPTION, values);
        if (rowId > 0) {
            Uri noteUri = ContentUris.withAppendedId(Card.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }

        throw new SQLException("Failed to insert row into " + uri);
    }

   

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        switch (sUriMatcher.match(uri)) {
            case CARDS:
                qb.setTables(CARDS_TABLE_NAME);
                qb.setProjectionMap(cardsProjectionMap);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }
    

 


    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
            case CARDS:
                count = db.update(CARDS_TABLE_NAME, values, where, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, CARDS_TABLE_NAME, CARDS);

        cardsProjectionMap = new HashMap<String, String>();
        cardsProjectionMap.put(Card.CARD_ID, Card.CARD_ID);
        cardsProjectionMap.put(Card.CARD_IMAGE, Card.CARD_IMAGE);
        cardsProjectionMap.put(Card.CODE, Card.CODE);
        cardsProjectionMap.put(Card.DESCRIPTION, Card.DESCRIPTION);
        cardsProjectionMap.put(Card.IMAGE, Card.IMAGE);
        cardsProjectionMap.put(Card.NAME, Card.NAME);
        cardsProjectionMap.put(Card.CODE_FORMAT, Card.CODE_FORMAT);
    }
}





