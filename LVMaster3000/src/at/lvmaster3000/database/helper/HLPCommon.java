package at.lvmaster3000.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import at.lvmaster3000.settings.DBsettings;

public class HLPCommon extends SQLiteOpenHelper {

	private SQLiteDatabase db;
	
	private HLPLectures hlpLectures;
	private HLPResources hlpResources;
	
	public HLPCommon(Context context) {
		super(context, DBsettings.DATABASE_NAME, null, DBsettings.DATABASE_VERSION);
		this.hlpLectures = new HLPLectures(context);
		this.hlpResources = new HLPResources(context);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
				
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				
	}
	
//	public void dropDatabase() {
//		this.db = this.getWritableDatabase();
//		this.db.execSQL("DROP DATABASE IF EXISTS " + DBsettings.DATABASE_NAME);
//		
//		this.db.dele
//		
//		this.db.close();
//		Log.i(DBsettings.LOG_TAG, "Database dropped!");
//	}
	
	public void deleteAllTables() {
		this.db = this.getWritableDatabase();
		
		this.hlpLectures.openCon();
		this.hlpLectures.deleteTable();
		this.hlpLectures.closeCon();
		
		this.hlpResources.openCon();
		this.hlpResources.deleteTable();
		this.hlpResources.closeCon();
		
		this.db.close();
		Log.i(DBsettings.LOG_TAG, "All tables deleted!");
	}

}
