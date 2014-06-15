package at.lvmaster3000.database.logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import at.lvmaster3000.database.helper.HLPCoworkers;
import at.lvmaster3000.database.helper.HLPDates;
import at.lvmaster3000.database.helper.HLPLectures;
import at.lvmaster3000.database.lists.Coworkers;
import at.lvmaster3000.database.objects.Coworker;
import at.lvmaster3000.settings.DBsettings;

public class DBLCoworkers {

	private HLPCoworkers hlpCoworkers;
	
	public DBLCoworkers(Context context) {
		this.hlpCoworkers = new HLPCoworkers(context);
	}
	
	public void resetTable() {
		this.hlpCoworkers.openCon();
		hlpCoworkers.resetTable();
		this.hlpCoworkers.closeCon();
	}
	
	public long addCoworker(String refid, String role) {
		this.hlpCoworkers.openCon();
		long cid = this.hlpCoworkers.addCoworker(refid, role);
		this.hlpCoworkers.closeCon();
		
		return cid;
	}
	
	public long addCoworker(Coworker coworker) {
		long cid = this.addCoworker(coworker.getRefID(), coworker.getRole());
		coworker.setID(cid);
		
		return cid;
	}
	
	public int updateCoworker(Coworker coworker) {
		ContentValues values = new ContentValues();
		
		if(!coworker.getRefID().isEmpty()) {
			values.put(HLPCoworkers.COL_REFID, coworker.getRefID());
		}
		
		if(!coworker.getRole().isEmpty()) {
			values.put(HLPCoworkers.COL_ROLE, coworker.getRole());
		}
		
		int ret = this.hlpCoworkers.openCon().update(HLPCoworkers.TABLE_NAME, values, "_id = " + coworker.getID(), null);
		
		Log.i(DBsettings.LOG_TAG_COWORKERS, "Update res.: " + ret);
		
		return ret;
	}
	
	public int deleteCoworker(long id) {
		this.hlpCoworkers.openCon();
		int ret = this.hlpCoworkers.deleteCoworker(id);
		this.hlpCoworkers.closeCon();
		
		return ret;
	}

	public Coworkers getCoworkers(int limit) {
		Coworkers coworkers = new Coworkers();
				
		String query = "SELECT * FROM " + HLPCoworkers.TABLE_NAME;
		
		if(limit > 0) {
			query += " LIMIT " + limit;
		}
		
		Cursor cursor = this.hlpCoworkers.openCon().rawQuery(query, null);
		if(cursor != null) {
			coworkers.cursorToCoworkerList(cursor);        	
        } else {
        	Log.w(DBsettings.LOG_TAG_COWORKERS, "Cursor is NULL!!");        	
        }
		
		this.hlpCoworkers.closeCon();
		
		return coworkers;
	}
}
