package at.lvmaster3000.database.helper.test;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.helper.HLPLectures;

public class HLPLecturesTest extends AndroidTestCase{

	private HLPLectures hlpLectures;
	private SQLiteDatabase db;
	
	public void setUp(){
		RenamingDelegatingContext context 
        = new RenamingDelegatingContext(getContext(), "test_");
		hlpLectures = new HLPLectures(context);
		db = hlpLectures.openCon();
	}
	
	public void testLectureInsert(){
        hlpLectures.resetTable();
        long id = hlpLectures.addLecture("701.123", "Test LV", "Some Comment...", "LV", 1, 1);
        assertNotSame(-1, id);
	}
	
	public void testLectureRead(){
		hlpLectures.resetTable();
        long id = hlpLectures.addLecture("701.123", "Test LV", "Some Comment...", "LV", 1, 1);
        
        Lecture queryResult = hlpLectures.getLecture(id);
        String lecNr = queryResult.getLectureNumber();
        
        assertNotSame(-1, id);
        assertEquals("701.123", lecNr);
	}
	
	public void tearDown(){
		hlpLectures.closeCon();
	}
}
