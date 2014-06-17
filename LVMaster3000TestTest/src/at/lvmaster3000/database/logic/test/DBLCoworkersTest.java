package at.lvmaster3000.database.logic.test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.interfaces.IDBLTests;
import at.lvmaster3000.database.logic.DBLCoworkers;
import at.lvmaster3000.database.objects.Coworker;

public class DBLCoworkersTest extends AndroidTestCase implements IDBLTests {
	
	private DBLCoworkers dblCoworkers;	
	private RenamingDelegatingContext context;
	
	public static final String LOG_TAG_COWORKERS_LOGIC_TEST = "TEST_COWORKERS_LOGIC";
	
	public void setUp(){
		this.context = new RenamingDelegatingContext(getContext(), "test_");				
		this.dblCoworkers = new DBLCoworkers(this.context);
		this.dblCoworkers.resetTable();
	}
	
	@Override
	public void testAddNew(){		
		long id = this.dblCoworkers.addCoworker("x31212", "admin");		
		assertSame(1l, id);
	}
	
	@Override
	public void testDelete() {
		long id = this.dblCoworkers.addCoworker("x31212", "admin");		
		assertSame(1l, id);
		assertSame(1, this.dblCoworkers.deleteCoworker(id));
	}

	@Override
	public void testUpdate() {
		Coworker cw = new Coworker(0, "x123", "admin");
		assertSame(1l, this.dblCoworkers.addCoworker(cw));
		
		cw.setRole("user");
		assertSame(1, this.dblCoworkers.updateCoworker(cw));
	}
	
	public void testGetAll(){
		this.dblCoworkers.addCoworker("x123", "admin 1");
		this.dblCoworkers.addCoworker("x123", "user 1");
		this.dblCoworkers.addCoworker("x123", "user 2");
		
		assertSame(3, this.dblCoworkers.getCoworkers(0).getCoworkers().size());
	}
}
