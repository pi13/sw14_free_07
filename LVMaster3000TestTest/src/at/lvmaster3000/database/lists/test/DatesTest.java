package at.lvmaster3000.database.lists.test;

import android.test.AndroidTestCase;
import at.lvmaster3000.database.interfaces.IListTests;
import at.lvmaster3000.database.lists.Dates;
import at.lvmaster3000.database.objects.Date;

public class DatesTest extends AndroidTestCase implements IListTests {
	
	private Dates dates;
	
	public void setUp(){
		dates = new Dates();
	}
	
	@Override
	public void testAddToList(){
		this.dates.clear();
		
		Date d = new Date(1,0l,"TU Graz","milestone","too early in the morning");
		dates.addDate(d);
		
		assertEquals(1, dates.nrOfDates());
	}
	
	@Override
	public void testPrintList() {
		Date d = new Date(1,0l,"TU Graz","milestone","too early in the morning");
		
		dates.addDate(d);
		
		try {
			this.dates.printDateList();
		} catch (Exception e) {
			assertNotNull(null);
		}
	}
	
	@Override
	public void testClearList() {
		this.dates.clear();
	}
}
