package at.lvmaster3000.database.helper.test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.helper.HLPCoworkers;
import at.lvmaster3000.database.helper.HLPLectures;

/**
 * test class for functionality of coworkers helper class
 * @author Doris Pedratscher
 *
 */
public class HLPCoworkersTest extends AndroidTestCase {

	private HLPCoworkers hlpCoworkers;
	
	
	/**
	 * first create new HLPLectures and open database connection
	 */
	public void setUp(){
		RenamingDelegatingContext context 
        = new RenamingDelegatingContext(getContext(), "test_");
		hlpCoworkers = new HLPCoworkers(context);
		hlpCoworkers.openCon();
	}
	
	/**
	 * at last tear town the database connection
	 */
	public void tearDown(){
		hlpCoworkers.closeCon();
	}
}
