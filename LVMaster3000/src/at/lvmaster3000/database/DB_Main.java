package at.lvmaster3000.database;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import at.lvmaster3000.database.demodata.DDTestsetA;
import at.lvmaster3000.database.helper.HLPRelations;
import at.lvmaster3000.database.logic.DBLDates;
import at.lvmaster3000.database.logic.DBLExams;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Exam;
import at.lvmaster3000.settings.CMONsettings;

public class DB_Main extends Activity {
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.i(CMONsettings.LOG_TAG, "APP startup..."); 
        
        DDTestsetA TestA = new DDTestsetA(this);
        TestA.FillDb();
//        TestA.someTest();
        
        long unixTime = System.currentTimeMillis() / 1000L;
        
        Date dObj = new Date();
        dObj.setTimestamp(unixTime);
        dObj.setLocation("Home");
        dObj.setType("XY");
        dObj.setComment("Comment ... -.-");    
        
        Exam eObj = new Exam();
        eObj.setLecture_id(1);
        eObj.setTitle("Ex Title");
        eObj.setComment("Ex commnet");        
        
        DBLExams e = new DBLExams(this);
        eObj.setId(e.addExam(eObj));
        
        e.setNewExamDate(eObj.getId(), dObj);
        
        HLPRelations r = new HLPRelations(this);
        r.openCon();
        r.allEntriesToLog();
        r.closeCon();
        
        Log.i(CMONsettings.LOG_TAG, "APP done. your brain will be toasted in a few seconds :P"); 
    }
	
}