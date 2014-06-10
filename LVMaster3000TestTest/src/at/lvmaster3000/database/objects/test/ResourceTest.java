package at.lvmaster3000.database.objects.test;

import android.test.AndroidTestCase;
import at.lvmaster3000.database.interfaces.IObjectTest;
import at.lvmaster3000.database.objects.Resource;

public class ResourceTest extends AndroidTestCase implements IObjectTest {

	@Override
	public void testCreateObject() {
		Resource res = new Resource();		
		assertNotNull(res);
	}

	@Override
	public void testFillObject() {
		Resource res1 = new Resource("title");
		Resource res2 = new Resource();
		res2.setTitle("title");
		
		assertSame(res1.getTitle(), res2.getTitle());
	}

	@Override
	public void testPrintDetails() {
		Resource res = new Resource("title");
		
		try {
			res.printResource();
		} catch (Exception e) {
			assertNotNull(null);
		}
	}

}
