package at.lvmaster3000.database.helper;

import java.util.List;

import akm.sql.helper.HLPwordlist;
import akm.types.Word;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import at.lvmaster3000.settings.DBsettings;

public class HLPlectures extends SQLiteOpenHelper {

	//table name
	public static final String TABLE_NAME = "lecture";
	
	//table columns
	public static final String COL_ID = "_id";
	public static final String COL_NUMBER = "number";
	public static final String COL_NAME = "name";
	public static final String COL_COMMENT = "comment";
	public static final String COL_TYPE = "type";
	public static final String COL_REQUIRED = "required";
	public static final String COL_COMPULSORY = "compulsory";
	
	
	//columns list
	public static final String[] allColumns = {COL_ID, COL_NUMBER, COL_NAME, COL_COMMENT, COL_TYPE, COL_REQUIRED, COL_COMPULSORY};
	
	//create string
	private static final String LECTURES_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
												COL_ID + " integer primary key autoincrement, " +
												COL_NUMBER + " text not null, " +
												COL_NAME + " text not null, " +
												COL_COMMENT + " text not null, " +
												COL_TYPE + " text not null, " +
												COL_REQUIRED + " integer, " +
												COL_COMPULSORY + " integer);";
	
	//database adapter
	private SQLiteDatabase db;
	
	/**
	 * Constructor
	 * 
	 * @param context	Per default "this" in main class
	 */
	public HLPlectures(Context context) {
		super(context, DBsettings.DATABASE_NAME, null, DBsettings.DATABASE_VERSION);
		Log.i(DBsettings.LOG_TAG, "Lectures helper created");
	}
	
	/**
	 * Just for debugging. Function returns the lectures table creation string
	 * 
	 * @return	String
	 */
	public String getCreationString() {
		return LECTURES_CREATE;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(DBsettings.LOG_TAG, LECTURES_CREATE);
		db.execSQL(LECTURES_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(DBsettings.LOG_TAG,
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
	 * Function resets lectures table and recreates it
	 */
	public void resetTable() {		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		db.execSQL(LECTURES_CREATE);
	}
	
	/**
	 * Function inserts a new record into the lectures table
	 * 
	 * @param number	
	 * @param name
	 * @param comment
	 * @param type
	 * @param required
	 * @param compulsory
	 */
	public void addLecture(String number, String name, String comment, String type, int required, int compulsory) {
		ContentValues values = new ContentValues();
		
		values.put(COL_NUMBER, number);
		values.put(COL_NAME, name);
		values.put(COL_COMMENT, comment);
		values.put(COL_TYPE, type);
		values.put(COL_REQUIRED, required);
		values.put(COL_COMPULSORY, compulsory);	
		
		long insertId = db.insert(TABLE_NAME, null, values);
		
		Log.i(DBsettings.LOG_TAG, "New lecture added. ID: "+ insertId);
	}
	
	/**
	 * Function graps all words stored in DB and returns a list
	 * 
	 * @return List<TBL_wordlist>
	 */
	public List<Word> getAll() {		
		Cursor cursor = h_wordlist.openCon().query(HLPwordlist.TABLE_NAME, HLPwordlist.allColumns, null, null, null, null, null);
		
		List<Word> words = cursorToWordlist(cursor);
		
		cursor.close();
		h_wordlist.closeCon();
		
		return words;		
	}

	/**
	 * Function deletes a word from DB
	 * 
	 * @param id	Lecture ID
	 */
	public void deleteLecture(long id) {
		db.delete(TABLE_NAME, COL_ID + " = " + id, null);		
		Log.i(DBsettings.LOG_TAG, "Word deleted. ID: " + id);
	}
}
