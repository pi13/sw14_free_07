package at.lvmaster3000.database.objects;

import android.database.Cursor;
import android.util.Log;
import at.lvmaster3000.settings.DBsettings;

public class Resource {
	private long id;
	private String title;
	
	public Resource(String title) {
		this.title = title;
	}
	public Resource() {
		
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Resource cursorToResource(Cursor cursor) {
		this.id = cursor.getLong(0); 
		this.title = cursor.getString(1);
		
		return this;
	}
	
	public void printResource() {
		String msg = "ID: " + this.id;
		msg += " | Title: " + this.title;

		Log.d(DBsettings.LOG_TAG_RESOURCES, msg);
	}
}
