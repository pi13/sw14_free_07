package at.lvmaster3000.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import at.lvmaster3000.settings.DBsettings;

public class HLPCoworkers extends SQLiteOpenHelper {

	//table name
	public static final String TABLE_NAME = "coworkers";
	
	//table columns
	public static final String COL_ID = "_id";
	public static final String COL_REFID = "refid";
	public static final String COL_ROLE = "role";
	
	private String logtag = DBsettings.LOG_TAG_COWORKERS;
	
	//columns list
	public static final String[] allColumns = {COL_ID, COL_REFID, COL_ROLE};
	
	//create string
	private static final String COWORKERS_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
												COL_ID + " integer primary key autoincrement, " +
												COL_REFID + " text not null, " +
												COL_ROLE + " text not null);";
	
	//database adapter
	private SQLiteDatabase db;
	
	/**
	 * Constructor
	 * 
	 * @param context	Per default "this" in main class
	 */
	public HLPCoworkers(Context context) {
		super(context, DBsettings.DATABASE_NAME, null, DBsettings.DATABASE_VERSION);
		Log.i(logtag, TABLE_NAME + " helper created");
	}
	
	/**
	 * Just for debugging. Function returns the table creation string
	 * 
	 * @return	String
	 */
	public String getCreationString() {
		return COWORKERS_CREATE;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(logtag, COWORKERS_CREATE);
		db.execSQL(COWORKERS_CREATE);
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
	
	/**
	 * Function resets table and recreates it
	 */
	public void resetTable() {		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		db.execSQL(COWORKERS_CREATE);
		Log.i(logtag, "Table '" + TABLE_NAME + "' reseted");
	}
	
	/**
	 * Function inserts a new record into the table
	 * @param refid
	 * @param role
	 * @return 
	 */
	public long addCoworker(String refid, String role) {
		ContentValues values = new ContentValues();
		
		values.put(COL_REFID, refid);
		values.put(COL_ROLE, role);	
		
		long insertId = db.insert(TABLE_NAME, null, values);
		
		Log.i(logtag, "New entry added. ID: "+ insertId);
		
		return insertId;
	}
	
	/**
	 * Function returns all entries stored in DB as list
	 */
	public void allEntriesToLog() {		
		Cursor cursor = db.query(TABLE_NAME, allColumns, null, null, null, null, null);
				
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {			
			String logstr = "";
			
			logstr += "ID: " + cursor.getLong(0) + " | ";
			logstr += "Ref-ID: " + cursor.getString(1) + " | ";
			logstr += "Role: " + cursor.getString(2);	
			
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
	public void deleteCoworker(long id) {
		db.delete(TABLE_NAME, COL_ID + " = " + id, null);		
		Log.i(logtag, "Entry deleted. ID: " + id);
	}

}
