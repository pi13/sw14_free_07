package at.lvmaster3000.database.helper.test;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.helper.HLPRelations;
import at.lvmaster3000.database.interfaces.IHLPTest;

public class HLPRelationsTest extends AndroidTestCase implements IHLPTest {

	private HLPRelations hlpRelations;
	private SQLiteDatabase db;
	
	public void setUp() {
		RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
		this.hlpRelations = new HLPRelations(context);
		this.db = this.hlpRelations.openCon();
	}
	
	@Override
	public void testInsert() {
		hlpRelations.resetTable();
        long id = hlpRelations.addRelation("any", 1l, 1l, 1l, 1l, 1l);
        assertSame(1l, id);
	}

	@Override
	public void testDelete() {
		hlpRelations.resetTable();
        long id = hlpRelations.addRelation("any", 1l, 1l, 1l, 1l, 1l);
        hlpRelations.deleteRelation(id);
		
		try{
			Cursor cursor = db.rawQuery("SELECT * FROM " + HLPRelations.TABLE_NAME + " WHERE _id = " + id, null);
			assertEquals(0, cursor.getCount());
		}catch(Exception e){
			assertNull(e);
		}
	}
	
	public void tearDown() {
		db.rawQuery("DELETE FROM " + HLPRelations.TABLE_NAME, null);
		hlpRelations.closeCon();
	}

}
