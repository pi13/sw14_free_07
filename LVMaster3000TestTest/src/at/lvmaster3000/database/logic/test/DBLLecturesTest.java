package at.lvmaster3000.database.logic.test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;
import at.lvmaster3000.database.demodata.DDTestsetA;
import at.lvmaster3000.database.logic.DBLLectures;
import at.lvmaster3000.database.objects.Lecture;

public class DBLLecturesTest extends AndroidTestCase{
	
	private DBLLectures dblLectures;
	private RenamingDelegatingContext context;
	
	public static final String LOG_TAG_LECTURES_LOGIC_TEST = "TEST_LECTURES_LOGIC";
	
	public void setUp(){
		this.context = new RenamingDelegatingContext(getContext(), "test_");			
		this.dblLectures = new DBLLectures(context);
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
		DDTestsetA a = new DDTestsetA(this.context);
		a.FillDb();
		
		this.dblLectures.getLectures(0).printLectureList();
		
		Lecture l = this.dblLectures.getLectures(1).getLectures().get(0);
		int cnt = this.dblLectures.getTasksForLecture(l).getTasks().size();
		
		assertEquals(cnt, a.getTasksCnt());
	}
	
	public void testGetAllExamsOfLecture(){
		
	}
	
	public void testGetAllLectures(){
		DDTestsetA a = new DDTestsetA(this.context);
		a.FillDb();
		
		int cnt = this.dblLectures.getLectures(0).getLectures().size();
		
		assertEquals(cnt, a.getLectureCnt());
	}
}
