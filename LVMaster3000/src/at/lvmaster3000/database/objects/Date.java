package at.lvmaster3000.database.objects;

import android.database.Cursor;
import android.util.Log;
import at.lvmaster3000.settings.DBsettings;

public class Date {
	private long id;
	private long timestamp;
	private String location;
	
	/**
	 * is it a lecture held, a milestone, a teammeeting?
	 */
	private String type;
	private String comment;
	
	public Date(long id, long time, String location, String type, String comment) {
		this.id = id;
		this.timestamp = time;
		this.location = location;
		this.type = type;
		this.comment = comment;
	}
	public long getID() {
		return id;
	}
	public void setID(long id) {
		this.id = id;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public Date cursorToDate(Cursor cursor) {
		//COL_ID, COL_TIMESTAMP, COL_LOCATION, COL_TYPE, COL_COMMENT
		this.id = cursor.getLong(0);
		this.timestamp = cursor.getLong(1);
		this.location = cursor.getString(2);
		this.type = cursor.getString(3);
		this.comment = cursor.getString(4);
		
		return this;
	}
	
	public void printDate() {
		String msg = "ID: " + this.id;
		msg += " | Timestamp: " + this.timestamp;
		msg += " | Location: " + this.location;
		msg += " | Type: " + this.type;
		msg += " | Comment" + this.comment;
		Log.d(DBsettings.LOG_TAG_DATES, msg);		
	}
}
