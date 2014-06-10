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
	}
	
	@Override
	public void testAddNew() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testDelete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testUpdate() {
		// TODO Auto-generated method stub
		
	}
	
	public void testAddResource(){
		long rid = dblResources.addResource(new Resource("TEST res"));		
		assertNotSame(-1l, rid);
	}
	
	public void testDeleteResource() {
		long rid = dblResources.addResource(new Resource("TEST res"));
		assertSame(1, dblResources.deleteResource(rid));
	}
	
	public void testGetAllResources(){
		DDTestsetA testset = new DDTestsetA(this.context);
		testset.FillDb();
		
		assertNotSame(0, testset.getResourceCnt());
	}
	
}
