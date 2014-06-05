package at.lvmaster3000.database.lists.test;

import android.test.AndroidTestCase;
import at.lvmaster3000.database.lists.Coworkers;
import at.lvmaster3000.database.objects.Coworker;

public class CoworkersTest extends AndroidTestCase{

	private Coworkers coworkers;
	
	public void startUp(){
		coworkers = new Coworkers();
	}
	
	public void addToListTest(){
		clearList();
		
		Coworker cw = new Coworker(1l, "reference", "leader");
		coworkers.addCoworker(cw);
		
		assertEquals(1, coworkers.getCoworkers().size());
	}
	
	private void clearList(){
		this.coworkers.deleteAllCoworkers();
	}
	
}
