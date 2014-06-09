package at.lvmaster3000.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;
import at.lvmaster3000.database.demodata.DDTestsetA;
import at.lvmaster3000.database.lists.Dates;
import at.lvmaster3000.database.lists.Exams;
import at.lvmaster3000.database.lists.Lectures;
import at.lvmaster3000.database.lists.Resources;
import at.lvmaster3000.database.lists.Tasks;
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

public class IDBlogic {

	private DBLLectures lectures;
	private DBLRelations relations;
	private DBLTasks tasks;
	private DBLExams exames;
	private DBLResources resources;
	private DBLDates dates;
	
	/**
	 * 
	 * @param context
	 */
	public IDBlogic(Context context) {
//		DDTestsetA TestA = new DDTestsetA(context);
//		TestA.FillDb();
		
		this.lectures = new DBLLectures(context);
		this.tasks = new DBLTasks(context);
		this.exames = new DBLExams(context);
		this.resources = new DBLResources(context);
		this.relations = new DBLRelations(context);
		this.dates = new DBLDates(context);
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		Boolean dbinstalled = prefs.getBoolean("dbinstalled", false);
		
		Log.w("PREFS", "installed: " + dbinstalled);
		if(!dbinstalled) {
			this.lectures.resetTable();
			this.tasks.resetTable();
			this.exames.resetTable();
			this.resources.resetTable();
			this.relations.resetTable();
			this.dates.resetTable();
			
			Editor editor = prefs.edit();
			editor.putBoolean("dbinstalled", true);
			editor.commit();
		}
		
	}
	
	/**
	 * 
	 * @param lecture
	 * @return	long
	 */
	public long addLecture(Lecture lecture) {
		return this.lectures.addLecture(lecture);
	}
	
	/**
	 * 
	 * @param task
	 * @param lecture
	 * @return
	 */
	public long addTaskToLecture(Task task, Lecture lecture) {
		return this.lectures.addTaskToLecture(task, lecture);
	}
	
	/**
	 * 
	 * @param date
	 * @param lecture
	 * @return
	 */
	public long addDateToLecture(Date date, Lecture lecture) {
		return this.lectures.addDateToLecture(date, lecture);
	}
	
	/**
	 * 
	 * @param exam
	 * @param lecture
	 * @return
	 */
	public long addExamToLecture(Exam exam, Lecture lecture) {
		return this.lectures.addExamToLecture(exam, lecture);
	}
	
	/**
	 * 
	 * @param resource
	 * @param lecture
	 * @return
	 */
	public long addResourceToLecture(Resource resource, Lecture lecture) {
		return this.lectures.addResourceToLecture(resource, lecture);
	}
	
	/**
	 * 
	 * @param lecture
	 * @return int
	 */
	public int deleteLecture(Lecture lecture) {
		return this.lectures.deleteLecture(lecture);
	}
	
	/**
	 * 
	 * @param lecture
	 * @return
	 */
	public long updateLecture(Lecture lecture) {
		return this.lectures.updateLecture(lecture);
	}
	
	/**
	 * 
	 * @param limit	If 0, all lectures are returned
	 * @return Lectures (Object)
	 */
	public Lectures getLectures(int limit) {
		return this.lectures.getLectures(limit);
	}
	
	/**
	 * 
	 * @param lectureID
	 * @return
	 */
	public Lecture getLectureByID(long lectureID) {
		return this.lectures.getLectureByID(lectureID);
	}
	
	/**
	 * 
	 * @param lecture
	 * @return
	 */
	public Tasks getTasksForLecture(Lecture lecture) {
		return this.lectures.getTasksForLecture(lecture);
	}
	
	/**
	 * 
	 * @param lecture
	 * @return
	 */
	public Dates getDatesForLecture(Lecture lecture) {
		return this.lectures.getDatesForLecture(lecture);
	}
	
	/**
	 * 
	 * @param lecture
	 * @return
	 */
	public Exams getExamsForLecture(Lecture lecture) {
		return this.lectures.getExamsForLecture(lecture);
	}
	
	/**
	 * 
	 * @param lecture
	 * @return
	 */
	public Resources getResourcesForLecture(Lecture lecture) {
		return this.lectures.getResourcesForLecture(lecture);
	}
	
	/**
	 * 
	 * @param exam
	 * @return
	 */
	public long addExam(Exam exam){
		return this.exames.addExam(exam);
	}
	
	/**
	 * 
	 * @param exam
	 * @return
	 */
	public int deleteExam(Exam exam){
		return this.exames.deleteExam(exam);
	}
	
	/**
	 * 
	 * @param limit
	 * @return
	 */
	public Exams getExams(int limit) {
		return this.exames.getExams(limit);
	}	
	
	/**
	 * 
	 * @param task
	 * @return
	 */
	public long addTask(Task task){
		return this.tasks.addTask(task);
	}
	
	/**
	 * 
	 * @param task
	 * @return
	 */
	public int deleteTask(Task task){
		return this.tasks.deleteTask(task);
	}
	
	/**
	 * 
	 * @param limit
	 * @return
	 */
	public Tasks getTasks(int limit){
		return this.tasks.getTasks(limit);
	}
	
	/**
	 * 
	 * @param resource
	 * @return
	 */
	public long addResource(Resource resource){
		return this.resources.addResource(resource);
	}
	
	/**
	 * 
	 * @param resource
	 * @return
	 */
	public int deleteResource(Resource resource){
		return this.resources.deleteResource(resource);
	}
	
	/**
	 * 
	 * @param limit
	 * @return
	 */
	public Resources getResources(int limit){
		return this.resources.getResources(limit);
	}
}
