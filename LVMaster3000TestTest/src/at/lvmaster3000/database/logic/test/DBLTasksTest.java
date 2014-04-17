package at.lvmaster3000.database.logic.test;

import java.util.ArrayList;
import java.util.List;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.lists.Tasks;
import at.lvmaster3000.database.logic.DBLTasks;
import at.lvmaster3000.database.objects.Coworker;
import at.lvmaster3000.database.objects.Task;

public class DBLTasksTest extends AndroidTestCase{
	private DBLTasks dblObjects;
	private List<Task> testObjects = null;
	private int NR_TEST_OBJECTS = 0;
	
	private RenamingDelegatingContext testContext = null;
	
	public void startUp(){
		testContext = new RenamingDelegatingContext(getContext(), "test_");
		
		dblObjects = new DBLTasks(testContext);
		createTestObjects();
	}
	
	public void testAddNew(){
		dropAllObjects();
		createTestObjects();
		
		long idFromDatabase = dblObjects.add(this.testObjects.get(0));
		
		assertNotSame(-1l, idFromDatabase);
	}
	
	public void testGetAll(){
		dropAllObjects();
		fillTestObjectsInDBL();
		Tasks tasks = dblObjects.getAll();
		
		assertEquals(NR_TEST_OBJECTS, tasks.nrOfObjects());
	}
	
	private void fillTestObjectsInDBL(){
		if(this.testObjects == null){
			createTestObjects();
		}
		
		for(Task task : this.testObjects){
			this.dblObjects.add(task);
		}
	}
	
	private void createTestObjects(){
		Task o1 = new Task();
		Task o2 = new Task();
		
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
