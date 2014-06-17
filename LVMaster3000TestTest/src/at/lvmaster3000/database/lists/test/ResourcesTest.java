package at.lvmaster3000.database.lists.test;

import android.test.AndroidTestCase;
import at.lvmaster3000.database.interfaces.IListTests;
import at.lvmaster3000.database.lists.Resources;
import at.lvmaster3000.database.objects.Resource;

public class ResourcesTest extends AndroidTestCase implements IListTests {

	private Resources resources;
	
	public void setUp() {
		this.resources = new Resources();
	}
	
	@Override
	public void testAddToList() {
		Resource r1 = new Resource("Res Title");
		this.resources.addResource(r1);
		
		assertSame(1, this.resources.getResources().size());
	}

	@Override
	public void testPrintList() {
		Resource r1 = new Resource("Res Title");
		this.resources.addResource(r1);
		
		try {
			this.resources.printResourceList();
		} catch (Exception e) {
			assertNotNull(null);
		}
	}

	@Override
	public void testClearList() {
		this.resources.clear();
	}
}
