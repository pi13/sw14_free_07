package at.lvmaster3000.database.logic.test;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.IsolatedContext;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContentResolver;
import android.test.mock.MockContext;
import at.lvmaster3000.database.lists.Exams;
import at.lvmaster3000.database.lists.Lectures;
import at.lvmaster3000.database.lists.Tasks;
import at.lvmaster3000.database.logic.DBLExams;
import at.lvmaster3000.database.logic.DBLLectures;
import at.lvmaster3000.database.objects.Coworker;
import at.lvmaster3000.database.objects.Exam;
import at.lvmaster3000.database.objects.Lecture;

public class DBLExamsTest extends AndroidTestCase{
	private DBLExams dblObjects;
	private List<Exam> testObjects = null;
	private int NR_TEST_EXAMS = 0;
	
	private RenamingDelegatingContext testContext = null;
	
	public void startUp(){
		testContext = new RenamingDelegatingContext(getContext(), "test_");
		
		dblObjects = new DBLExams(testContext);
		createTestObjects();
	}
	
	public void testAddNewExam(){
		dropAllObjects();
		createTestObjects();
		
		long idFromDatabase = dblObjects.add(this.testObjects.get(0));
		
		assertNotSame(-1l, idFromDatabase);
	}
	
	public void testGetAllExams(){
		dropAllObjects();
		fillTestExamsInDBL();
		Exams exs = dblObjects.getAll();
		
		assertEquals(NR_TEST_EXAMS, exs.nrOfExams());
	}
	
	private void fillTestExamsInDBL(){
		if(this.testObjects == null){
			createTestObjects();
		}
		
		for(Exam ex : this.testObjects){
			this.dblObjects.add(ex);
		}
	}
	
	private void createTestObjects(){
		Exam e1 = new Exam();
		Exam e2 = new Exam();
	
		this.testObjects = new ArrayList<Exam>();
		
		this.testObjects.add(e1);
		this.testObjects.add(e2);
		
		this.NR_TEST_EXAMS = this.testObjects.size();
	}
	
	private void dropAllObjects(){
		this.dblObjects = null;
		this.dblObjects = new DBLExams(testContext);
	}
}
