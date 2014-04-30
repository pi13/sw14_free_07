package at.lvmaster3000.database.logic;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import at.lvmaster3000.database.helper.HLPDates;
import at.lvmaster3000.database.helper.HLPLectures;
import at.lvmaster3000.database.lists.Dates;
import at.lvmaster3000.database.lists.Lectures;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.settings.DBsettings;

public class DBLDates {
	
	private HLPDates hlpdates = null;

	public DBLDates(Context context) {
		this.hlpdates = new HLPDates(context);
	}
	
	public long addDate(long timestamp, String location, String type, String comment) {
		this.hlpdates.openCon();
		long id = this.hlpdates.addDate(timestamp, location, type, comment);
		this.hlpdates.closeCon();
		
		return id; 
	}
	
	public long addDate(Date date) {
		return this.addDate(date.getTimestamp(), date.getLocation(), date.getType(), date.getComment());
	}
	
	public int deleteDate(long id){
		this.hlpdates.openCon();
		int res = hlpdates.deleteDate(id);
		this.hlpdates.closeCon();
		
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
		
        Cursor cursor = hlpdates.openCon().rawQuery(query, null);
        if(cursor != null) {                	
        	dates.cursorToDateList(cursor);
        } else {
        	Log.w(DBsettings.LOG_TAG_LECTURES, "Cursor is NULL!!");        	
        }
        hlpdates.closeCon();
		
		return dates;
	}

}
