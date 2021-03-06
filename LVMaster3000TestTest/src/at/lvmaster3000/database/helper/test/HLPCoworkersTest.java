package at.lvmaster3000.database.helper.test;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.helper.HLPCoworkers;
import at.lvmaster3000.database.interfaces.IHLPTest;

/**
 * test class for functionality of coworkers helper class
 *
 */
public class HLPCoworkersTest extends AndroidTestCase implements IHLPTest {

	private HLPCoworkers hlpCoworkers;
	private SQLiteDatabase db;
	
	
	/**
	 * first create new HLPLectures and open database connection
	 */
	public void setUp(){
		RenamingDelegatingContext context 
        = new RenamingDelegatingContext(getContext(), "test_");
		hlpCoworkers = new HLPCoworkers(context);
		db = hlpCoworkers.openCon();
	}
	
	/**
	 * test is the insertion of an object returns an error case or if it works
	 */
	public void testInsert(){
        hlpCoworkers.resetTable();
        long id = hlpCoworkers.addCoworker("contact bruno baer", "leader");
        assertNotSame(-1, id);
	}
	
	/**
	 * test if deletion of an object works
	 */
	public void testDelete(){
		hlpCoworkers.resetTable();
        long id = hlpCoworkers.addCoworker("contact bruno baer", "leader");
		hlpCoworkers.deleteCoworker(id);
		
		try{
			// should throw exception because lecture has been deleted
			Cursor cursor = db.rawQuery("SELECT * FROM " + HLPCoworkers.TABLE_NAME + " WHERE _id = " + id, null);
			assertEquals(0, cursor.getCount());
		}catch(Exception e){
			assertNull(e);
		}
	}
	
	/**
	 * at last tear town the database connection
	 */
	public void tearDown(){
		db.rawQuery("DELETE FROM " + HLPCoworkers.TABLE_NAME, null);
		hlpCoworkers.closeCon();
	}
}
