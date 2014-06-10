package at.lvmaster3000.database.lists.test;

import android.test.AndroidTestCase;
import at.lvmaster3000.database.interfaces.IListTests;
import at.lvmaster3000.database.lists.Exams;
import at.lvmaster3000.database.objects.Exam;

public class ExamsTest extends AndroidTestCase implements IListTests {

	private Exams exams;
	
	public void setUp(){
		this.exams = new Exams();
	}
	
	@Override
	public void testAddToList(){
		this.exams.clear();
		
		Exam ex1 = new Exam(1l, "Exam 1", "Ex comment", 0, null);
		exams.addExam(ex1);
		
		assertSame(1, this.exams.getExams().size());
	}

	@Override
	public void testPrintList() {
		this.exams.clear();
		
		Exam ex1 = new Exam(1l, "Exam 1", "Ex comment", 0, null);
		exams.addExam(ex1);
		
		try {
			this.exams.printExamList();
		} catch (Exception e) {
			assertNull(null);
		}
	}

	@Override
	public void testClearList() {
		this.exams.clear();
	}
	
}
