package at.lvmaster3000.database.logic.test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.interfaces.IDBLTests;
import at.lvmaster3000.database.logic.DBLTasks;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Task;

public class DBLTasksTest extends AndroidTestCase implements IDBLTests {
	private DBLTasks dblTasks;	
	private RenamingDelegatingContext context = null;
	
	public static final String LOG_TAG_TASKS_LOGIC_TEST = "TEST_TASKS_LOGIC";
	
	public void setUp(){
		this.context = new RenamingDelegatingContext(getContext(), "test_");		
		this.dblTasks = new DBLTasks(context);
		this.dblTasks.resetTablesInvolved();
	}
	
	@Override
	public void testAddNew(){		
		long id = dblTasks.addTask("T1", "comment");
		assertSame(1l, id);
	}
	
	@Override
	public void testDelete() {
		long id = dblTasks.addTask("T1", "comment");	
		assertSame(1l, id);
		assertSame(1, this.dblTasks.deleteTask(id));
	}

	@Override
	public void testUpdate() {
		Task task = new Task(0, "T1", "comment", null);
		assertSame(1l, this.dblTasks.addTask(task));
		
		task.setTitle("T 1.1");
		assertSame(1, this.dblTasks.updateTask(task));
	}
	
	public void testCreateTaskWithDate() {
		long unixTime = System.currentTimeMillis() / 1000L;
		Date date = new Date(0, unixTime, "i13", "", "Comment ...");		
		Task task = new Task(0, "Task title", "Comment ...", date);
		
		this.dblTasks.addTask(task);
		
		boolean eval = ((long)date.getTimestamp() == (long)this.dblTasks.getTaskDate(task).getTimestamp());
		
		assertTrue(eval);
	}
}
