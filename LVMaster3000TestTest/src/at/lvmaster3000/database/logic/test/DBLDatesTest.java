package at.lvmaster3000.database.logic.test;

import java.util.ArrayList;
import java.util.List;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.demodata.DDTestsetA;
import at.lvmaster3000.database.interfaces.IDBLTests;
import at.lvmaster3000.database.lists.Dates;
import at.lvmaster3000.database.logic.DBLDates;
import at.lvmaster3000.database.objects.Coworker;
import at.lvmaster3000.database.objects.Date;

public class DBLDatesTest extends AndroidTestCase implements IDBLTests {
	private DBLDates dblObjects;
	private List<Date> testObjects = null;
	private int NR_TEST_OBJECTS = 0;
	
	private RenamingDelegatingContext testContext = null;
	
	public void setUp(){
		testContext = new RenamingDelegatingContext(getContext(), "test_");
		
		dblObjects = new DBLDates(testContext);
		createTestObjects();
	}
	
	@Override
	public void testAddNew(){
		dropAllObjects();
		createTestObjects();
		
		long idFromDatabase = dblObjects.addDate(this.testObjects.get(0));
		
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
		DDTestsetA a = new DDTestsetA(this.testContext);
		a.FillDb();
		
		int cnt = dblObjects.getDates(0).getDates().size();
		
		assertEquals(a.getDatesCnt(), cnt);
	}
		
	private void createTestObjects(){
		long unixTime = System.currentTimeMillis() / 1000L;
		
		Date o1 = new Date();
		o1.setType("LV");
		o1.setComment("some d1 comment");
		o1.setTimestamp(unixTime);
		o1.setLocation("i13");
		
		Date o2 = new Date();
		o2.setType("VU");
		o2.setComment("some d2 comment");
		o2.setTimestamp(unixTime + 3600);
		o2.setLocation("i12");
	
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
