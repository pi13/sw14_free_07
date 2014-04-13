package at.lvmaster3000.database.helper.test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.helper.HLPDates;

/**
 * test class for functionality of dates helper class
 * @author Doris Pedratscher
 *
 */
public class HLPDatesTest extends AndroidTestCase{

	private HLPDates hlpDates;

	/**
	 * first create new HLPLectures and open database connection
	 */
	public void setUp() {
		RenamingDelegatingContext context = new RenamingDelegatingContext(
				getContext(), "test_");
		hlpDates = new HLPDates(context);
		hlpDates.openCon();
	}

	/**
	 * at last tear town the database connection
	 */
	public void tearDown() {
		hlpDates.closeCon();
	}

}
