package at.lvmaster3000.database.logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import at.lvmaster3000.database.helper.HLPDates;
import at.lvmaster3000.database.helper.HLPTasks;
import at.lvmaster3000.database.lists.Dates;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Relation;
import at.lvmaster3000.settings.DBsettings;

public class DBLDates {
	
	private HLPDates hlpDates = null;

	public DBLDates(Context context) {
		this.hlpDates = new HLPDates(context);
	}
	
	public long addDate(long timestamp, String location, String type, String comment) {
		this.hlpDates.openCon();
		long id = this.hlpDates.addDate(timestamp, location, type, comment);
		this.hlpDates.closeCon();
		
		return id; 
	}
	
	public long addDate(Date date) {
		return this.addDate(date.getTimestamp(), date.getLocation(), date.getType(), date.getComment());
	}
	
	public int deleteDate(long id){
		this.hlpDates.openCon();
		int res = hlpDates.deleteDate(id);
		this.hlpDates.closeCon();
		
		return res;
	}
	
	public int deleteDate(Date date) {
		return this.deleteDate(date.getID());
	}

	public Dates getDates(int limit) {
		Dates dates = new Dates();
		
		String query = "SELECT * FROM " + HLPDates.TABLE_NAME;
		
		if(limit > 0) {
			query += " LIMIT " + limit;
		}
		
        Cursor cursor = this.hlpDates.openCon().rawQuery(query, null);
        if(cursor != null) {              
        	if(cursor.getCount() < 1) {
        		this.hlpDates.closeCon();
        		return null;
        	}
        	dates.cursorToDateList(cursor);
        } else {
        	Log.w(DBsettings.LOG_TAG_LECTURES, "Cursor is NULL!!");        	
        }
        this.hlpDates.closeCon();
		
		return dates;
	}
	
	public Date getDateByRelation(Relation relation) {
		Date date = new Date();
		
		String query = "SELECT * FROM " + HLPDates.TABLE_NAME;
		query += " WHERE " + HLPDates.COL_ID + " = " + relation.getDateId();
		
        Cursor cursor = hlpDates.openCon().rawQuery(query, null);
        if(cursor != null) {  
        	if(cursor.getCount() < 1) {
        		this.hlpDates.closeCon();
        		Log.e(DBsettings.LOG_TAG_LECTURES, "Nothing found"); 
        		return null;
        	}
        	
        	cursor.moveToFirst();        	
        	date.cursorToDate(cursor);
        } else {
        	Log.w(DBsettings.LOG_TAG_LECTURES, "Cursor is NULL!!");        	
        }
        this.hlpDates.closeCon();
		
		return date;
	}

	public int updateDate(Date date) {
		ContentValues values = new ContentValues();
		
		if(date.getTimestamp() > 0) {
			values.put(HLPDates.COL_TIMESTAMP, date.getTimestamp());
		}
		
		if(!date.getType().isEmpty()) {
			values.put(HLPDates.COL_TYPE, date.getType());
		}
		
		if(!date.getLocation().isEmpty()) {
			values.put(HLPDates.COL_TYPE, date.getType());
		}
		
		if(!date.getComment().isEmpty()) {
			values.put(HLPDates.COL_COMMENT, date.getComment());
		}
		
		int ret = this.hlpDates.openCon().update(HLPDates.TABLE_NAME, values, "_id = " + date.getID(), null);
		
		Log.i(DBsettings.LOG_TAG_DATES, "Update res.: " + ret);
		
		return ret;
	}
	
}
