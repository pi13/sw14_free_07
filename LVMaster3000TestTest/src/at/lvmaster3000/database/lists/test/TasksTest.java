package at.lvmaster3000.database.lists.test;

import android.test.AndroidTestCase;
import at.lvmaster3000.database.interfaces.IListTests;
import at.lvmaster3000.database.lists.Tasks;
import at.lvmaster3000.database.objects.Task;

public class TasksTest extends AndroidTestCase implements IListTests {

	private Tasks tasks;
	
	public void setUp() {
		this.tasks = new Tasks();
	}

	@Override
	public void testAddToList() {
		Task t1 = new Task(0, "T1", "Comment", null);
		this.tasks.addTask(t1);
		
		assertSame(1, this.tasks.getTasks().size());
	}

	@Override
	public void testPrintList() {
		Task t1 = new Task(0, "T1", "Comment", null);
		this.tasks.addTask(t1);
		
		try {
			this.tasks.printTaskList();
		} catch (Exception e) {
			assertNull(null);
		}
	}

	@Override
	public void testClearList() {
		this.tasks.clear();
	}
}
