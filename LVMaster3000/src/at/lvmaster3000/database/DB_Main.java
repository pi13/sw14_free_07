package at.lvmaster3000.database;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import at.lvmaster3000.database.helper.HLPTasks;
import at.lvmaster3000.database.helper.HLPLectures;
import at.lvmaster3000.settings.CMONsettings;

public class DB_Main extends Activity {
	
	/* Helper classes */
	private HLPLectures hlpLectures;
	private HLPTasks hlptasks;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.i(CMONsettings.LOG_TAG, "APP startup..."); 
        
        hlpLectures = new HLPLectures(this);
        hlpLectures.openCon();
        hlpLectures.resetTable();
          
        long id = hlpLectures.addLecture("701.123", "Test LV", "Some Comment...", "LV", 1, 1);
        hlpLectures.allEntriesToLog();
        hlpLectures.closeCon();
        
        hlptasks = new HLPTasks(this);
        hlptasks.openCon();
        hlptasks.resetTable();
          
        hlptasks.addTask("Test TASK", "Some Comment...", id);
        hlptasks.allEntriesToLog();
        hlptasks.closeCon();
        
        Log.i(CMONsettings.LOG_TAG, "APP done. your brain will be toasted in a few seconds :P"); 
    }
	
}