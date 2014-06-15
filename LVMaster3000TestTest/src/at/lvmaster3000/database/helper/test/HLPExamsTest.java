package at.lvmaster3000.database.helper.test;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.helper.HLPExams;
import at.lvmaster3000.database.interfaces.IHLPTest;

/**
 * test class for functionality of exams helper class
 *
 */
public class HLPExamsTest extends AndroidTestCase implements IHLPTest {
	private HLPExams hlpObjects;
	private SQLiteDatabase db;

	/**
	 * first create new HLPLectures and open database connection
	 */
	public void setUp() {
		RenamingDelegatingContext context = new RenamingDelegatingContext(
				getContext(), "test_");
		hlpObjects = new HLPExams(context);
		db = hlpObjects.openCon();
	}

	/**
	 * test is the insertion of an object returns an error case or if it works
	 */
	public void testInsert(){
        hlpObjects.resetTable();
        long id = hlpObjects.addExam("Ass1", "first assignment", 1);
        assertNotSame(-1, id);
	}
	
	/**
	 * test if deletion of object works
	 */
	public void testDelete(){
		hlpObjects.resetTable();
        long id = hlpObjects.addExam("Ass1", "first assignment", 1);
		hlpObjects.deleteExam(id);
		
		try{
			// should throw exception because lecture has been deleted
			Cursor cursor = db.rawQuery("SELECT * FROM " + HLPExams.TABLE_NAME + " WHERE _id = " + id, null);
			assertEquals(0, cursor.getCount());
		}catch(Exception e){
			assertNull(e);
		}
	}
	
	/**
	 * at last tear town the database connection
	 */
	public void tearDown() {
		db.rawQuery("DELETE FROM " + HLPExams.TABLE_NAME, null);
		hlpObjects.closeCon();
	}
}
