package at.lvmaster3000.database.helper.test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.helper.HLPLectures;

/**
 * test class for functionality of lecture helper class
 * @author Doris Pedratscher
 *
 */
public class HLPLecturesTest extends AndroidTestCase{
	
	public static final String LOG_TAG_LECTURES_TEST = "TEST_LVM3K_SQL_LECTURES";

	private HLPLectures hlplectures;
	
	/**
	 * first create new HLPLectures and set database and open connection
	 */
	public void setUp(){
		RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
		this.hlplectures = new HLPLectures(context);
		this.hlplectures.openCon();
	}
	
	/**
	 * test is the insertion of a lecture returns an error case or if it works
	 */
	public void testLectureInsert(){
        long id = this.hlplectures.addLecture("701.123", "Test LV", "Some Comment...", "LV", 1, 1);
        assertNotSame(-1, id);
	}
	
	/**
	 * test if deletion of lecture works
	 */
	public void testLectureDelete(){
		
		long id = this.hlplectures.addLecture("701.123", "Test LV", "Some Comment...", "LV", 1, 1);
		assertNotSame(0, this.hlplectures.deleteLecture(id));
	}
	
	/**
	 * at last clear database and tear town the database connection
	 */
	public void tearDown(){
//		db.rawQuery("DELETE FROM " + HLPLectures.TABLE_NAME, null);
		this.hlplectures.closeCon();
	}
}
