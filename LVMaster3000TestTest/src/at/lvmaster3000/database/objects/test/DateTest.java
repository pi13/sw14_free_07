package at.lvmaster3000.database.objects.test;

import android.test.AndroidTestCase;
import at.lvmaster3000.database.interfaces.IObjectTest;
import at.lvmaster3000.database.objects.Date;

public class DateTest extends AndroidTestCase implements IObjectTest {

	@Override
	public void testCreateObject() {
		long unixTime = System.currentTimeMillis() / 1000L;
		Date date = new Date(0, unixTime, "i13", "", "Comment ...");
		assertNotNull(date);
	}

	@Override
	public void testFillObject() {
		long unixTime = System.currentTimeMillis() / 1000L;
		Date d1 = new Date(0, unixTime, "i13", "", "Comment ...");
		Date d2 = new Date(0, unixTime + (3600 * 48), "i13", "", "Comment ...");
		
		assertNotSame(d1.getTimestamp(), d2.getTimestamp());
	}

	@Override
	public void testPrintDetails() {
		long unixTime = System.currentTimeMillis() / 1000L;
		Date date = new Date(0, unixTime, "i13", "", "Comment ...");
		
		try {
			date.printDate();
		} catch (Exception e) {
			assertNotNull(null);
		}
	}

}
