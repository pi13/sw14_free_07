package at.lvmaster3000.database.logic.test;

import java.util.ArrayList;
import java.util.List;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;
import at.lvmaster3000.database.helper.test.HLPLecturesTest;
import at.lvmaster3000.database.lists.Exams;
import at.lvmaster3000.database.lists.Lectures;
import at.lvmaster3000.database.lists.Tasks;
import at.lvmaster3000.database.logic.DBLExams;
import at.lvmaster3000.database.logic.DBLLectures;
import at.lvmaster3000.database.objects.Exam;
import at.lvmaster3000.database.objects.Lecture;

public class DBLLecturesTest extends AndroidTestCase{
	
	private DBLLectures dblObject;
	private List<Lecture> testObjects = null;
	private int NR_TEST_LECTURES = 0;
	
	public static final String LOG_TAG_LECTURES_LOGIC_TEST = "TEST_LECTURES_LOGIC";
	
	public void setUp(){
		RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");			
		this.dblObject = new DBLLectures(context);
	}
	
	public void testAddNewLecture(){
		long id = this.dblObject.addLecture("701.001","Test LV", "Some comment...", "LV", 1, 1);		
		Log.i(DBLLecturesTest.LOG_TAG_LECTURES_LOGIC_TEST, "InsertID: " + id);
		assertNotSame(-1l, id);
	}
	
	public void testDeleteLecture(){
//		long id = this.dblObject.addLecture("701.001","Test LV", "Some comment...", "LV", 1, 1);
//		int res = this.dblObject.deleteLecture(id);
//		assertNotSame(0, res);
	}
	
	public void testGetLectureByNumber(){
		String number = "701.001";
		
		this.dblObject.addLecture(number, "Test LV", "Some comment...", "LV", 1, 1);		
		Lecture lec = this.dblObject.getLectureByNumber(number);
		
		assertEquals(lec.getNumber(), number);
	}

	public void testGetTasksForLecture(){
//		this.insertTestsetToDb();
//		this.dblObject.getTasksForLecture(this.dblObject.getLectureByNumber("301.001"));
	}
	
	public void testGetAllExamsOfLecture(){
//		dropAllObjects();
//		createTestObjects();
//		
//		// TODO useful test case
//		long lecId = dblObject.addLecture("300.400", "stochastik", "2 teilprüfungen", "VO", 1, 0);
//		Exam ex1 = new Exam(0, "stoch1", "1. teilpruefung", lecId);
//		Exam ex2 = new Exam(0, "stoch2", "2.teilpruefung", lecId);
//		
//		DBLExams dblEx = new DBLExams(testContext);
//		dblEx.add(ex1);
//		dblEx.add(ex2);
//		
//		String number = "701.001";
//		this.dblObject.addLecture(number, "Test LV", "Some comment...", "LV", 1, 1);
//		Lecture lecture = this.dblObject.getLectureByNumber(number);
		
//		Exams exams = dblObject.getExamsForLecture(lecture);
//		
//		assertEquals(exams.nrOfExams(), 2);
//		boolean containsEx1 = false;
//		if(exams.getExam().get(0).getTitle().equals("stoch1") || exams.getExam().get(1).getTitle().equals("stoch1"))
//			containsEx1 = true;
//		assertEquals(true, containsEx1);
//		
//		boolean containsEx2 = false;
//		if(exams.getExam().get(0).getTitle().equals("stoch2") || exams.getExam().get(1).getTitle().equals("stoch2"))
//			containsEx2 = true;
//		assertEquals(true, containsEx2);
		
//		this.dblObject.getExamsForLecture(lecture);
		
	}
	
	public void testGetAllLectures(){
//		this.createTestObjects();
//		Lectures lecs = this.dblObject.getLectures(0);
//		
//		assertEquals(NR_TEST_LECTURES, lecs.getLectures().size());
	}
	
	private void insertTestsetToDb(){
		if(this.testObjects == null){
			this.createTestObjects();
		}
		
		for(Lecture lec : this.testObjects){
			this.dblObject.addLecture(lec);
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
//		this.dblObject = null;
//		this.dblObject = new DBLLectures(testContext);
	}
}
