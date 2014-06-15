package at.lvmaster3000.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import at.lvmaster3000.settings.DBsettings;

public class HLPResources extends SQLiteOpenHelper {
	
	//table name
	public static final String TABLE_NAME = "resources";
	
	//table columns
	public static final String COL_ID = "_id";
	public static final String COL_TITLE = "title";
	
	private String logtag = DBsettings.LOG_TAG_RESOURCES;
	
	
	//columns list
	public static final String[] allColumns = {COL_ID, COL_TITLE};
	
	//create string
	private static final String RESOURCES_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
												COL_ID + " integer primary key autoincrement, " +
												COL_TITLE  + " text not null); ";
	
	//database adapter
	private SQLiteDatabase db;
	
	/**
	 * Constructor
	 * 
	 * @param context	Per default "this" in main class
	 */
	public HLPResources(Context context) {
		super(context, DBsettings.DATABASE_NAME, null, DBsettings.DATABASE_VERSION);
		Log.i(logtag, "constructor: Resources helper created");
	}
	
	/**
	 * Just for debugging. Function returns the resources table creation string
	 * 
	 * @return	String
	 */
	public String getCreationString() {
		return RESOURCES_CREATE;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(logtag, RESOURCES_CREATE);
		db.execSQL(RESOURCES_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(logtag, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		this.onCreate(db);		
	}
	
	/**
	 * Function opens a DB adapter and returns it, if needed
	 * 
	 * @return	SQLiteDatabase
	 */
	public SQLiteDatabase openCon() {
		db = this.getWritableDatabase();
		
		return db;
	}
		
	/**
	 * Function closes the DB adapter, if open
	 */
	public void closeCon() {
		db.close();
	}
	
	/**
	 * 
	 */
	public void deleteTable() {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		Log.i(logtag, "Table '" + TABLE_NAME + "' deleted");
	}
	
	/**
	 * Function resets resources table and recreates it
	 */
	public void resetTable() {		
		this.deleteTable();
		this.onCreate(db);
		Log.i(logtag, "Table '" + TABLE_NAME + "' reseted");
	}
	
	/**
	 * Function inserts a new record into the resources table
	 * @param title
	 * @return
	 */
	
	public long addResource(String title) {
		ContentValues values = new ContentValues();
		
		values.put(COL_TITLE, title);
		
		long insertId = db.insert(TABLE_NAME, null, values);
		
		Log.i(logtag, "New entry added. ID: "+ insertId);
		
		return insertId;
	}
	
	/**
	 * Function returns all resources stored in DB as list
	 */
	public void allEntriesToLog() {		
		Cursor cursor = db.query(TABLE_NAME, allColumns, null, null, null, null, null);
				
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {			
			String logstr = "";
			
			logstr += "ID: " + cursor.getLong(0) + " | ";
			logstr += "Title: " + cursor.getString(1) + " | ";	
			
			Log.i(logtag, logstr);
			
			cursor.moveToNext();
		}		
		
		cursor.close();
	}

	/**
	 * Function deletes a word from DB
	 * @param id
	 */
	public int deleteResource(long id) {
		Log.i(logtag, "Word deleted. ID: " + id);
		return db.delete(TABLE_NAME, COL_ID + " = " + id, null);
	}

}
