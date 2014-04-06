package at.lvmaster3000.database;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import at.lvmaster3000.database.helper.HLPlectures;
import at.lvmaster3000.settings.CMONsettings;

public class DB_Main extends Activity {
	
	/* Helper classes */
	private HLPlectures hlpLectures;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.i(CMONsettings.LOG_TAG, "APP startup..."); 
        
        hlpLectures = new HLPlectures(this);
        hlpLectures.resetTable();
        hlpLectures.getCreationString();
        
        hlpLectures.openCon();
        hlpLectures.addLecture("701.123", "Test LV", "Some Comment...", "LV", 1, 1);
        hlpLectures.closeCon();
        
        Log.i(CMONsettings.LOG_TAG, "APP done. your brain will be toasted in a few seconds :P"); 
    }
	
}