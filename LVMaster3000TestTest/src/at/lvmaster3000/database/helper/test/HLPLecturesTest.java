package at.lvmaster3000.database.helper.test;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.helper.HLPLectures;

/**
 * test class for functionality of lecture helper class
 * @author Doris Pedratscher
 *
 */
public class HLPLecturesTest extends AndroidTestCase{

	private HLPLectures hlpLectures;
	private SQLiteDatabase db;
	
	/**
	 * first create new HLPLectures and set database and open connection
	 */
	public void setUp(){
		RenamingDelegatingContext context 
        = new RenamingDelegatingContext(getContext(), "test_");
		hlpLectures = new HLPLectures(context);
		db = hlpLectures.openCon();
	}
	
	/**
	 * test is the insertion of a lecture returns an error case or if it works
	 */
	public void testLectureInsert(){
        hlpLectures.resetTable();
        long id = hlpLectures.addLecture("701.123", "Test LV", "Some Comment...", "LV", 1, 1);
        assertNotSame(-1, id);
	}
	
	/**
	 * test if we can read a lecture and its values from the database
	 */
	public void testLectureRead(){
		hlpLectures.resetTable();
        long id = hlpLectures.addLecture("701.123", "Test LV", "Some Comment...", "LV", 1, 1);
        
        Lecture queryResult = hlpLectures.getLecture(id);
        String lecNr = queryResult.getLectureNumber();
        
        assertNotSame(-1, id);
        assertEquals("701.123", lecNr);
	}
	
	/**
	 * at last tear town the database connection
	 */
	public void tearDown(){
		hlpLectures.closeCon();
	}
}
