package at.lvmaster3000.database.logic.test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;
import at.lvmaster3000.database.logic.DBLLectures;
import at.lvmaster3000.database.logic.DBLTasks;
import at.lvmaster3000.database.objects.Lecture;
import at.lvmaster3000.database.objects.Task;

public class DBLLecturesTest extends AndroidTestCase{
	
	private DBLLectures dblLectures;
	private DBLTasks dblTasks;
	
	public static final String LOG_TAG_LECTURES_LOGIC_TEST = "TEST_LECTURES_LOGIC";
	
	public void setUp(){
		RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");			
		this.dblLectures = new DBLLectures(context);
		this.dblTasks = new DBLTasks(context);
	}
	
	public void testAddNewLecture(){
		long id = this.dblLectures.addLecture("701.001","Test LV", "Some comment...", "LV", 1, 1);		
		Log.i(DBLLecturesTest.LOG_TAG_LECTURES_LOGIC_TEST, "(testAddNewLecture) InsertID: " + id);
		assertNotSame(-1l, id);
	}
	
	public void testDeleteLecture(){
		long id = this.dblLectures.addLecture("701.001","Test LV", "Some comment...", "LV", 1, 1);
		Log.i(DBLLecturesTest.LOG_TAG_LECTURES_LOGIC_TEST, "(testDeleteLecture) InsertID: " + id);
		int res = this.dblLectures.deleteLecture(id);
		assertNotSame(0, res);
	}
	
	public void testGetLectureByNumber(){
		String number = "701.001";
		
		this.dblLectures.addLecture(number, "Test LV", "Some comment...", "LV", 1, 1);		
		Lecture lec = this.dblLectures.getLectureByNumber(number);
		
		Log.i(DBLLecturesTest.LOG_TAG_LECTURES_LOGIC_TEST, "(testGetLectureByNumber) Number: " + lec.getNumber());
		
		assertEquals(lec.getNumber(), number);
	}

	public void testGetTasksForLecture(){
		
	}
	
	public void testGetAllExamsOfLecture(){
		
	}
	
	public void testGetAllLectures(){
		this.insertTestsetToDb();
	}
	
	private void insertTestsetToDb(){		
		Lecture l1 = new Lecture(0, "301.000", "sophisticated 1", "good prof", "SE", 1, 1);
		Lecture l2 = new Lecture(0, "301.001", "sophisticated 1", "good prof", "VO", 1, 0);
		
		long id1 = this.dblLectures.addLecture(l1);
		Log.i(DBLLecturesTest.LOG_TAG_LECTURES_LOGIC_TEST, "(insertTestsetToDb) InsertID: " + id1);
		l1.setID(id1);
		
		long id2 = this.dblLectures.addLecture(l2);
		Log.i(DBLLecturesTest.LOG_TAG_LECTURES_LOGIC_TEST, "(insertTestsetToDb) InsertID: " + id2);
		l1.setID(id2);	
		
		Task t1 = new Task(0, "Task 1", "Some...", null);
		this.dblTasks.addTask(t1);
	}
}
