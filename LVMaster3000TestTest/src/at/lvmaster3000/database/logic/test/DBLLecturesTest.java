package at.lvmaster3000.database.logic.test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;
import at.lvmaster3000.database.demodata.DDTestsetA;
import at.lvmaster3000.database.logic.DBLLectures;
import at.lvmaster3000.database.objects.Lecture;
import at.lvmaster3000.database.objects.Resource;

public class DBLLecturesTest extends AndroidTestCase{
	
	private DBLLectures dblLectures;
	private RenamingDelegatingContext context;
	private DDTestsetA testset;
	
	public static final String LOG_TAG_LECTURES_LOGIC_TEST = "TEST_LECTURES_LOGIC";
	
	public void setUp(){
		this.context = new RenamingDelegatingContext(getContext(), "test_");			
		this.dblLectures = new DBLLectures(this.context);
		this.testset = new DDTestsetA(this.context);
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
		this.testset.FillDb();		
		this.dblLectures.getLectures(0).printLectureList();
		
		Lecture l = this.dblLectures.getLectures(1).getLectures().get(0);
		int cnt = this.dblLectures.getTasksForLecture(l).getTasks().size();
		
		assertEquals(cnt, this.testset.getTasksCnt());
	}
	
	public void testGetAllExamsOfLecture(){
		
	}
	
	public void testGetAllLectures(){
		this.testset.FillDb();		
		int cnt = this.dblLectures.getLectures(0).getLectures().size();
		
		assertEquals(cnt, this.testset.getLectureCnt());
	}
	
	public void voidAddLectureWithResources() {
		Lecture lecture = new Lecture(0, "705.001", "LV with res test", "some res added", "LV", 0, 0);
		Resource resource = new Resource("Some res");
		
		long rid = this.dblLectures.addResourceToLecture(resource, lecture);
		
		assertNotSame(-1L, rid);
	}
	
	public void testGetResourcesForLecture() {
		Lecture lecture = new Lecture(0, "705.001", "LV with res test", "some res added", "LV", 0, 0);
		this.dblLectures.addLecture(lecture);
		
		Resource resource = new Resource("Some res");		
		long rid = this.dblLectures.addResourceToLecture(resource, lecture);
		
		Log.i(LOG_TAG_LECTURES_LOGIC_TEST, "rid: " + rid);
		
		assertNotSame(-1L, rid);
	}
}
