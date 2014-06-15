package at.lvmaster3000.database.logic.test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.demodata.DDTestsetA;
import at.lvmaster3000.database.interfaces.IDBLTests;
import at.lvmaster3000.database.lists.Exams;
import at.lvmaster3000.database.logic.DBLExams;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Exam;

public class DBLExamsTest extends AndroidTestCase implements IDBLTests {
	
	private DBLExams dblExams;
	
	private RenamingDelegatingContext context = null;
	
	public static final String LOG_TAG_EXAMS_LOGIC_TEST = "TEST_EXAMS_LOGIC";
	
	public void setUp(){
		this.context = new RenamingDelegatingContext(getContext(), "test_");		
		this.dblExams = new DBLExams(context);
		this.dblExams.resetTablesInvolved();
	}
	
	@Override
	public void testAddNew() {
		long id = this.dblExams.addExam("Ex 1", "Comment...", 0);
		assertNotSame(-1l, id);
	}

	@Override
	public void testDelete() {
		long id = this.dblExams.addExam("Ex 1", "Comment...", 0);
		assertNotSame(-1l, id);		
		assertNotSame(-1, this.dblExams.deleteExam(id));
	}

	@Override
	public void testUpdate() {
		Exam ex = new Exam(0, "Ex 1", "Comment...", 0, null);
		assertEquals(1, this.dblExams.addExam(ex));
		
		ex.setTitle("EX 1.0");
		assertEquals(1, this.dblExams.updateExam(ex));
	}
	
	public void testGetAllExams(){
		DDTestsetA TestA = new DDTestsetA(context);
	    TestA.FillDb();

		Exams exs = dblExams.getExams(0);		
		assertEquals(TestA.getExamCnt(), exs.nrOfExams());		
	}
	
	public void testGetExamById(){
		long id = this.dblExams.addExam("Ex 1", "Comment...", 0);		
		Exam exam = dblExams.getExamById(id);
		
		assertEquals("Ex 1", exam.getTitle());		
	}
	
	public void testAddExamWithDate() {
		long unixTime = System.currentTimeMillis() / 1000L;
		Date date = new Date(0, unixTime, "i13", "", "Comment");
		Exam exam = new Exam(0, "Ex Title", "Comment", 0, date);
		
		this.dblExams.addExam(exam);
		
		boolean eval = ((long)date.getTimestamp() == (long)this.dblExams.getExamDate(exam).getTimestamp());
		
		assertTrue(eval);
	}
	
	public void testGetDateOfExam(){
		DDTestsetA TestA = new DDTestsetA(context);
	    TestA.FillDb();
	    
	    Exam exam = dblExams.getExamById(1);	    
	    exam.printExam();
	    
	    long unixTime = System.currentTimeMillis() / 1000L;
	    Date date = new Date(0, unixTime, "i13", "exam", "what ever ...");
	    
	    dblExams.setExamDate(exam, date);
	    
	    Date dateRet = dblExams.getExamDate(exam);
	    dateRet.printDate();
	    
		assertEquals(date.getTimestamp(), dateRet.getTimestamp());
	}	
	
	public void testGetResourcesOfExam(){
		long id = this.dblExams.addExam("Ex 1", "Comment...", 0);
//		this.dblExams
		//TODO: implement core function
	}
	
}
