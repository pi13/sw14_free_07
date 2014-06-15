package at.lvmaster3000.database.logic.test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.interfaces.IDBLTests;
import at.lvmaster3000.database.logic.DBLRelations;

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
//		long id = this.dblRelations.addRelation(relation)
	}

	@Override
	public void testDelete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testUpdate() {
		// TODO Auto-generated method stub
		
	}

}
