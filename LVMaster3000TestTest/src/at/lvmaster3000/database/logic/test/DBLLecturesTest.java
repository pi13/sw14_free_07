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
		dropAllObjects();
		createTestObjects();
		fillTestLecturesInDBL();
		
		assertEquals(testObjects.size(), dblObjects.getLectures().nrOfLectures());		
		dblObjects.deleteLecture(testObjects.get(0));		
		assertEquals(testObjects.size()-1, dblObjects.getLectures().nrOfLectures());
		Lecture remaining = dblObjects.getLectures().getLectures().get(0);
		assertEquals(testObjects.get(1).getNumber(), remaining.getNumber());
	}
	
	public void testGetLectureByNumber(){
		dropAllObjects();
		createTestObjects();
		fillTestLecturesInDBL();
		
		Lecture lec = dblObjects.getLectureByNumber(testObjects.get(0).getNumber());
		assertEquals(lec.getNumber(), testObjects.get(0).getNumber());
	}

	public void testGetTasksForLecture(){
		dropAllObjects();
		createTestObjects();
		fillTestLecturesInDBL();
		
		Tasks tasks = dblObjects.getTasksForLecture(testObjects.get(0));
		// TODO useful test case
		assertEquals(true, false);
	}
	
	public void testGetAllExamsOfLecture(){
		dropAllObjects();
		createTestObjects();
		
		// TODO useful test case
		long lecId = dblObjects.addLecture("300.400", "stochastik", "2 teilprüfungen", "VO", 1, 0);
		Exam ex1 = new Exam(0, "stoch1", "1. teilpruefung", lecId);
		Exam ex2 = new Exam(0, "stoch2", "2.teilpruefung", lecId);
		
		DBLExams dblEx = new DBLExams(testContext);
		dblEx.add(ex1);
		dblEx.add(ex2);
		
		Lecture lecture = dblObjects.getLectureByNumber("300.400");
		Exams exams = dblObjects.getExamsForLecture(lecture);
		
		assertEquals(exams.nrOfExams(), 2);
		boolean containsEx1 = false;
		if(exams.getExam().get(0).getTitle().equals("stoch1") || exams.getExam().get(1).getTitle().equals("stoch1"))
			containsEx1 = true;
		assertEquals(true, containsEx1);
		
		boolean containsEx2 = false;
		if(exams.getExam().get(0).getTitle().equals("stoch2") || exams.getExam().get(1).getTitle().equals("stoch2"))
			containsEx2 = true;
		assertEquals(true, containsEx2);
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
