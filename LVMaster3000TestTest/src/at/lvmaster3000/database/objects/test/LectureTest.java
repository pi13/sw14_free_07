package at.lvmaster3000.database.objects.test;

import android.test.AndroidTestCase;
import at.lvmaster3000.database.interfaces.IObjectTest;
import at.lvmaster3000.database.objects.Lecture;

public class LectureTest extends AndroidTestCase implements IObjectTest {

	@Override
	public void testCreateObject() {
		Lecture l = new Lecture(0, "123.456", "LV name", "Comment ...", "LV", 0, 0);
		assertNotNull(l);
	}

	@Override
	public void testFillObject() {
		Lecture l1 = new Lecture(0, "123.456", "LV name", "Comment ...", "LV", 0, 0);
		Lecture l2 = new Lecture(0, "123.456", "LV name", "Comment ...", "LV", 0, 0);
		
		assertSame(l1.getNumber(), l2.getNumber());
	}

	@Override
	public void testPrintDetails() {
		Lecture l = new Lecture(0, "123.456", "LV name", "Comment ...", "LV", 0, 0);
		
		try {
			l.printLecture();
		} catch (Exception e) {
			assertNotNull(null);
		}
	}

}
