package at.lvmaster3000.database.demodata;

import java.util.List;

import android.content.Context;
import at.lvmaster3000.database.helper.HLPCoworkers;
import at.lvmaster3000.database.helper.HLPDates;
import at.lvmaster3000.database.helper.HLPExams;
import at.lvmaster3000.database.helper.HLPLectures;
import at.lvmaster3000.database.helper.HLPRelations;
import at.lvmaster3000.database.helper.HLPResources;
import at.lvmaster3000.database.helper.HLPTasks;
import at.lvmaster3000.database.logic.DBLLectures;
import at.lvmaster3000.database.objects.Exam;
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
	private int lecturecnt;
	private int taskscnt;
	private int datescnt;
	private int examcnt;
	private int resourcecnt;

	public DDTestsetA(Context context) {
		this.hlplectures = new HLPLectures(context);
		this.hlptasks = new HLPTasks(context);
		this.hlpresources = new HLPResources(context);
		this.hlpexams = new HLPExams(context);
		this.hlpcoworkers = new HLPCoworkers(context);
		this.hlpdates = new HLPDates(context);
		this.hlprelations = new HLPRelations(context);
		this.context = context;
		this.lecturecnt = 0;
		this.taskscnt = 0;
		this.datescnt = 0;
		this.examcnt = 0;
		this.resourcecnt = 0;
	}
	
	public void FillDb() {
		//create lecture
		this.hlplectures.openCon();
		this.hlplectures.resetTable();          
        long lid1 = hlplectures.addLecture("701.123", "Test LV 1", "Some Comment...", "VO", 1, 1);
        this.lecturecnt += 1;
        long lid2 = hlplectures.addLecture("702.123", "Test LV 2", "Some Comment...", "UE", 0, 0);
        this.lecturecnt += 1;
        long lid3 = hlplectures.addLecture("703.123", "Test LV 3", "Some Comment...", "SE", 0, 1);
        this.lecturecnt += 1;
        long lid4 = hlplectures.addLecture("704.123", "Test LV 4 with resources", "Some Comment...", "KU", 1, 0);
        this.lecturecnt += 1;
//        this.hlplectures.allEntriesToLog();
        this.hlplectures.closeCon();
        
        //add tasks
        this.hlptasks.openCon();
        this.hlptasks.resetTable();          
        long tid1 = hlptasks.addTask("Test TASK 1", "Some Comment...");
        this.taskscnt += 1;
        long tid2 = hlptasks.addTask("Test TASK 2", "Some Comment...");
        this.taskscnt += 1;
//        this.hlptasks.allEntriesToLog();
        this.hlptasks.closeCon();
        
        //add dates
        this.hlpdates.openCon();
        this.hlpdates.resetTable();  
        long unixTime = System.currentTimeMillis() / 1000L;
        long hour = 60;
        long day = hour * 24;
        long week = day * 7;
        long did1 = hlpdates.addDate(unixTime, "Testlocation 1", "LV", "Thats a comment");
        this.datescnt += 1;
        long did2 = hlpdates.addDate(unixTime + hour , "Testlocation 2", "LV", "Thats a comment");
        this.datescnt += 1;
        long did3 = hlpdates.addDate(unixTime + day, "Testlocation 3", "LV", "Thats a comment");
        this.datescnt += 1;
        long did4 = hlpdates.addDate(unixTime + (day * 2), "Testlocation 4", "LV", "Thats a comment");
        this.datescnt += 1;
        long did5 = hlpdates.addDate(unixTime + week, "Testlocation 5", "LV", "Thats a comment");
        this.datescnt += 1;
//        this.hlpdates.allEntriesToLog();
        this.hlpdates.closeCon();
        
        //add exams
        this.hlpexams.openCon();
        this.hlpexams.resetTable();
        long eid1 = hlpexams.addExam("Ex 1 ", "Some comment", lid1);
        this.examcnt++;
        long eid2 = hlpexams.addExam("1. teilpruefung", "schwerig", lid1);
        this.examcnt++;
        long eid3 = hlpexams.addExam("Exam with res attatched", "schwerig", lid1);
        this.examcnt++;
        this.hlpexams.closeCon();                      
        
        //add resources
        this.hlpresources.openCon();
        this.hlpresources.resetTable();
        long res1 = this.hlpresources.addResource("C# for Dummies");
        this.resourcecnt++;
        long res2 = this.hlpresources.addResource("C++ for Dummies");
        this.resourcecnt++;
        long res3 = this.hlpresources.addResource("Java for Dummies");
        this.resourcecnt++;
        long res4 = this.hlpresources.addResource("PHP for Dummies");
        this.resourcecnt++;
        this.hlpresources.closeCon();
        
      //add relation
        this.hlprelations.openCon();
        this.hlprelations.resetTable();        
        this.hlprelations.addRelation(HLPLectures.TABLE_NAME, lid1, 0, tid1, did5, 0);
        this.hlprelations.addRelation(HLPLectures.TABLE_NAME, lid1, 0, tid2, did3, 0);
        this.hlprelations.addRelation(HLPLectures.TABLE_NAME, lid1, 0, 0, did1, 0);
        this.hlprelations.addRelation(HLPLectures.TABLE_NAME, lid1, 0, 0, did4, 0);
        this.hlprelations.addRelation(HLPLectures.TABLE_NAME, 0, eid1, 0, did2, 0);
        this.hlprelations.addRelation(HLPLectures.TABLE_NAME, lid1, eid2, 0, 0, 0);
        this.hlprelations.addRelation(HLPExams.TABLE_NAME, 0, eid3, 0, 0, res1);
        this.hlprelations.addRelation(HLPExams.TABLE_NAME, 0, eid3, 0, 0, res2);
        this.hlprelations.addRelation(HLPLectures.TABLE_NAME, lid4, 0, 0, 0, res3);
        this.hlprelations.addRelation(HLPLectures.TABLE_NAME, 0, eid3, 0, 0, res4);
//        this.hlprelations.allEntriesToLog();
        this.hlprelations.closeCon();
	}
	
	public int getLectureCnt() {
		return this.lecturecnt;
	}
	
	public int getTasksCnt() {
		return this.taskscnt;
	}
	
	public int getDatesCnt() {
		return this.datescnt;
	}
	
	public int getExamCnt(){
		return this.examcnt;
	}
	
	public int getResourceCnt(){
		return this.resourcecnt;
	}
	
	public void someTest() {
		DBLLectures ll = new DBLLectures(this.context);
		Lecture lecture = ll.getLectureByNumber("701.123");
//		l.getLectures(0).printLectureList();
		
		ll.getTasksForLecture(lecture).printTaskList();
		ll.getDatesForLecture(lecture).printDateList();
	}
	
}
