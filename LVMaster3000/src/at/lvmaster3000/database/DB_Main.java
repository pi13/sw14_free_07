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
import at.lvmaster3000.database.objects.Lecture;
import at.lvmaster3000.settings.CMONsettings;

public class DB_Main extends Activity {
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.i(CMONsettings.LOG_TAG, "APP startup..."); 
        
        IDBlogic dbl = new IDBlogic(this);
//        dbl.getLectures(0).printLectureList();
//        dbl.getExams(0).printExamList();
//        dbl.getTasks(0).printTaskList();
//        dbl.getResources(0).printResourceList();
        
        Lecture lecture = new Lecture(1, "700.144", "Updated Lecture", "asfasfsdfsaf", "VU", 0, 0);
        
        dbl.updateLecture(lecture);
        dbl.getLectures(0).printLectureList();
        
        Log.i(CMONsettings.LOG_TAG, "APP done. your brain will be toasted in a few seconds :P"); 
    }
    
    private void Test1() {
    	DDTestsetA TestA = new DDTestsetA(this);
        TestA.FillDb();
//        TestA.someTest();
        
        long unixTime = System.currentTimeMillis() / 1000L;
        
        Date dObj = new Date();
        dObj.setTimestamp(unixTime);
        dObj.setLocation("Home 1");
        dObj.setType("XY 1");
        dObj.setComment("Comment 1 ... -.-");    
        
        Exam eObj = new Exam();
        eObj.setLecture_id(1);
        eObj.setTitle("Ex Title 1");
        eObj.setComment("Ex commnet 1");        
        
        DBLExams e = new DBLExams(this);
        eObj.setId(e.addExam(eObj));
        
        e.setNewExamDate(eObj.getId(), dObj);
        
        dObj.setTimestamp(unixTime);
        dObj.setLocation("Home 2");
        dObj.setType("XY 2");
        dObj.setComment("Comment 2 ... -.-");    
        
        eObj.setLecture_id(1);
        eObj.setTitle("Ex Title 2");
        eObj.setComment("Ex commnet 2");        
        eObj.setId(e.addExam(eObj));
        
        e.setNewExamDate(eObj.getId(), dObj);
        
        HLPRelations r = new HLPRelations(this);
        r.openCon();
        r.allEntriesToLog();
        r.closeCon();
    }
	
}