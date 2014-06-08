package at.lvmaster3000.database.logic.test;

import java.util.ArrayList;
import java.util.List;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.demodata.DDTestsetA;
import at.lvmaster3000.database.helper.HLPExams;
import at.lvmaster3000.database.helper.HLPLectures;
import at.lvmaster3000.database.helper.HLPRelations;
import at.lvmaster3000.database.helper.HLPResources;
import at.lvmaster3000.database.lists.Coworkers;
import at.lvmaster3000.database.lists.Exams;
import at.lvmaster3000.database.lists.Resources;
import at.lvmaster3000.database.logic.DBLExams;
import at.lvmaster3000.database.logic.DBLLectures;
import at.lvmaster3000.database.logic.DBLResources;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Exam;
import at.lvmaster3000.database.objects.Resource;

public class DBLExamsTest extends AndroidTestCase{
	private DBLExams dblExams;
	private List<Exam> testObjects = null;
	private int NR_TEST_EXAMS = 0;
	private int limit = 10000;
	//private DDTestsetA TestA = null;
	
	private RenamingDelegatingContext context = null;
	
	public static final String LOG_TAG_EXAMS_LOGIC_TEST = "TEST_EXAMS_LOGIC";
	
	public void setUp(){
		context = new RenamingDelegatingContext(getContext(), "test_");		
		dblExams = new DBLExams(context);
		createTestObjects();
	}
	
	public void testTestSet(){
		DDTestsetA TestA = new DDTestsetA(context);
	    TestA.FillDb();
	}
	
	public void testAddNewExamAndDeleteIt(){
		DDTestsetA TestA = new DDTestsetA(context);
	    TestA.FillDb();
		
		dropAllObjects();
		createTestObjects();
		
		long idFromDatabase = dblExams.addExam(this.testObjects.get(0));
		
		assertNotSame(-1l, idFromDatabase);
		assertEquals(1,dblExams.deleteExam(idFromDatabase));
		
		Exam fromTest = this.testObjects.get(1);
		idFromDatabase = dblExams.addExam(fromTest.getTitle(), fromTest.getComment(), fromTest.getLecture_id());
		
		assertNotSame(-1l, idFromDatabase);
		assertEquals(1,dblExams.deleteExam(idFromDatabase));	
		
	}
	
	public void testGetAllExams(){
		DDTestsetA TestA = new DDTestsetA(context);
	    TestA.FillDb();

		Exams exs = dblExams.getExams(limit);
		
		assertEquals(TestA.getExamCnt(), exs.nrOfExams());		
	}
	
	public void testDeleteExam(){
		DDTestsetA TestA = new DDTestsetA(context);
	    TestA.FillDb();
	    
		dropAllObjects();
		createTestObjects();
		
		long id = dblExams.addExam(testObjects.get(0));
		
		int size = dblExams.getExams(limit).nrOfExams();
		
		int res = dblExams.deleteExam(id);
		assertEquals(1, res);
		
		assertEquals(size-1, dblExams.getExams(limit).nrOfExams());
	}
	
	public void testGetExamById(){
		DDTestsetA TestA = new DDTestsetA(context);
	    TestA.FillDb();
	    
		dropAllObjects();
		createTestObjects();
		
		Exam testExam = testObjects.get(0);
		long id = dblExams.addExam(testExam);
		
		Exam exam = dblExams.getExamById(id);
		
		assertEquals(testExam.getTitle(), exam.getTitle());
		
	}
	
	public void testEditExam(){
		DDTestsetA TestA = new DDTestsetA(context);
	    TestA.FillDb();
	    
		String editComment = "edited comment";
		
		long id = dblExams.getExams(limit).getExams().get(0).getId();
		
		Exam testExam = dblExams.getExamById(id);
		
		testExam.setComment(editComment);
		
		dblExams.updateExam(testExam);
		
		Exam editedExam = dblExams.getExamById(id);
		String comment = editedExam.getComment();
		
		assertEquals(editComment, comment);
	}
	
	/**
	 * 
	 */
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
	
	public void testGetAllResourcesOfExam(){
		DDTestsetA TestA = new DDTestsetA(context);
	    TestA.FillDb();
	    
	    long lecId = new DBLLectures(context).getLectures(0).getLectures().get(0).getID();
		long exId = dblExams.addExam("resource test", "want to add resource here", lecId);

		long resId = new DBLResources(context).addResource(new Resource("test resource"));
		
		assertNotSame(-1l, resId);
		
		HLPRelations hlpRelations = new HLPRelations(context);
		hlpRelations.openCon();
		hlpRelations.addRelation(HLPExams.TABLE_NAME, 0, exId, 0, 0, resId);
		hlpRelations.closeCon();
		
		Resources resources = dblExams.getExamResources(new Exam(exId, "", "", 0));
		
		assertNotNull(resources);
		assertEquals(1, resources.getResources().size());
	}
	
	public void testGetCoworkersOfExam(){
		//TODO: feature migth not be added
		DDTestsetA TestA = new DDTestsetA(context);
	    TestA.FillDb();
	    
		dropAllObjects();
		createTestObjects();
		
		long id = 0;
		Coworkers coworkers = null; //dblExams.getCoworkers(id);
		
//		assertNotNull(coworkers);
	}
	
	private void fillTestExamsInDBL(){
		if(this.testObjects == null){
			createTestObjects();
		}
		
		for(Exam ex : this.testObjects){
			this.dblExams.addExam(ex);
		}
	}
	
	private void createTestObjects(){
		Exam e1 = new Exam(0, "ex1","comm1",1);
		Exam e2 = new Exam(0, "ex2","comm2",2);
	
		this.testObjects = new ArrayList<Exam>();
		
		this.testObjects.add(e1);
		this.testObjects.add(e2);
		
		this.NR_TEST_EXAMS = this.testObjects.size();
	}
	
	private void dropAllObjects(){
		this.dblExams = null;
		this.dblExams = new DBLExams(context);
	}
}
