package at.lvmaster3000.database.logic;

import android.content.Context;
import at.lvmaster3000.database.helper.HLPCoworkers;

public class DBLCoworkers {

	private HLPCoworkers hlpcoworkers;
	
	public DBLCoworkers(Context context) {
		hlpcoworkers = new HLPCoworkers(context);
	}
	
	public long addCoworker(String refid, String role) {
		return hlpcoworkers.addCoworker(refid, role);
	}
	
	public int deleteCoworker(long id) {
		return hlpcoworkers.deleteCoworker(id);
	}
	
}
