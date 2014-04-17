package at.lvmaster3000.database.objects;

import android.database.Cursor;
import android.util.Log;
import at.lvmaster3000.settings.DBsettings;

public class Lecture {
	private long id;
	private String number;
	private String name;
	private String comment;
	private String type;
	private int required;
	private int compulsory;
	
	public Lecture(long id, String number, String name, String comment,
			String type, int req, int comp) {
		this.id = id;
		this.number = number;
		this.name = name;
		this.comment = comment;
		this.type = type;
		this.required = req;
		this.compulsory = comp;
	}

	public Lecture() {
		
	}

	public long getID() {
		return id;
	}
	
	public void setID(long id) {
		this.id = id;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public int getRequired() {
		return required;
	}
	
	public void setRequired(int required) {
		this.required = required;
	}
	
	public int getCompulsory() {
		return compulsory;
	}
	
	public void setCompulsory(int compulsory) {
		this.compulsory = compulsory;
	}
	
	public Lecture cursorToLecture(Cursor cursor) {
		//COL_ID, COL_NUMBER, COL_NAME, COL_COMMENT, COL_TYPE, COL_REQUIRED, COL_COMPULSORY
		this.id = cursor.getLong(0); 
		this.number = cursor.getString(1);
		this.name = cursor.getString(2);
		this.comment = cursor.getString(3);
		this.type = cursor.getString(4);
		this.required = cursor.getInt(5);
		this.compulsory = cursor.getInt(6);	
		
		return this;
	}
	
	public void printLecture() {
		String msg = "ID: " + this.id;
		msg += " | Number: " + this.number;
		msg += " | Name: " + this.name;
		msg += " | Comment: " + this.comment;
		msg += " | Type: " +  this.type;
		msg += " | Required: " + this.required;
		msg += " | Compulsory: " + this.compulsory;
		Log.d(DBsettings.LOG_TAG_LECTURES, msg);
	}
}
