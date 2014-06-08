package at.lvmaster3000.database.objects;

import android.database.Cursor;
import android.util.Log;
import at.lvmaster3000.database.helper.HLPRelations;
import at.lvmaster3000.database.helper.HLPTasks;
import at.lvmaster3000.settings.DBsettings;

public class Task {

	private long id;
	private String title;
	private String comment;
	private Date date;
	
	public Task() {
		
	}
	
	/**
	 * 
	 * @param id
	 * @param title
	 * @param comment
	 * @param date
	 */
	public Task(long id, String title, String comment, Date date) {
		this.id = id;
		this.title = title;
		this.comment = comment;
		this.date = date;
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Task cursorToTask(Cursor cursor) {
		//COL_ID, COL_TITLE, COL_COMMENT
		int idx = cursor.getColumnIndex(HLPRelations.COL_TASK_ID);
				
		if(idx < 0) {
			this.id = cursor.getLong(cursor.getColumnIndex(HLPTasks.COL_ID));
		} else {
			this.id = cursor.getLong(idx);
		}
		
		if(this.id < 0){
			return null;
		}
		
		this.title = cursor.getString(cursor.getColumnIndex(HLPTasks.COL_TITLE));
		this.comment = cursor.getString(cursor.getColumnIndex(HLPTasks.COL_COMMENT));
//		this.date = new Date().cursorToDate(cursor);
		
		return this;
	}
	
	public void printTask() {
		String msg = "ID: " + this.id;
		msg += " | Title: " + this.title;
		msg += " | Comment: " + this.comment;		
		Log.d(DBsettings.LOG_TAG_TASKS, msg);
		if(date != null) {
			date.printDate();
		}
	}
	
}
