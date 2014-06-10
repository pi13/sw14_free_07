package at.lvmaster3000.database.objects.test;

import android.test.AndroidTestCase;
import at.lvmaster3000.database.interfaces.IObjectTest;
import at.lvmaster3000.database.objects.Coworker;

public class CoworkerTest extends AndroidTestCase implements IObjectTest {

	@Override
	public void testCreateObject() {
		Coworker c = new Coworker(0, "x123y", "leader");
		assertNotNull(c);
	}

	@Override
	public void testFillObject() {
		Coworker c1 = new Coworker(0, "x123y", "leader");
		Coworker c2 = new Coworker(0, "x456y", "worker");
		
		assertNotSame(c1.getRole(), c2.getRole());
	}

	@Override
	public void testPrintDetails() {
		Coworker c = new Coworker(0, "x123y", "leader");
		
		try {
			c.printCoworker();
		} catch (Exception e) {
			assertNotNull(null);
		}
	}

}
