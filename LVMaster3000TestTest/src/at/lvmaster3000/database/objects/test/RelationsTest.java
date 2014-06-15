package at.lvmaster3000.database.objects.test;

import android.test.AndroidTestCase;
import at.lvmaster3000.database.interfaces.IObjectTest;
import at.lvmaster3000.database.objects.Relation;

public class RelationsTest extends AndroidTestCase implements IObjectTest {

	@Override
	public void testCreateObject() {
		Relation rel = new Relation(1l, "any", 1l, 1l, 1l, 1l, 1l);
		assertNotNull(rel);
	}

	@Override
	public void testFillObject() {
		Relation rel1 =  new Relation(1l, "any1", 1l, 1l, 1l, 1l, 1l);
		Relation rel2 =  new Relation();
		rel2.setSrcTable("any2");
		
		assertNotSame(rel1.getSrcTable(), rel2.getSrcTable());
	}

	@Override
	public void testPrintDetails() {
		Relation rel = new Relation(1l, "any", 1l, 1l, 1l, 1l, 1l);
		
		try {
			rel.printRelation();
		} catch (Exception e) {
			assertNotNull(null);
		}
	}

}
