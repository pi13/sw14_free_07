package at.lvmaster3000.database.logic.test;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.IsolatedContext;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContentResolver;
import android.test.mock.MockContext;
import at.lvmaster3000.database.lists.Lectures;
import at.lvmaster3000.database.lists.Tasks;
import at.lvmaster3000.database.logic.DBLLectures;
import at.lvmaster3000.database.objects.Coworker;
import at.lvmaster3000.database.objects.Lecture;

public class DBLLecturesTest extends AndroidTestCase{
	private DBLLectures dblObjects;
	private List<Lecture> testObjects = null;
	private int NR_TEST_LECTURES = 0;
	
	private RenamingDelegatingContext testContext = null;
	
	public void startUp(){
		testContext = new RenamingDelegatingContext(getContext(), "test_");
		
		dblObjects = new DBLLectures(testContext);
		createTestObjects();
	}
	
	public void testAddNewLecture(){
		dropAllObjects();
		createTestObjects();
		
		long idFromDatabase = dblObjects.addLecture(this.testObjects.get(0));
		
		assertNotSame(-1l, idFromDatabase);
		
		idFromDatabase = dblObjects.addLecture("301.000", "sophisticated 1", "good prof","SEM", 1, 1);
		
		assertNotSame(-1l, idFromDatabase);
	}
	
	public void testDeleteLecture(){
		dblObjects.deleteLecture(testObjects.get(0));
	}
	
	public void testGetLectureByNumber(){
		Lecture lec = dblObjects.getLectureByNumber("300.000");
	}

	public void testGetTasksForLecture(){
		Tasks tasks = dblObjects.getTasksForLecture(testObjects.get(0));
	}
	
	public void testGetAllLectures(){
		dropAllObjects();
		fillTestLecturesInDBL();
		Lectures lecs = dblObjects.getAll();
		
		assertEquals(NR_TEST_LECTURES, lecs.nrOfLectures());
	}
	
	private void fillTestLecturesInDBL(){
		if(this.testObjects == null){
			createTestObjects();
		}
		
		for(Lecture lec : this.testObjects){
			this.dblObjects.addLecture(lec);
		}
	}
	
	private void createTestObjects(){
		Lecture l1 = new Lecture(-1l, "301.000", "sophisticated 1", "good prof","SEM", 1, 1);
		Lecture l2 = new Lecture(-2l, "301.001", "sophisticated 1", "good prof","VO", 1, 1);
	
		this.testObjects = new ArrayList<Lecture>();
		
		this.testObjects.add(l1);
		this.testObjects.add(l2);
		
		this.NR_TEST_LECTURES = this.testObjects.size();
	}
	
	private void dropAllObjects(){
		this.dblObjects = null;
		this.dblObjects = new DBLLectures(testContext);
	}
}
