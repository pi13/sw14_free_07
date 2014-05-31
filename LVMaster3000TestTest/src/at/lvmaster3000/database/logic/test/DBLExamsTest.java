package at.lvmaster3000.database.logic.test;

import java.util.ArrayList;
import java.util.List;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.demodata.DDTestsetA;
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
	private DBLExams dblObjects;
	private List<Exam> testObjects = null;
	private int NR_TEST_EXAMS = 0;
	private int limit = 10000;
	//private DDTestsetA TestA = null;
	
	private RenamingDelegatingContext testContext = null;
	
	public void setUp(){
		testContext = new RenamingDelegatingContext(getContext(), "test_");
		
		dblObjects = new DBLExams(testContext);
		createTestObjects();
	}
	
	public void testTestSet(){
		DDTestsetA TestA = new DDTestsetA(testContext);
	    TestA.FillDb();
	}
	
	public void testAddNewExamAndDeleteIt(){
		DDTestsetA TestA = new DDTestsetA(testContext);
	    TestA.FillDb();
		
		dropAllObjects();
		createTestObjects();
		
		long idFromDatabase = dblObjects.addExam(this.testObjects.get(0));
		
		assertNotSame(-1l, idFromDatabase);
		assertEquals(1,dblObjects.deleteExam(idFromDatabase));
		
		Exam fromTest = this.testObjects.get(1);
		idFromDatabase = dblObjects.addExam(fromTest.getTitle(), fromTest.getComment(), fromTest.getLecture_id());
		
		assertNotSame(-1l, idFromDatabase);
		assertEquals(1,dblObjects.deleteExam(idFromDatabase));	
		
	}
	
	public void testGetAllExams(){
		DDTestsetA TestA = new DDTestsetA(testContext);
	    TestA.FillDb();

		Exams exs = dblObjects.getExams(limit);
		
		assertEquals(TestA.getExamCnt(), exs.nrOfExams());		
	}
	
	public void testDeleteExam(){
		DDTestsetA TestA = new DDTestsetA(testContext);
	    TestA.FillDb();
	    
		dropAllObjects();
		createTestObjects();
		
		long id = dblObjects.addExam(testObjects.get(0));
		
		int size = dblObjects.getExams(limit).nrOfExams();
		
		int res = dblObjects.deleteExam(id);
		assertEquals(1, res);
		
		assertEquals(size-1, dblObjects.getExams(limit).nrOfExams());
	}
	
	public void testGetExamById(){
		DDTestsetA TestA = new DDTestsetA(testContext);
	    TestA.FillDb();
	    
		dropAllObjects();
		createTestObjects();
		
		Exam testExam = testObjects.get(0);
		long id = dblObjects.addExam(testExam);
		
		Exam exam = dblObjects.getExamById(id);
		
		assertEquals(testExam.getTitle(), exam.getTitle());
		
	}
	
	public void testEditExam(){
		DDTestsetA TestA = new DDTestsetA(testContext);
	    TestA.FillDb();
	    
		String editComment = "edited comment";
		
		long id = dblObjects.getExams(limit).getExam().get(0).getId();
		
		Exam testExam = dblObjects.getExamById(id);
		
		testExam.setComment(editComment);
		
		dblObjects.editExam(testExam);
		
		Exam editedExam = dblObjects.getExamById(id);
		String comment = editedExam.getComment();
		
		assertEquals(editComment, comment);
	}
	
	/**
	 * anscheinend wird onCreate für 'relations' nicht ausgeführt, schlägt deshalb fehl
	 */
	public void testGetDateOfExam(){
		DDTestsetA TestA = new DDTestsetA(testContext);
	    TestA.FillDb();
	    
		dropAllObjects();
		createTestObjects();
		// TODO useful test case
		long id = 0;
		Date date = new Date(new java.util.Date(2014-1900, 8,1,10,0,0).getTime(), "i 13", "exam", "i need it");
		boolean worked = dblObjects.setNewExamDate(id, date);
		
		assertEquals(true, worked);
		
		Date fromDBL = dblObjects.getDateOfExam(id);
		
		assertEquals(fromDBL.getTimestamp(), date.getTimestamp());
	}
	
	public void testGetAllResourcesOfExam(){
		DDTestsetA TestA = new DDTestsetA(testContext);
	    TestA.FillDb();
	    
	    long lecId = new DBLLectures(testContext).getLectures(limit).getLectures().get(0).getID();
		long exId = dblObjects.addExam("resource test", "want to add resource here", lecId);

		long resId = new DBLResources(testContext).addResource(new Resource("test resource"));
		
		assertNotSame(-1l, resId);
		
		HLPRelations hlpRelations = new HLPRelations(testContext);
		hlpRelations.openCon();
		hlpRelations.addRelation(HLPResources.TABLE_NAME, 0, exId, 0, 0, resId);
		hlpRelations.closeCon();
		
		Resources resources = dblObjects.getAllResourcesOfExam(exId);
		
		assertNotNull(resources);
		assertEquals(1, resources.nrOfResources());
	}
	
	public void testGetCoworkersOfExam(){
		DDTestsetA TestA = new DDTestsetA(testContext);
	    TestA.FillDb();
	    
		dropAllObjects();
		createTestObjects();
		// TODO useful test case
		long id = 0;
		Coworkers coworkers = dblObjects.getAllCoworkersOfExam(id);
		
		assertNotNull(coworkers);
	}
	
	private void fillTestExamsInDBL(){
		if(this.testObjects == null){
			createTestObjects();
		}
		
		for(Exam ex : this.testObjects){
			this.dblObjects.addExam(ex);
		}
	}
	
	private void createTestObjects(){
		Exam e1 = new Exam("ex1","comm1",1);
		Exam e2 = new Exam("ex2","comm2",2);
	
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
