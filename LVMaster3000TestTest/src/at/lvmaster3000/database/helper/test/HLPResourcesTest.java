package at.lvmaster3000.database.helper.test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.helper.HLPResources;

/**
 * test class for functionality of resources helper class
 * @author Doris Pedratscher
 *
 */
public class HLPResourcesTest extends AndroidTestCase{
	private HLPResources hlpResources;

	/**
	 * first create new HLPLectures and open database connection
	 */
	public void setUp() {
		RenamingDelegatingContext context = new RenamingDelegatingContext(
				getContext(), "test_");
		hlpResources = new HLPResources(context);
		hlpResources.openCon();
	}

	/**
	 * at last tear town the database connection
	 */
	public void tearDown() {
		hlpResources.closeCon();
	}
}
