package at.lvmaster3000.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import at.lvmaster3000.settings.DBsettings;

public class HLPDates extends SQLiteOpenHelper {

	//table name
	public static final String TABLE_NAME = "dates";
	
	//table columns
	public static final String COL_ID = "_id";
	public static final String COL_TIMESTAMP = "timestamp";
	public static final String COL_LOCATION = "location";
	public static final String COL_TYPE = "type";
	public static final String COL_COMMENT = "datecomment";
	
	private String logtag = DBsettings.LOG_TAG_DATES;
	
	//columns list
	public static final String[] allColumns = {COL_ID, COL_TIMESTAMP, COL_LOCATION, COL_TYPE, COL_COMMENT};
	
	//create string
	private static final String DATES_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
												COL_ID + " integer primary key autoincrement, " +
												COL_TIMESTAMP + " integer not null, " +
												COL_LOCATION + " text not null, " +
												COL_TYPE + " text not null, " +
												COL_COMMENT + " text not null);";
	
	//database adapter
	private SQLiteDatabase db;
	
	/**
	 * Constructor
	 * 
	 * @param context	Per default "this" in main class
	 */
	public HLPDates(Context context) {
		super(context, DBsettings.DATABASE_NAME, null, DBsettings.DATABASE_VERSION);
		Log.i(logtag, "constructor: Dates helper created");
	}
	
	/**
	 * Just for debugging. Function returns the table creation string
	 * 
	 * @return	String
	 */
	public String getCreationString() {
		return DATES_CREATE;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(logtag, DATES_CREATE);
		db.execSQL(DATES_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(logtag,
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);		
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
	
	public void deleteTable() {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		Log.i(logtag, "Table '" + TABLE_NAME + "' deleted");
	}
	
	/**
	 * Function resets table and recreates it
	 */
	public void resetTable() {		
		this.deleteTable();
		this.onCreate(db);
		Log.i(logtag, "Table '" + TABLE_NAME + "' reseted");
	}
	
	/**
	 * Function inserts a new record into the table
	 * @param timestamp
	 * @param location
	 * @param type
	 * @param comment
	 * @return 
	 */
	public long addDate(long timestamp, String location, String type, String comment) {
		ContentValues values = new ContentValues();
		
		values.put(COL_TIMESTAMP, timestamp);
		values.put(COL_LOCATION, location);
		values.put(COL_TYPE, type);	
		values.put(COL_COMMENT, comment);
		
		long insertId = db.insert(TABLE_NAME, null, values);
		
		Log.i(logtag, "New entry added. ID: " + insertId);
		
		return insertId;
	}
	
	/**
	 * Function returns all entries stored in DB as list
	 */
	public void allEntriesToLog() {		
		Cursor cursor = db.query(TABLE_NAME, allColumns, null, null, null, null, null);
		//COL_ID, COL_TIMESTAMP, COL_LOCATION, COL_TYPE, COL_COMMENT
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {			
			String logstr = "";
			
			logstr += "ID: " + cursor.getLong(0) + " | ";
			logstr += "Timestamp: " + cursor.getLong(1) + " | ";
			logstr += "Location: " + cursor.getString(2) + " | ";
			logstr += "Type: " + cursor.getString(3) + " | ";	
			logstr += "Comment: " + cursor.getString(4);	
			
			Log.i(logtag, logstr);
			
			cursor.moveToNext();
		}		
		
		cursor.close();
	}

	/**
	 * Function deletes a word from DB
	 * 
	 * @param id	Entry ID
	 */
	public int deleteDate(long id) {
		int ret = db.delete(TABLE_NAME, COL_ID + " = " + id, null);		
		Log.i(logtag, "Entry deleted. ID: " + id);
		
		return ret;
	}

}
