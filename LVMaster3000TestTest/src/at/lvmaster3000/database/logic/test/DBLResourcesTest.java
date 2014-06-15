package at.lvmaster3000.database.logic.test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.demodata.DDTestsetA;
import at.lvmaster3000.database.interfaces.IDBLTests;
import at.lvmaster3000.database.logic.DBLResources;
import at.lvmaster3000.database.objects.Resource;

public class DBLResourcesTest extends AndroidTestCase implements IDBLTests {
	private DBLResources dblResources;	
	private RenamingDelegatingContext context = null;
	
	public static final String LOG_TAG_RESOURCES_LOGIC_TEST = "TEST_RESOURCES_LOGIC";
	
	public void setUp(){
		this.context = new RenamingDelegatingContext(getContext(), "test_");		
		this.dblResources = new DBLResources(this.context);	
		this.dblResources.resetTable();
	}
	
	@Override
	public void testAddNew() {
		long rid = dblResources.addResource(new Resource("TEST res"));		
		assertNotSame(-1l, rid);
	}

	@Override
	public void testDelete() {
		long rid = dblResources.addResource(new Resource("TEST res"));
		assertSame(1, dblResources.deleteResource(rid));
	}

	@Override
	public void testUpdate() {
		Resource res = new Resource("Res 1");
		assertSame(1l, this.dblResources.addResource(res));
		
		res.setTitle("Res 1.0");
		assertSame(1, this.dblResources.updateResource(res));
	}
	
	public void testGetResources(){
		DDTestsetA testset = new DDTestsetA(this.context);
		testset.FillDb();
		
		assertNotSame(0, testset.getResourceCnt());
	}
	
}
