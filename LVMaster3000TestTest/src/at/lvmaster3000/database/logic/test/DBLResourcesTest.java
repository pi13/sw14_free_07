package at.lvmaster3000.database.logic.test;

import java.util.ArrayList;
import java.util.List;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.lists.Resources;
import at.lvmaster3000.database.logic.DBLResources;
import at.lvmaster3000.database.objects.Coworker;
import at.lvmaster3000.database.objects.Resource;

public class DBLResourcesTest extends AndroidTestCase{
	private DBLResources dblObjects;
	private List<Resource> testObjects = null;
	private int NR_TEST_OBJECTS = 0;
	private int limit = 100;
	
	private RenamingDelegatingContext testContext = null;
	
	public void setUp(){
		testContext = new RenamingDelegatingContext(getContext(), "test_");
		
		dblObjects = new DBLResources(testContext);
		createTestObjects();
	}
	
	public void testAddNew(){
		dropAllObjects();
		createTestObjects();
		
		long idFromDatabase = dblObjects.addResource(this.testObjects.get(0));
		
		assertNotSame(-1l, idFromDatabase);
	}
	
	public void testGetAll(){
		dropAllObjects();
		fillTestObjectsInDBL();
		Resources objects = dblObjects.getResources(limit);
		
		assertEquals(NR_TEST_OBJECTS, objects.nrOfResources());
	}
	
	private void fillTestObjectsInDBL(){
		if(this.testObjects == null){
			createTestObjects();
		}
		
		for(Resource object : this.testObjects){
			this.dblObjects.addResource(object);
		}
	}
	
	private void createTestObjects(){
		Resource o1 = new Resource();
		o1.setTitle("Test T1");
		Resource o2 = new Resource();
		o2.setTitle("Test T2");
	
		this.testObjects = new ArrayList<Resource>();
		
		this.testObjects.add(o1);
		this.testObjects.add(o2);
		
		this.NR_TEST_OBJECTS = this.testObjects.size();
	}
	
	private void dropAllObjects(){
		this.dblObjects = null;
		this.dblObjects = new DBLResources(testContext);
	}
}
