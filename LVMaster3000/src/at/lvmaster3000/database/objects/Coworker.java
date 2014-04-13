package at.lvmaster3000.database.objects;

import android.database.Cursor;
import android.util.Log;
import at.lvmaster3000.settings.DBsettings;

public class Coworker {
	
	private long id;
	
	/**
	 * would be the reference to the contact in phonebook, if implemented
	 */
	private String refid;
	
	/**
	 * the role the coworker has in the task/learngroup
	 */
	private String role;

	public Coworker(){
		
	}
	
	public Coworker(long id, String refid, String role) {
		this.id = id;
		this.refid = refid;
		this.role = role;
	}

	public long getID() {
		return id;
	}

	public void setID(long id) {
		this.id = id;
	}

	public String getRefID() {
		return refid;
	}

	public void setRefID(String refid) {
		this.refid = refid;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public Coworker cursorToCoworker(Cursor cursor){
		//COL_ID, COL_REFID, COL_ROLE
		this.id = cursor.getLong(0);
		this.refid = cursor.getString(1);
		this.role = cursor.getString(2);
		
		return this;
	}
	
	public void printCoworker() {
		String msg = "ID: " + this.id;
		msg += " | Ref-ID: " + this.refid;
		msg += " | Role: " + this.role;
		Log.d(DBsettings.LOG_TAG_COWORKERS, msg);
	}
	
}
