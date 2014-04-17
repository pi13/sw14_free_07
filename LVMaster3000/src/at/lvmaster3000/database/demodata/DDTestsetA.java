package at.lvmaster3000.database.demodata;

import android.content.Context;
import at.lvmaster3000.database.helper.HLPCoworkers;
import at.lvmaster3000.database.helper.HLPDates;
import at.lvmaster3000.database.helper.HLPExams;
import at.lvmaster3000.database.helper.HLPLectures;
import at.lvmaster3000.database.helper.HLPRelations;
import at.lvmaster3000.database.helper.HLPResources;
import at.lvmaster3000.database.helper.HLPTasks;
import at.lvmaster3000.database.logic.DBLLectures;
import at.lvmaster3000.database.objects.Lecture;

public class DDTestsetA {

	/* Helper classes */
	private HLPLectures hlplectures;
	private HLPTasks hlptasks;
	private HLPResources hlpresources;
	private HLPExams hlpexams;
	private HLPCoworkers hlpcoworkers;
	private HLPDates hlpdates;
	private HLPRelations hlprelations;
	private Context context;
	
	public DDTestsetA(Context context) {
		this.hlplectures = new HLPLectures(context);
		this.hlptasks = new HLPTasks(context);
		this.hlpresources = new HLPResources(context);
		this.hlpexams = new HLPExams(context);
		this.hlpcoworkers = new HLPCoworkers(context);
		this.hlpdates = new HLPDates(context);
		this.hlprelations = new HLPRelations(context);
		this.context = context;
	}
	
	public void FillDb() {
		//create lecture
		this.hlplectures.openCon();
		this.hlplectures.resetTable();          
        long lid = hlplectures.addLecture("701.123", "Test LV", "Some Comment...", "LV", 1, 1);
        this.hlplectures.allEntriesToLog();
        this.hlplectures.closeCon();
        
        //add tasks
        this.hlptasks.openCon();
        this.hlptasks.resetTable();          
        long tid1 = hlptasks.addTask("Test TASK 1", "Some Comment...");
        long tid2 = hlptasks.addTask("Test TASK 2", "Some Comment...");
        this.hlptasks.allEntriesToLog();
        this.hlptasks.closeCon();
        
        //add dates
        this.hlpdates.openCon();
        this.hlpdates.resetTable();  
        long unixTime = System.currentTimeMillis() / 1000L;
        long hour = 60;
        long day = hour * 24;
        long week = day * 7;
        long did1 = hlpdates.addDate(unixTime, "Testlocation 1", "LV", "Thats a comment");
        long did2 = hlpdates.addDate(unixTime + hour , "Testlocation 2", "LV", "Thats a comment");
        long did3 = hlpdates.addDate(unixTime + day, "Testlocation 3", "LV", "Thats a comment");
        long did4 = hlpdates.addDate(unixTime + (day * 2), "Testlocation 4", "LV", "Thats a comment");
        long did5 = hlpdates.addDate(unixTime + week, "Testlocation 5", "LV", "Thats a comment");
        this.hlpdates.allEntriesToLog();
        this.hlpdates.closeCon();
        
        //add relation
        this.hlprelations.openCon();
        this.hlprelations.resetTable();        
        this.hlprelations.addRelation("lectures", lid, 0, tid1, did5);
        this.hlprelations.addRelation("lectures", lid, 0, tid2, did3);
        this.hlprelations.addRelation("lectures", lid, 0, 0, did1);
        this.hlprelations.addRelation("lectures", lid, 0, 0, did4);
        this.hlprelations.addRelation("lectures", lid, 0, 0, did2);
        this.hlprelations.allEntriesToLog();
        this.hlprelations.closeCon();
	}
	
	public void someTest() {
		DBLLectures ll = new DBLLectures(this.context);
		Lecture lecture = ll.getLectureByNumber("701.123");
//		l.getLectures(0).printLectureList();
		
		ll.getTasksForLecture(lecture).printTaskList();
		ll.getDatesForLecture(lecture).printDateList();
	}
	
}
