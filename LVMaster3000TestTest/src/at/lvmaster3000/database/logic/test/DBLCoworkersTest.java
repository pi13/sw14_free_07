package at.lvmaster3000.database.logic.test;

import java.util.ArrayList;
import java.util.List;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;
import at.lvmaster3000.database.lists.Coworkers;
import at.lvmaster3000.database.logic.DBLCoworkers;
import at.lvmaster3000.database.objects.Coworker;
import at.lvmaster3000.settings.CMONsettings;

public class DBLCoworkersTest extends AndroidTestCase{
	private DBLCoworkers dblObjects;
	private List<Coworker> testObjects = null;
	private int NR_TEST_OBJECTS = 0;
	
	private RenamingDelegatingContext testContext = null;
	
	public void startUp(){
		testContext = new RenamingDelegatingContext(getContext(), "test_");
		
		dblObjects = new DBLCoworkers(testContext);
		createTestObjects();
	}
	
	public void testAddNew(){
		dropAllObjects();
		createTestObjects();
		
		long idFromDatabase = dblObjects.add(this.testObjects.get(0));
		
		assertNotSame(-1l, idFromDatabase);
	}
	
	public void testGetAll(){
		dropAllObjects();
		fillTestObjectsInDBL();
		Coworkers objects = dblObjects.getAll();
		
		assertEquals(NR_TEST_OBJECTS, objects.nrOfObjects());
	}
	
	private void fillTestObjectsInDBL(){
		if(this.testObjects == null){
			createTestObjects();
		}
		
		for(Coworker object : this.testObjects){
			this.dblObjects.add(object);
		}
	}
	
	private void createTestObjects(){
		Coworker o1 = new Coworker();
		Coworker o2 = new Coworker();
	
		this.testObjects = new ArrayList<Coworker>();
		
		this.testObjects.add(o1);
		this.testObjects.add(o2);
		
		this.NR_TEST_OBJECTS = this.testObjects.size();
	}
	
	private void dropAllObjects(){
		this.dblObjects = null;
		this.dblObjects = new DBLCoworkers(testContext);
	}
}
