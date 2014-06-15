package at.lvmaster3000.database.helper.test;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.helper.HLPResources;
import at.lvmaster3000.database.interfaces.IHLPTest;

/**
 * test class for functionality of resources helper class
 *
 */
public class HLPResourcesTest extends AndroidTestCase implements IHLPTest {
	private HLPResources hlpResources;
	private SQLiteDatabase db;

	/**
	 * first create new HLPLectures and open database connection
	 */
	public void setUp() {
		RenamingDelegatingContext context = new RenamingDelegatingContext(
				getContext(), "test_");
		hlpResources = new HLPResources(context);
		db = hlpResources.openCon();
	}

	/**
	 * test is the insertion of an object returns an error case or if it works
	 */
	public void testInsert(){
        hlpResources.resetTable();
        long id = hlpResources.addResource("test book");
        assertNotSame(-1, id);
	}
	
	/**
	 * test if deletion of an object works
	 */
	public void testDelete(){
		hlpResources.resetTable();
        long id = hlpResources.addResource("test book");
		hlpResources.deleteResource(id);
		
		try{
			// should throw exception because lecture has been deleted
			Cursor cursor = db.rawQuery("SELECT * FROM " + HLPResources.TABLE_NAME + " WHERE _id = " + id, null);
			assertEquals(0, cursor.getCount());
		}catch(Exception e){
			assertNull(e);
		}
	}
	
	/**
	 * at last tear town the database connection
	 */
	public void tearDown() {
		db.rawQuery("DELETE FROM " + HLPResources.TABLE_NAME, null);
		hlpResources.closeCon();
	}
}
