package at.lvmaster3000.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import at.lvmaster3000.settings.DBsettings;

public class HLPTasks extends SQLiteOpenHelper {

	//table name
	public static final String TABLE_NAME = "tasks";
	
	//table columns
	public static final String COL_ID = "_id";
	public static final String COL_TITLE = "title";
	public static final String COL_COMMENT = "taskcomment";
//	public static final String COL_LECTURE_ID = "lecture_id";
	
	private String logtag = DBsettings.LOG_TAG_TASKS;
	
	//columns list
	public static final String[] allColumns = {COL_ID, COL_TITLE, COL_COMMENT};
	
	//create string
	private static final String TASKS_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
												COL_ID + " integer primary key autoincrement, " +
												COL_TITLE + " text not null, " +
												COL_COMMENT + " text not null);";
	
	//database adapter
	private SQLiteDatabase db;
	
	/**
	 * Constructor
	 * 
	 * @param context	Per default "this" in main class
	 */
	public HLPTasks(Context context) {
		super(context, DBsettings.DATABASE_NAME, null, DBsettings.DATABASE_VERSION);
		Log.i(logtag, "constructor: Tasks helper created");
	}
	
	/**
	 * Just for debugging. Function returns the table creation string
	 * 
	 * @return	String
	 */
	public String getCreationString() {
		return TASKS_CREATE;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(logtag, TASKS_CREATE);
		db.execSQL(TASKS_CREATE);
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
	 * @param title
	 * @param comment
	 * @param lectureID
	 */
	public long addTask(String title, String comment) {
		ContentValues values = new ContentValues();
		
		values.put(COL_TITLE, title);
		values.put(COL_COMMENT, comment);
//		values.put(COL_LECTURE_ID, lectureID);	
		
		long insertId = db.insert(TABLE_NAME, null, values);
		
		Log.i(logtag, "New entry added. ID: " + insertId);
		
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
			logstr += "Title: " + cursor.getString(1) + " | ";
			logstr += "Comment: " + cursor.getString(2);	
			
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
	public int deleteTask(long id) {
		int ret = db.delete(TABLE_NAME, COL_ID + " = " + id, null);		
		Log.i(logtag, "Entry deleted. ID: " + id);
		
		return ret;
	}

}
