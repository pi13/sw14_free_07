package at.lvmaster3000.database.demodata;

import android.content.Context;
import at.lvmaster3000.database.logic.DBLDates;
import at.lvmaster3000.database.logic.DBLExams;
import at.lvmaster3000.database.logic.DBLLectures;
import at.lvmaster3000.database.logic.DBLRelations;
import at.lvmaster3000.database.logic.DBLResources;
import at.lvmaster3000.database.logic.DBLTasks;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Exam;
import at.lvmaster3000.database.objects.Lecture;
import at.lvmaster3000.database.objects.Resource;
import at.lvmaster3000.database.objects.Task;

public class DDTestsetB {

	private DBLLectures dblLectures;
	private DBLTasks dblTasks;
	private DBLExams dblExams;
	private DBLResources dblResources;
	private DBLDates dblDates;
	private DBLRelations dblRelations;
	
	private Context context;
	
	public DDTestsetB(Context context) {
		this.context = context;
		this.dblLectures = new DBLLectures(context);
		this.dblTasks = new DBLTasks(context);
		this.dblExams = new DBLExams(context);
		this.dblResources = new DBLResources(context);
		this.dblDates = new DBLDates(context);
		this.dblRelations = new DBLRelations(context);
		
		this.dblLectures.resetTablesInvolved();
		this.dblTasks.resetTablesInvolved();
		this.dblExams.resetTablesInvolved();
		this.dblResources.resetTable();
		this.dblDates.resetTable();
		this.dblRelations.resetTable();
	}
	
	public void fillDb()  {
		Lecture l1 = new Lecture(0, "705.036", "Rechnerorganisation", "Posch K", "VO", 1, 0);
		this.dblLectures.addLecture(l1);
		
		Lecture l2 = new Lecture(0, "716.100", "Mobile Applications", "Slany W", "VU", 1, 0);
		this.dblLectures.addLecture(l2);
		
		long unixTime = System.currentTimeMillis() / 1000L;
        long hour = 3600;
        long day = hour * 24;
        long week = day * 7;
        
		Date d1 = new Date(0, unixTime, "i13", "", "");
		this.dblDates.addDate(d1);
		this.dblLectures.addDateToLecture(d1, l1);
		
		Date d2 = new Date(0, unixTime + week, "i13", "", "");		
		this.dblDates.addDate(d2);
		this.dblLectures.addDateToLecture(d2, l1);
		
		Date d3 = new Date(0, unixTime + (week * 2), "i13", "", "");		
		this.dblDates.addDate(d3);
		this.dblLectures.addDateToLecture(d3, l1);
		
		Date d4 = new Date(0, unixTime + (week * 3), "i13", "", "");		
		this.dblDates.addDate(d4);
		this.dblLectures.addDateToLecture(d4, l1);
		
		Date d5 = new Date(0, unixTime + (week * 4), "i13", "", "");		
		this.dblDates.addDate(d5);
		this.dblLectures.addDateToLecture(d5, l1);
		
		Date d6 = new Date(0, unixTime + (week * 5), "i13", "", "");		
		this.dblDates.addDate(d6);
		this.dblLectures.addDateToLecture(d6, l1);
		
		Date d7 = new Date(0, unixTime + (week * 6), "i13", "", "");		
		this.dblDates.addDate(d7);
		this.dblLectures.addDateToLecture(d7, l1);
		
		Date d8 = new Date(0, unixTime + (week * 7), "i13", "", "");		
		this.dblDates.addDate(d8);
		this.dblLectures.addDateToLecture(d8, l1);
		
		Date d9 = new Date(0, unixTime + (week * 8), "i13", "", "");		
		this.dblDates.addDate(d9);
		this.dblLectures.addDateToLecture(d9, l1);
		
		Date d10 = new Date(0, unixTime + (week * 9), "i13", "", "");		
		this.dblDates.addDate(d10);
		this.dblLectures.addDateToLecture(d10, l1);
		
		Date d11 = new Date(0, unixTime + (week * 10), "i13", "", "");		
		this.dblDates.addDate(d11);
		this.dblLectures.addDateToLecture(d11, l1);
		
		Task t1 = new Task(0, "Complete the app", "", null);
		this.dblTasks.addTask(t1);
		this.dblLectures.addTaskToLecture(t1, l1);
		
		Task t2 = new Task(0, "Task with final date", "No", new Date(0, unixTime + (day * 2), "Inffeldgasse", "", ""));
		this.dblTasks.addTask(t2);
		this.dblLectures.addTaskToLecture(t2, l1);
		
		Exam e1 = new Exam(0, "Exam in RO", "", 0, new Date(0, unixTime + day, "i13", "", ""));
		this.dblExams.addExam(e1);
		this.dblLectures.addExamToLecture(e1, l1);
		
		Exam e2 = new Exam(0, "Exam in MA", "+ Project presentation", 0, new Date(0, unixTime + day, "i7", "", ""));
		this.dblExams.addExam(e2);
		this.dblLectures.addExamToLecture(e2, l2);
		
		Resource r1 = new Resource("Some resource...");
		this.dblResources.addResource(r1);
		this.dblLectures.addResourceToLecture(r1, l1);
		
		//print all
		this.dblLectures.getLectures(0).printLectureList();
		this.dblDates.getDates(0).printDateList();
		this.dblExams.getExams(0, false).printExamList();
		this.dblTasks.getTasks(0, false).printTaskList();
		this.dblResources.getResources(0).printResourceList();
		this.dblRelations.printRelations();
	}
	
}
