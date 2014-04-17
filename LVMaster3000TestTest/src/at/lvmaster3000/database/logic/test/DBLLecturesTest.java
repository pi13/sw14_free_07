package at.lvmaster3000.database.logic.test;

import java.util.List;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.lists.Lectures;
import at.lvmaster3000.database.logic.DBLLectures;
import at.lvmaster3000.database.objects.Lecture;

public class DBLLecturesTest extends AndroidTestCase{
	private DBLLectures dblLectures;
	private Lecture lecture1;
	private List<Lecture> testLectures = null;
	private int NR_TEST_LECTURES = 0;
	
	public void startUp(){
		RenamingDelegatingContext context 
        = new RenamingDelegatingContext(getContext(), "test_");
		dblLectures = new DBLLectures();
		createTestLectures();
	}
	
	public void testAddNewLecture(){
		dropAllLectures();
		
		long idFromDatabase = dblLectures.addLecture(this.testLectures.get(0));
		
		assertNotSame(-1l, idFromDatabase);
	}
	
	public void testGetAllLectures(){
		dropAllLectures();
		fillTestLecturesInDBL();
		Lectures lecs = dblLectures.getAllLectures();
		
		assertEquals(NR_TEST_LECTURES, lecs.nrOfLectures());
	}
	
	private void fillTestLecturesInDBL(){
		for(Lecture lec : this.testLectures){
			this.dblLectures.addLecture(lec);
		}
	}
	
	private void createTestLectures(){
		Lecture l1 = new Lecture(-1l, "301.000", "sophisticated 1", "good prof","SEM", 1, 1);
		Lecture l2 = new Lecture(-2l, "301.001", "sophisticated 1", "good prof","VO", 1, 1);
	
		this.testLectures.add(l1);
		this.testLectures.add(l2);
		
		this.NR_TEST_LECTURES = this.testLectures.size();
	}
	
	/**
	 * to be overwritten, if testdatabase is there
	 */
	private void dropAllLectures(){
		this.testLectures.clear();
	}
}
