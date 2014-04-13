package at.lvmaster3000.database.lists.test;

import android.test.AndroidTestCase;
import at.lvmaster3000.database.lists.Dates;
import at.lvmaster3000.database.objects.Date;

public class DatesTest extends AndroidTestCase{
private Dates dates;
	
	public void startUp(){
		dates = new Dates();
	}
	
	public void addToListTest(){
		clearList();
		
		Date d = new Date(1,0l,"TU Graz","milestone","too early in the morning");
		dates.addDate(d);
		
		assertEquals(1, dates.nrOfDates());
	}
	
	private void clearList(){
		this.dates.deleteAllDates();
	}
}
