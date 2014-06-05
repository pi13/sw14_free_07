package at.lvmaster3000.database.logic;

import android.content.Context;
import at.lvmaster3000.database.helper.HLPCoworkers;
import at.lvmaster3000.database.lists.Coworkers;
import at.lvmaster3000.database.objects.Coworker;

public class DBLCoworkers {

	private HLPCoworkers hlpCoworkers;
	
	public DBLCoworkers(Context context) {
		this.hlpCoworkers = new HLPCoworkers(context);
		
		this.hlpCoworkers.openCon();
//		hlpCoworkers.resetTable();
//		this.hlpCoworkers.closeCon();
	}
	
	public long addCoworker(String refid, String role) {
		this.hlpCoworkers.openCon();
		long ret = this.hlpCoworkers.addCoworker(refid, role);
		this.hlpCoworkers.closeCon();
		
		return ret;
	}
	
	public int deleteCoworker(long id) {
		this.hlpCoworkers.openCon();
		int ret = this.hlpCoworkers.deleteCoworker(id);
		this.hlpCoworkers.closeCon();
		
		return ret;
	}

	public Coworkers getCoworkers(int limit) {
		// TODO Auto-generated method stub
		return null;
	}
}
