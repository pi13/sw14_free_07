package at.lvmaster3000.database.logic.test;

import java.util.ArrayList;
import java.util.List;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.helper.HLPRelations;
import at.lvmaster3000.database.lists.Coworkers;
import at.lvmaster3000.database.lists.Exams;
import at.lvmaster3000.database.lists.Resources;
import at.lvmaster3000.database.logic.DBLExams;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Exam;

public class DBLExamsTest extends AndroidTestCase{
	private DBLExams dblObjects;
	private List<Exam> testObjects = null;
	private int NR_TEST_EXAMS = 0;
	private int limit = 10000;
	
	private RenamingDelegatingContext testContext = null;
	
	public void setUp(){
		testContext = new RenamingDelegatingContext(getContext(), "test_");
		
		dblObjects = new DBLExams(testContext);
		createTestObjects();
	}
	
	public void testAddNewExam(){
		dropAllObjects();
		createTestObjects();
		
		long idFromDatabase = dblObjects.addExam(this.testObjects.get(0));
		
		assertNotSame(-1l, idFromDatabase);
		
		Exam fromTest = this.testObjects.get(1);
		idFromDatabase = dblObjects.addExam(fromTest.getTitle(), fromTest.getComment(), fromTest.getLecture_id());
		
		assertNotSame(-1l, idFromDatabase);
	}
	
	public void testGetAllExams(){
		dropAllObjects();
		fillTestExamsInDBL();
		Exams exs = dblObjects.getAllExams(limit);
		
		assertEquals(NR_TEST_EXAMS, exs.nrOfExams());		
	}
	
	public void testDeleteExam(){
		dropAllObjects();
		createTestObjects();
		
		long id = dblObjects.addExam(testObjects.get(0));
		
		int size = dblObjects.getAllExams(limit).nrOfExams();
		
		int res = dblObjects.deleteExam(id);
		assertEquals(1, res);
		
		assertEquals(size-1, dblObjects.getAllExams(limit).nrOfExams());
	}
	
	public void testGetExamById(){
		dropAllObjects();
		createTestObjects();
		
		Exam testExam = testObjects.get(0);
		long id = dblObjects.addExam(testExam);
		
		Exam exam = dblObjects.getExamById(id);
		
		assertEquals(testExam.getTitle(), exam.getTitle());
		
	}
	
	public void testEditExam(){
		dropAllObjects();
		createTestObjects();
		String editComment = "edited comment";
		
		long id = dblObjects.addExam(testObjects.get(0));
		testObjects.get(0).setComment(editComment);
		testObjects.get(0).setId(id);
		
		dblObjects.editExam(testObjects.get(0));
		
		Exam editedExam = dblObjects.getExamById(id);
		String comment = editedExam.getComment();
		
		assertEquals(editComment, comment);
	}
	
	/**
	 * anscheinend wird onCreate für 'relations' nicht ausgeführt, schlägt deshalb fehl
	 */
	public void testGetDateOfExam(){
		dropAllObjects();
		createTestObjects();
		// TODO useful test case
		long id = 0;
		Date date = new Date(new java.util.Date(2014-1900, 8,1,10,0,0).getTime(), "i 13", "exam", "i need it");
		boolean worked = dblObjects.setDateToExam(id, date);
		
		assertEquals(true, worked);
		
		Date fromDBL = dblObjects.getDateOfExam(id);
		
		assertEquals(fromDBL.getTimestamp(), date.getTimestamp());
	}
	
	public void testGetAllResourcesOfExam(){
		dropAllObjects();
		createTestObjects();
		// TODO useful test case
		long id = 0;
		Resources resources = dblObjects.getAllResourcesOfExam(id);
		
		assertNotNull(resources);
	}
	
	public void testGetCoworkersOfExam(){
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
