package at.lvmaster3000.database.logic.test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.helper.HLPExams;
import at.lvmaster3000.database.helper.HLPLectures;
import at.lvmaster3000.database.helper.HLPRelations;
import at.lvmaster3000.database.interfaces.IDBLTests;
import at.lvmaster3000.database.lists.Exams;
import at.lvmaster3000.database.logic.DBLLectures;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Exam;
import at.lvmaster3000.database.objects.Lecture;
import at.lvmaster3000.database.objects.Resource;
import at.lvmaster3000.database.objects.Task;

public class DBLLecturesTest extends AndroidTestCase implements IDBLTests {
	
	private DBLLectures dblLectures;
	
	private RenamingDelegatingContext context;
	
	public static final String LOG_TAG_LECTURES_LOGIC_TEST = "TEST_LECTURES_LOGIC";
	
	public void setUp(){
		this.context = new RenamingDelegatingContext(getContext(), "test_");
		
		this.dblLectures = new DBLLectures(this.context);
		this.dblLectures.resetTablesInvolved();
	}
	
	@Override
	public void testAddNew(){
		long id = this.dblLectures.addLecture("701.001","Test LV", "Some comment...", "LV", 1, 1);	
		assertSame(1l, id);
	}
	
	@Override
	public void testDelete(){
		long id = this.dblLectures.addLecture("701.001","Test LV", "Some comment...", "LV", 1, 1);
		assertSame(1l, id);
		assertSame(1, this.dblLectures.deleteLecture(id));
	}
	
	@Override
	public void testUpdate() {
		Lecture lecture = new Lecture(0, "701.001","Test LV", "Some comment...", "LV", 1, 1);		
		assertEquals(1l, this.dblLectures.addLecture(lecture));
		
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
						
		this.dblLectures.addResourceToLecture(new Resource("Some res 1"), lecture);
		this.dblLectures.addResourceToLecture(new Resource("Some res 2"), lecture);
		
		int cnt = dblLectures.getResourcesForLecture(lecture).getResources().size();
		
		assertSame(2, cnt);
	}
	
	public void testAddLectureWithExam() {
		Lecture lecture = new Lecture(0, "705.001", "LV with exam", "some text ...", "LV", 0, 0);
		this.dblLectures.addLecture(lecture);
		
		long eid = this.dblLectures.addExamToLecture(new Exam(0, "Ex1", "Ex1 comment", 0, null), lecture);
		
		assertNotSame(-1L, eid);
	}
	
	public void testGetExamsForLecture() {
		Lecture lecture = new Lecture(0, "705.001", "LV with exam", "some text ...", "LV", 0, 0);
		this.dblLectures.addLecture(lecture);
		
		long unixTime = System.currentTimeMillis() / 1000L;
		this.dblLectures.addExamToLecture(new Exam(0, "Ex1", "Ex1 comment", 0, new Date(0, unixTime, "i13", "", "date comment")), lecture);
		
		Exams exams = this.dblLectures.getExamsForLecture(lecture);
		
		assertSame(1, exams.getExams().size());
	}
	
	public void testGetExamsForLectureCheckContent() {
		Lecture lecture = new Lecture(0, "705.001", "LV with exam", "some text ...", "LV", 0, 0);
		this.dblLectures.addLecture(lecture);
		
		long unixTime = System.currentTimeMillis() / 1000L;
		Date d1 = new Date(0, unixTime, "i13", "", "date comment");
		String comment = "Ex1 comment";
		this.dblLectures.addExamToLecture(new Exam(0, "Ex1", comment, 0, d1), lecture);
		
		Exams exams = this.dblLectures.getExamsForLecture(lecture);
		exams.printExamList();
		Date d2 = exams.getExams().get(0).getDate();
		
		boolean eval = ((long)d1.getTimestamp() == (long)d2.getTimestamp());		
		assertTrue(eval);
		
		assertSame(comment, exams.getExams().get(0).getComment());
	}
	
	public void testAddLectureWithTaks() {
		Lecture lecture = new Lecture(0, "705.001", "LV with exam", "some text ...", "LV", 0, 0);
		this.dblLectures.addLecture(lecture);
		
		long tid = this.dblLectures.addTaskToLecture(new Task(0, "T1", "T1 comment", null), lecture);
		
		assertNotSame(-1L, tid);
	}
	
	public void testGetTasksForLecture() {
		Lecture lecture = new Lecture(0, "705.001", "LV with exam", "some text ...", "LV", 0, 0);
		this.dblLectures.addLecture(lecture);
		
		this.dblLectures.addTaskToLecture(new Task(0, "T1", "T1 comment", null), lecture);
		this.dblLectures.addTaskToLecture(new Task(0, "T2", "T2 comment", null), lecture);
		this.dblLectures.addTaskToLecture(new Task(0, "T3", "T3 comment", null), lecture);
		
		assertSame(3, this.dblLectures.getTasksForLecture(lecture).getTasks().size());
	}
	
	public void testAddLectureWithDate() {
		Lecture lecture = new Lecture(0, "705.001", "LV with exam", "some text ...", "LV", 0, 0);
		this.dblLectures.addLecture(lecture);
		
		long unixTime = System.currentTimeMillis() / 1000L;
		long did = this.dblLectures.addDateToLecture(new Date(0, unixTime, "i13", "none", "Date comment"), lecture);
		
		assertNotSame(-1L, did);
	}
	
	public void testGetDatesForLecture() {
		Lecture lecture = new Lecture(0, "705.001", "LV with exam", "some text ...", "LV", 0, 0);
		this.dblLectures.addLecture(lecture);
		
		long unixTime = System.currentTimeMillis() / 1000L;
		this.dblLectures.addDateToLecture(new Date(0, unixTime, "i13", "none", "Date comment"), lecture);
		this.dblLectures.addDateToLecture(new Date(0, unixTime + 3600, "i13", "none", "Date comment"), lecture);
		
		assertSame(2, this.dblLectures.getDatesForLecture(lecture).getDates().size());
	}
}
