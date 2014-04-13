package at.lvmaster3000.database.helper.test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.helper.HLPTasks;

public class HLPTasksTest extends AndroidTestCase{
	private HLPTasks hlpTasks;

	/**
	 * first create new HLPLectures and open database connection
	 */
	public void setUp() {
		RenamingDelegatingContext context = new RenamingDelegatingContext(
				getContext(), "test_");
		hlpTasks = new HLPTasks(context);
		hlpTasks.openCon();
	}

	/**
	 * at last tear town the database connection
	 */
	public void tearDown() {
		hlpTasks.closeCon();
	}
}
