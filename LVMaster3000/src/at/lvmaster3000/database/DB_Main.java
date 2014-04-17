package at.lvmaster3000.database;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import at.lvmaster3000.database.demodata.DDTestsetA;
import at.lvmaster3000.database.helper.HLPCoworkers;
import at.lvmaster3000.database.helper.HLPDates;
import at.lvmaster3000.database.helper.HLPExams;
import at.lvmaster3000.database.helper.HLPLectures;
import at.lvmaster3000.database.helper.HLPRelations;
import at.lvmaster3000.database.helper.HLPResources;
import at.lvmaster3000.database.helper.HLPTasks;
import at.lvmaster3000.settings.CMONsettings;

public class DB_Main extends Activity {
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.i(CMONsettings.LOG_TAG, "APP startup..."); 
        
        DDTestsetA TestA = new DDTestsetA(this);
        TestA.FillDb();
        TestA.someTest();
        
        Log.i(CMONsettings.LOG_TAG, "APP done. your brain will be toasted in a few seconds :P"); 
    }
	
}