package at.lvmaster3000.database.objects;

import android.database.Cursor;
import android.util.Log;
import at.lvmaster3000.settings.DBsettings;

public class Exam {
	private long id;
	private String title;
	private String comment;
	private long lecture_id;
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
	public long getLecture_id() {
		return lecture_id;
	}
	public void setLecture_id(long lecture_id) {
		this.lecture_id = lecture_id;
	}
	
	public Exam cursorToExam(Cursor cursor) {
		this.id = cursor.getLong(0); 
		this.title = cursor.getString(1);
		this.comment = cursor.getString(2);
		this.lecture_id = cursor.getLong(3);
		
		return this;
	}
	
	public void printExam() {
		String msg = "ID: " + this.id;
		msg += " | Title: " + this.title;
		msg += " | Comment: " + this.comment;
		msg += " | Lecture ID: " +  this.lecture_id;
		Log.d(DBsettings.LOG_TAG_TASKS, msg);
	}

}
