package at.lvmaster3000.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import at.lvmaster3000.settings.DBsettings;

public class HLPRelations extends SQLiteOpenHelper {

	//table name
	public static final String TABLE_NAME = "relations";
	
	//table columns
	public static final String COL_ID = "_id";
	public static final String COL_SRCTABLE = "srctable";
	public static final String COL_LECTURE_ID = "lecture_id";
	public static final String COL_EXAM_ID = "exam_id";
	public static final String COL_TASK_ID = "task_id";
	public static final String COL_DATE_ID = "date_id";
	public static final String COL_RES_ID = "resource_id";
	
	private String logtag = DBsettings.LOG_TAG_RELATIONS;
	
	//columns list
	public static final String[] allColumns = {COL_ID, COL_SRCTABLE, COL_LECTURE_ID, COL_EXAM_ID, COL_TASK_ID, COL_DATE_ID, COL_RES_ID};
	
	//create string
	private static final String RELATIONS_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
												COL_ID + " integer primary key autoincrement, " +
												COL_SRCTABLE + " text not null, " +
												COL_LECTURE_ID + " integer, " +
												COL_EXAM_ID + " integer, " +
												COL_TASK_ID + " integer," +
												COL_DATE_ID + " integer," +
												COL_RES_ID + " integer);";
	
	//database adapter
	private SQLiteDatabase db;
	
	/**
	 * Constructor
	 * 
	 * @param context	Per default "this" in main class
	 */
	public HLPRelations(Context context) {
		super(context, DBsettings.DATABASE_NAME, null, DBsettings.DATABASE_VERSION);
		Log.i(logtag, TABLE_NAME + " helper created");
	}
	
	/**
	 * Just for debugging. Function returns the table creation string
	 * 
	 * @return	String
	 */
	public String getCreationString() {
		return RELATIONS_CREATE;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(logtag, RELATIONS_CREATE);
		db.execSQL(RELATIONS_CREATE);
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
		db.execSQL(RELATIONS_CREATE);
		Log.i(logtag, "Table '" + TABLE_NAME + "' reseted");
	}
	
	/**
	 * Function inserts a new record into the table
	 * @param srctable
	 * @param lectureID
	 * @param examID
	 * @param taskID
	 * @param dateID
	 * @return insertId
	 */
	public long addRelation(String srctable, long lectureID, long examID, long taskID, long dateID, long resID) {
		ContentValues values = new ContentValues();
		
		values.put(COL_SRCTABLE, srctable);
		values.put(COL_LECTURE_ID, lectureID);
		values.put(COL_EXAM_ID, examID);
		values.put(COL_TASK_ID, taskID);
		values.put(COL_DATE_ID, dateID);
		values.put(COL_RES_ID, resID);
		
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
			logstr += "SrcTable: " + cursor.getString(1) + " | ";
			logstr += "LectureID: " + cursor.getLong(2) + " | ";
			logstr += "ExamID: " + cursor.getLong(3) + " | ";
			logstr += "TaskID: " + cursor.getLong(4) + " | ";
			logstr += "DateID: " + cursor.getLong(5)+ " | ";
			logstr += "ResourceID: " + cursor.getLong(6);
			
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
	public int deleteRelation(long id) {
		int ret = db.delete(TABLE_NAME, COL_ID + " = " + id, null);		
		Log.i(logtag, "Entry deleted. ID: " + id);
		
		return ret;
	}

}
