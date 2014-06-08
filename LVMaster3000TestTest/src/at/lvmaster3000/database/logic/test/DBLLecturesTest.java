package at.lvmaster3000.database.logic.test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;
import at.lvmaster3000.database.demodata.DDTestsetA;
import at.lvmaster3000.database.logic.DBLLectures;
import at.lvmaster3000.database.logic.DBLResources;
import at.lvmaster3000.database.objects.Lecture;
import at.lvmaster3000.database.objects.Resource;

public class DBLLecturesTest extends AndroidTestCase{
	
	private DBLLectures dblLectures;
	private DBLResources dblResources;
	
	private RenamingDelegatingContext context;
	
	public static final String LOG_TAG_LECTURES_LOGIC_TEST = "TEST_LECTURES_LOGIC";
	
	public void setUp(){
		this.context = new RenamingDelegatingContext(getContext(), "test_");
		
		this.dblLectures = new DBLLectures(this.context);
		this.dblLectures.resetTablesInvolved();
		
		this.dblResources = new DBLResources(this.context);
	}
	
	public void testAddNewLecture(){
		long id = this.dblLectures.addLecture("701.001","Test LV", "Some comment...", "LV", 1, 1);	
		assertNotSame(-1l, id);
	}
	
	public void testDeleteLecture(){
		long id = this.dblLectures.addLecture("701.001","Test LV", "Some comment...", "LV", 1, 1);
		assertSame(1, this.dblLectures.deleteLecture(id));
	}
	
	public void testUpdateLecture() {
		Lecture lecture = new Lecture(0, "701.001","Test LV", "Some comment...", "LV", 1, 1);		
		assertEquals(1, this.dblLectures.addLecture(lecture));
		
		String title = "New title for LV 701.001";
		lecture.setName(title);
		
		assertEquals(1, this.dblLectures.updateLecture(lecture));
	}
	
	public void testGetLectureByNumber(){
		String number = "701.001";
		
		this.dblLectures.addLecture(number, "Test LV", "Some comment...", "LV", 1, 1);		
		Lecture lecture = this.dblLectures.getLectureByNumber(number);
		
		assertEquals(lecture.getNumber(), number);
	}

	public void testGetTasksForLecture(){
		DDTestsetA testset = new DDTestsetA(this.context);
		testset.FillDb();		
		
		Lecture l = this.dblLectures.getLectures(1).getLectures().get(0);
		int cnt = this.dblLectures.getTasksForLecture(l).getTasks().size();
		
		assertEquals(cnt, testset.getTasksCnt());
	}
	
	public void testGetAllLectures(){
		this.dblLectures.addLecture("701.001","Test LV 1", "Some comment...", "LV", 1, 1);
		this.dblLectures.addLecture("702.001","Test LV 2", "Some comment...", "LV", 0, 0);;		
		
		assertEquals(2, this.dblLectures.getLectures(0).getLectures().size());
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
		this.dblResources.addResource(resource);
				
		long rid = this.dblLectures.addResourceToLecture(resource, lecture);
		
		Log.i(LOG_TAG_LECTURES_LOGIC_TEST, "rid: " + rid);
		
		assertNotSame(-1L, rid);
	}
}
