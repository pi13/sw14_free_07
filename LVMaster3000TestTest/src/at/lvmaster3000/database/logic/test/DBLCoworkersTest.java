package at.lvmaster3000.database.logic.test;

import java.util.ArrayList;
import java.util.List;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;
import at.lvmaster3000.database.interfaces.IDBLTests;
import at.lvmaster3000.database.lists.Coworkers;
import at.lvmaster3000.database.logic.DBLCoworkers;
import at.lvmaster3000.database.objects.Coworker;
import at.lvmaster3000.settings.CMONsettings;

public class DBLCoworkersTest extends AndroidTestCase implements IDBLTests {
	
	private DBLCoworkers dblCoworkers;
	private List<Coworker> testObjects = null;
	private int NR_TEST_OBJECTS = 0;
	
	private RenamingDelegatingContext context;
	
	public static final String LOG_TAG_COWORKERS_LOGIC_TEST = "TEST_COWORKERS_LOGIC";
	
	public void setUp(){
		this.context = new RenamingDelegatingContext(getContext(), "test_");
				
		dblCoworkers = new DBLCoworkers(this.context);
		createTestObjects();
	}
	
	@Override
	public void testAddNew(){
		dropAllObjects();
		createTestObjects();
		
		long idFromDatabase = dblCoworkers.addCoworker(this.testObjects.get(0).getRefID(), this.testObjects.get(0).getRole());
		
		assertNotSame(-1l, idFromDatabase);
	}
	
	@Override
	public void testDelete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testUpdate() {
		// TODO Auto-generated method stub
		
	}
	
	public void testGetAll(){
		dropAllObjects();
		fillTestObjectsInDBL();
		Coworkers coworkers = dblCoworkers.getCoworkers(0);
		
		coworkers.printCoworkerList();
		
		assertEquals(NR_TEST_OBJECTS, coworkers.getCoworkers().size());
	}
	
	private void fillTestObjectsInDBL(){
		if(this.testObjects == null){
			createTestObjects();
		}
		
		for(Coworker object : this.testObjects){
			this.dblCoworkers.addCoworker(object.getRefID(), object.getRole());
		}
	}
	
	private void createTestObjects(){
		Coworker o1 = new Coworker();
		o1.setRefID("x1");
		o1.setRole("Admin");
		
		Coworker o2 = new Coworker();
		o2.setRefID("x2");
		o2.setRole("Worker");
	
		this.testObjects = new ArrayList<Coworker>();
		
		this.testObjects.add(o1);
		this.testObjects.add(o2);
		
		this.NR_TEST_OBJECTS = this.testObjects.size();
	}
	
	private void dropAllObjects(){
		this.dblCoworkers = null;
		this.dblCoworkers = new DBLCoworkers(this.context);
	}
}
