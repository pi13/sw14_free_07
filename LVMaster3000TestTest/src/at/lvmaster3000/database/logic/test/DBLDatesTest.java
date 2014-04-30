package at.lvmaster3000.database.logic.test;

import java.util.ArrayList;
import java.util.List;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.demodata.DDTestsetA;
import at.lvmaster3000.database.lists.Dates;
import at.lvmaster3000.database.logic.DBLDates;
import at.lvmaster3000.database.objects.Coworker;
import at.lvmaster3000.database.objects.Date;

public class DBLDatesTest extends AndroidTestCase{
	private DBLDates dblObjects;
	private List<Date> testObjects = null;
	private int NR_TEST_OBJECTS = 0;
	
	private RenamingDelegatingContext testContext = null;
	
	public void setUp(){
		testContext = new RenamingDelegatingContext(getContext(), "test_");
		
		dblObjects = new DBLDates(testContext);
		createTestObjects();
	}
	
	public void testAddNew(){
		dropAllObjects();
		createTestObjects();
		
		long idFromDatabase = dblObjects.addDate(this.testObjects.get(0));
		
		assertNotSame(-1l, idFromDatabase);
	}
	
	public void testGetAll(){
//		dropAllObjects();
//		fillTestObjectsInDBL();
		
		DDTestsetA a = new DDTestsetA(this.testContext);
		a.FillDb();
		
		int cnt = dblObjects.getDates(0).getDates().size();
		
		assertEquals(a.getDatesCnt(), cnt);
	}
	
	private void fillTestObjectsInDBL(){
		if(this.testObjects == null){
			createTestObjects();
		}
		
		for(Date object : this.testObjects){
			this.dblObjects.addDate(object);
		}
	}
	
	private void createTestObjects(){
		Date o1 = new Date();
		Date o2 = new Date();
	
		this.testObjects = new ArrayList<Date>();
		
		this.testObjects.add(o1);
		this.testObjects.add(o2);
		
		this.NR_TEST_OBJECTS = this.testObjects.size();
	}
	
	private void dropAllObjects(){
		this.dblObjects = null;
		this.dblObjects = new DBLDates(testContext);
	}
}
