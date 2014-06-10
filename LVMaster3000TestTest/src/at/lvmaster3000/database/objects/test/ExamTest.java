package at.lvmaster3000.database.objects.test;

import android.test.AndroidTestCase;
import at.lvmaster3000.database.interfaces.IObjectTest;
import at.lvmaster3000.database.objects.Exam;

public class ExamTest extends AndroidTestCase implements IObjectTest {

	@Override
	public void testCreateObject() {
		Exam ex = new Exam(0, "title", "comment ...", 0, null);
	}

	@Override
	public void testFillObject() {
		Exam ex1 = new Exam(0, "title", "comment ...", 0, null);
		Exam ex2 = new Exam(0, "title", "comment ...", 0, null);
		
		assertSame(ex1.getTitle(), ex2.getTitle());
	}

	@Override
	public void testPrintDetails() {
		Exam ex = new Exam(0, "title", "comment ...", 0, null);
		
		try {
			ex.printExam();
		} catch (Exception e) {
			assertNotNull(null);
		}
	}

}
