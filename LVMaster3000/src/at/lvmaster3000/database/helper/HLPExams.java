package at.lvmaster3000.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import at.lvmaster3000.settings.DBsettings;

public class HLPExams extends SQLiteOpenHelper{
	//table name
	public static final String TABLE_NAME = "exams";
	
	//table columns
	public static final String COL_ID = "_id";
	public static final String COL_TITLE = "title";
	public static final String COL_COMMENT = "examcomment";
	public static final String COL_LECTURE_ID = "lecture_id";
	
	private String logtag = DBsettings.LOG_TAG_EXAMS;
	
	
	//columns list
	public static final String[] allColumns = {COL_ID, COL_TITLE, COL_COMMENT, COL_LECTURE_ID};
	
	//create string
	private static final String EXAMS_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
												COL_ID + " integer primary key autoincrement, " +
												COL_TITLE + " text not null, " +
												COL_COMMENT + " text not null, " +
												COL_LECTURE_ID + " integer);";
	
	//database adapter
	private SQLiteDatabase db;
	
	/**
	 * Constructor
	 * 
	 * @param context	Per default "this" in main class
	 */
	public HLPExams(Context context) {
		super(context, DBsettings.DATABASE_NAME, null, DBsettings.DATABASE_VERSION);
		Log.i(logtag, "constructor: Exams helper created");
	}
	
	/**
	 * Just for debugging. Function returns the exams table creation string
	 * 
	 * @return	String
	 */
	public String getCreationString() {
		return EXAMS_CREATE;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(logtag, EXAMS_CREATE);
		db.execSQL(EXAMS_CREATE);
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
	 * 
	 */
	public void deleteTable() {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		Log.i(logtag, "Table '" + TABLE_NAME + "' deleted");
	}
	
	/**
	 * Function resets exams table and recreates it
	 */
	public void resetTable() {		
		this.deleteTable();
		this.onCreate(db);
		Log.i(logtag, "Table '" + TABLE_NAME + "' reseted");
	}
	
	/**
	 * Function inserts a new record into the exams table
	 * @param title
	 * @param comment
	 * @param lectureId
	 * @return
	 */
	public long addExam(String title, String comment, long lectureId) {
		ContentValues values = new ContentValues();
		
		values.put(COL_TITLE, title);
		values.put(COL_COMMENT, comment);
		values.put(COL_LECTURE_ID, lectureId);	
		
		long insertId = db.insert(TABLE_NAME, null, values);
		
		Log.i(logtag, "New entry added. ID: "+ insertId);
		
		return insertId;
	}
	
	/**
	 * Function returns all exams stored in DB as list
	 */
	public void allEntriesToLog() {		
		Cursor cursor = db.query(TABLE_NAME, allColumns, null, null, null, null, null);
				
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {			
			String logstr = "";
			
			logstr += "ID: " + cursor.getLong(0) + " | ";
			logstr += "Title: " + cursor.getString(1) + " | ";
			logstr += "Comment: " + cursor.getString(2) + " | ";
			logstr += "Lecture ID: " + cursor.getLong(3) + " | ";	
			
			Log.i(logtag, logstr);
			
			cursor.moveToNext();
		}		
		
		cursor.close();
	}

	/**
	 * Function deletes a word from DB
	 * @param id
	 */
	public int deleteExam(long id) {
		int res = db.delete(TABLE_NAME, COL_ID + " = " + id, null);		
		Log.i(logtag, "Word deleted. ID: " + id);
		
		return res;
	}
}
