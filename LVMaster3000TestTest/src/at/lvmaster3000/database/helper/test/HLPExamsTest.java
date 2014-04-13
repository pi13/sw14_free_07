package at.lvmaster3000.database.helper.test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.helper.HLPExams;

/**
 * test class for functionality of exams helper class
 * @author Doris Pedratscher
 *
 */
public class HLPExamsTest extends AndroidTestCase{
	private HLPExams hlpExams;

	/**
	 * first create new HLPLectures and open database connection
	 */
	public void setUp() {
		RenamingDelegatingContext context = new RenamingDelegatingContext(
				getContext(), "test_");
		hlpExams = new HLPExams(context);
		hlpExams.openCon();
	}

	/**
	 * at last tear town the database connection
	 */
	public void tearDown() {
		hlpExams.closeCon();
	}
}
