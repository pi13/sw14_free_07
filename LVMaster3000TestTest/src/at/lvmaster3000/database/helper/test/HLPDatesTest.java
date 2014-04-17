package at.lvmaster3000.database.helper.test;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.helper.HLPDates;

/**
 * test class for functionality of dates helper class
 * @author Doris Pedratscher
 *
 */
public class HLPDatesTest extends AndroidTestCase{

	private HLPDates hlpObjects;
	private SQLiteDatabase db;

	/**
	 * first create new HLPLectures and open database connection
	 */
	public void setUp() {
		RenamingDelegatingContext context = new RenamingDelegatingContext(
				getContext(), "test_");
		hlpObjects = new HLPDates(context);
		db = hlpObjects.openCon();
	}

	/**
	 * test is the insertion of an object returns an error case or if it works
	 */
	public void testInsert(){
        hlpObjects.resetTable();
        long id = hlpObjects.addDate(1000l, "30 grad west", "weekly appointment", "team meeting");
        assertNotSame(-1, id);
	}
	
	/**
	 * test if deletion of an object works
	 */
	public void testDelete(){
		hlpObjects.resetTable();
        long id = hlpObjects.addDate(1000l, "30 grad west", "weekly appointment", "team meeting");
		hlpObjects.deleteDate(id);
		
		try{
			// should throw exception because lecture has been deleted
			Cursor cursor = db.rawQuery("SELECT * FROM " + hlpObjects.TABLE_NAME + " WHERE _id = " + id, null);
			assertEquals(0, cursor.getCount());
		}catch(Exception e){
			assertNull(e);
		}
	}
	
	/**
	 * at last tear town the database connection
	 */
	public void tearDown() {
		db.rawQuery("DELETE FROM " + hlpObjects.TABLE_NAME, null);
		hlpObjects.closeCon();
	}

}
