package at.lvmaster3000.database.objects.test;

import android.test.AndroidTestCase;
import at.lvmaster3000.database.interfaces.IObjectTest;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Task;

public class TaskTest extends AndroidTestCase implements IObjectTest {

	@Override
	public void testCreateObject() {
		long unixTime = System.currentTimeMillis() / 1000L;
		Date date = new Date(0, unixTime, "i13", "", "Comment ...");		
		Task task = new Task(0, "Task title", "Comment ...", date);
		assertNotNull(task);
	}

	@Override
	public void testFillObject() {
		Task t1 = new Task(0, "Task title", "Comment ...", null);
		Task t2 = new Task(0, "Task title", "Comment ...", null);
		
		assertSame(t1.getTitle(), t2.getTitle());
	}

	@Override
	public void testPrintDetails() {
		Task task = new Task(0, "Task title", "Comment ...", null);
		
		try {
			task.printTask();
		} catch (Exception e) {
			assertNotNull(null);
		}
	}

}
