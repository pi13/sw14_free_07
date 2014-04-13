package at.lvmaster3000.database;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import at.lvmaster3000.database.helper.HLPCoworkers;
import at.lvmaster3000.database.helper.HLPDates;
import at.lvmaster3000.database.helper.HLPExams;
import at.lvmaster3000.database.helper.HLPResources;
import at.lvmaster3000.database.helper.HLPTasks;
import at.lvmaster3000.database.helper.HLPLectures;
import at.lvmaster3000.settings.CMONsettings;

public class DB_Main extends Activity {
	
	/* Helper classes */
	private HLPLectures hlpLectures;
	private HLPTasks hlptasks;
	private HLPResources hlpresources;
	private HLPExams hlpexams;
	private HLPCoworkers hlpcoworkers;
	private HLPDates hlpdates;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.i(CMONsettings.LOG_TAG, "APP startup..."); 
        
        //Lectures test
        hlpLectures = new HLPLectures(this);
        hlpLectures.openCon();
        hlpLectures.resetTable();
          
        long id = hlpLectures.addLecture("701.123", "Test LV", "Some Comment...", "LV", 1, 1);
        hlpLectures.allEntriesToLog();
        hlpLectures.closeCon();
        
        //Tasks test
        hlptasks = new HLPTasks(this);
        hlptasks.openCon();
        hlptasks.resetTable();
          
        hlptasks.addTask("Test TASK", "Some Comment...", id);
        hlptasks.allEntriesToLog();
        hlptasks.closeCon();
        
        //Resources test
        hlpresources = new HLPResources(this);
        hlpresources.openCon();
        hlpresources.resetTable();
          
        hlpresources.addResource("R1");
        hlpresources.allEntriesToLog();
        hlpresources.closeCon();
        
        //Exams test
        hlpexams = new HLPExams(this);
        hlpexams.openCon();
        hlpexams.resetTable();
          
        hlpexams.addExam("exam1", "exam1 bla bla", id);
        hlpexams.addExam("exam2", "exam2 bla bla", 0);
        hlpexams.allEntriesToLog();
        hlpexams.closeCon();
        
      //Coworkers test
        hlpcoworkers = new HLPCoworkers(this);
        hlpcoworkers.openCon();
        hlpcoworkers.resetTable();
        
        hlpcoworkers.addCoworker("xzy", "Boss");
        hlpcoworkers.allEntriesToLog();
        hlpcoworkers.closeCon();
        
        //Dates test
        hlpdates = new HLPDates(this);
        hlpdates.openCon();
        hlpdates.resetTable();
        
        hlpdates.addDate(1234567890, "Testlocation", "LV", "Thats a comment");
        hlpdates.allEntriesToLog();
        hlpdates.closeCon();
        
        
        Log.i(CMONsettings.LOG_TAG, "APP done. your brain will be toasted in a few seconds :P"); 
    }
	
}