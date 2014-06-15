package at.lvmaster3000.database.logic.test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.demodata.DDTestsetA;
import at.lvmaster3000.database.interfaces.IDBLTests;
import at.lvmaster3000.database.logic.DBLDates;
import at.lvmaster3000.database.objects.Date;

public class DBLDatesTest extends AndroidTestCase implements IDBLTests {
	private DBLDates dblDates;
	
	private RenamingDelegatingContext context = null;
	
	public void setUp(){
		context = new RenamingDelegatingContext(getContext(), "test_");		
		dblDates = new DBLDates(context);
	}
	
	@Override
	public void testAddNew(){
		long unixTime = System.currentTimeMillis() / 1000L;
		long id = dblDates.addDate(unixTime, "i13","","Comment");		
		assertEquals(1, id);
	}
	
	@Override
	public void testDelete() {
		long unixTime = System.currentTimeMillis() / 1000L;
		long id = dblDates.addDate(unixTime, "i13","","Comment");		
		assertEquals(1, this.dblDates.deleteDate(id));
	}

	@Override
	public void testUpdate() {
		long unixTime = System.currentTimeMillis() / 1000L;
		Date date = new Date(0, unixTime, "i13", "", "Comment");
		long id = this.dblDates.addDate(date);
		assertEquals(1, id);
		
		date.setLocation("i12");
		assertEquals(1, this.dblDates.updateDate(date));
	}
	
	public void testGetAll(){		
		DDTestsetA a = new DDTestsetA(this.context);
		a.FillDb();
		
		int cnt = dblDates.getDates(0).getDates().size();		
		assertEquals(a.getDatesCnt(), cnt);
	}
}
