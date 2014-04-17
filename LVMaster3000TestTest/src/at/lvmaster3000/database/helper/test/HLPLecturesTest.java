package at.lvmaster3000.database.helper.test;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;
import at.lvmaster3000.database.helper.HLPLectures;
import at.lvmaster3000.database.objects.Lecture;
import at.lvmaster3000.settings.DBsettings;

/**
 * test class for functionality of lecture helper class
 * @author Doris Pedratscher
 *
 */
public class HLPLecturesTest extends AndroidTestCase{

	private String logtag = DBsettings.LOG_TAG_LECTURES_TEST;
	private HLPLectures hlpObjects;
	private SQLiteDatabase db;
	
	/**
	 * first create new HLPLectures and set database and open connection
	 */
	public void setUp(){
		RenamingDelegatingContext context 
        = new RenamingDelegatingContext(getContext(), "test_");
		hlpObjects = new HLPLectures(context);
		db = hlpObjects.openCon();
	}
	
	/**
	 * test is the insertion of a lecture returns an error case or if it works
	 */
	public void testLectureInsert(){
		hlpObjects.resetTable();
        long id = hlpObjects.addLecture("701.123", "Test LV", "Some Comment...", "LV", 1, 1);
        assertNotSame(-1, id);
	}
	
	/**
	 * test if deletion of lecture works
	 */
	public void testLectureDelete(){
		hlpObjects.resetTable();
        long id = hlpObjects.addLecture("701.123", "Test LV", "Some Comment...", "LV", 1, 1);
        hlpObjects.deleteLecture(id);
		
		try{
			// should throw exception because lecture has been deleted
			Cursor cursor = db.rawQuery("SELECT * FROM " + hlpObjects.TABLE_NAME + " WHERE _id = " + id, null);
			assertEquals(0, cursor.getCount());
		}catch(Exception e){
			assertNull(e);
		}
	}
	
	/**
	 * at last clear database and tear town the database connection
	 */
	public void tearDown(){
		db.rawQuery("DELETE FROM " + hlpObjects.TABLE_NAME, null);
		hlpObjects.closeCon();
	}
}
