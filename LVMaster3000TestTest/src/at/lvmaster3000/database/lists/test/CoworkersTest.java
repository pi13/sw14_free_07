package at.lvmaster3000.database.lists.test;

import android.test.AndroidTestCase;
import at.lvmaster3000.database.interfaces.IListTests;
import at.lvmaster3000.database.lists.Coworkers;
import at.lvmaster3000.database.objects.Coworker;

public class CoworkersTest extends AndroidTestCase implements IListTests {

	private Coworkers coworkers;
	
	public void setUp(){
		coworkers = new Coworkers();
	}
	
	@Override
	public void testAddToList(){
		this.coworkers.clear();
		
		Coworker cw = new Coworker(1l, "reference", "leader");
		coworkers.addCoworker(cw);
		
		assertEquals(1, coworkers.getCoworkers().size());
	}
	
	@Override
	public void testPrintList() {
		this.coworkers.clear();
		
		Coworker cw1 = new Coworker(1l, "reference1", "leader");
		Coworker cw2 = new Coworker(2l, "reference2", "worker");
		Coworker cw3 = new Coworker(3l, "reference3", "worker");
		
		this.coworkers.addCoworker(cw1);
		this.coworkers.addCoworker(cw2);
		this.coworkers.addCoworker(cw3);
		
		try {
			this.coworkers.printCoworkerList();
		} catch (Exception e) {
			assertNotNull(null);
		}
	}
	
	@Override
	public void testClearList() {
		this.coworkers.clear();
	}
	
}
