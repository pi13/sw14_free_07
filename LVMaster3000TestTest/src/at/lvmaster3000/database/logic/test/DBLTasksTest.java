package at.lvmaster3000.database.logic.test;

import java.util.ArrayList;
import java.util.List;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;
import at.lvmaster3000.database.interfaces.IDBLTests;
import at.lvmaster3000.database.lists.Tasks;
import at.lvmaster3000.database.logic.DBLTasks;
import at.lvmaster3000.database.objects.Task;

public class DBLTasksTest extends AndroidTestCase implements IDBLTests {
	private DBLTasks dblObjects;
	private List<Task> testObjects = null;
	private int NR_TEST_OBJECTS = 0;
	
	private RenamingDelegatingContext testContext = null;
	
	public static final String LOG_TAG_TASKS_LOGIC_TEST = "TEST_TASKS_LOGIC";
	
	public void setUp(){
		testContext = new RenamingDelegatingContext(getContext(), "test_");		
		dblObjects = new DBLTasks(testContext);
		createTestObjects();
	}
	
	@Override
	public void testAddNew(){
		dropAllObjects();
		createTestObjects();
		
		long idFromDatabase = dblObjects.addTask(this.testObjects.get(0));
		
		assertNotSame(-1l, idFromDatabase);
	}
	
	@Override
	public void testDelete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testUpdate() {
		// TODO Auto-generated method stub
		
	}
	
	public void testGetAll(){
		dropAllObjects();
		fillTestObjectsInDBL();
		Tasks tasks = dblObjects.getTasks(0);
		
		Log.w(LOG_TAG_TASKS_LOGIC_TEST, "size: " + tasks.getTasks().size());
		
		assertEquals(NR_TEST_OBJECTS, tasks.getTasks().size());
	}
	
	private void fillTestObjectsInDBL(){
		if(this.testObjects == null){
			createTestObjects();
		}
		
		for(Task task : this.testObjects) {
			task.printTask();
			this.dblObjects.addTask(task);
		}
	}
	
	private void createTestObjects(){
		Task o1 = new Task();
		o1.setTitle("Titel T1");
		o1.setComment("Comment T 1");
		
		Task o2 = new Task();
		o2.setTitle("Title T2");
		o2.setComment("Comment T2");
		
		this.testObjects = new ArrayList<Task>();
	
		this.testObjects.add(o1);
		this.testObjects.add(o2);
		
		this.NR_TEST_OBJECTS = this.testObjects.size();
	}
	
	private void dropAllObjects(){
		this.dblObjects = null;
		this.dblObjects = new DBLTasks(testContext);
	}	
}
