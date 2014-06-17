package at.lvmaster3000.database.lists.test;

import android.test.AndroidTestCase;
import at.lvmaster3000.database.interfaces.IListTests;
import at.lvmaster3000.database.lists.Lectures;
import at.lvmaster3000.database.objects.Lecture;

public class LecturesTest extends AndroidTestCase implements IListTests{
	
	private Lectures lectures;
	
	public void setUp() {
		this.lectures = new Lectures();
	}

	@Override
	public void testAddToList() {
		Lecture l1 = new Lecture(0, "123.456", "Some asdf", "asadfasdf", "LV", 0, 0);
		this.lectures.addLecture(l1);
		
		assertSame(1, this.lectures.getLectures().size());
	}

	@Override
	public void testPrintList() {
		Lecture l1 = new Lecture(0, "123.456", "Some asdf", "asadfasdf", "LV", 0, 0);
		this.lectures.addLecture(l1);
		
		try {
			this.lectures.printLectureList();
		} catch (Exception e) {
			assertNotNull(null);
		}
	}

	@Override
	public void testClearList() {
		this.lectures.clear();
	}

	
	
}
