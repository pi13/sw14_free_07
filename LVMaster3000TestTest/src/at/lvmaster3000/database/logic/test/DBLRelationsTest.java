package at.lvmaster3000.database.logic.test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.helper.HLPDates;
import at.lvmaster3000.database.interfaces.IDBLTests;
import at.lvmaster3000.database.logic.DBLRelations;
import at.lvmaster3000.database.objects.Date;

public class DBLRelationsTest extends AndroidTestCase implements IDBLTests {

	private DBLRelations dblRelations;	
	private RenamingDelegatingContext context = null;
	
	public static final String LOG_TAG_RELATIONS_LOGIC_TEST = "TEST_RELATIONS_LOGIC";
	
	public void setUp(){
		this.context = new RenamingDelegatingContext(getContext(), "test_");
		this.dblRelations = new DBLRelations(context);
		this.dblRelations.resetTable();
	}
	
	@Override
	public void testAddNew() {
		long id = this.dblRelations.addRelation("any", 1l, 1l, 1l, 1l, 1l);
		assertSame(1l, id);
	}

	@Override
	public void testDelete() {
		long id = this.dblRelations.addRelation("any", 1l, 1l, 1l, 1l, 1l);
		assertSame(1l, id);
		assertSame(1, this.dblRelations.deleteRelation(id));
	}

	@Override
	public void testUpdate() {
		//NO UPDATES here
		assertTrue(true);
	}
	
	public void testDeleteByDate() {
		Date date = new Date(1l, 0, "i13,", "", "comment");
		this.dblRelations.addRelation(HLPDates.TABLE_NAME, 0, 0, 0, 1l, 0);
		
		assertSame(1, this.dblRelations.deleteRelationByDate(date));
	}

}
