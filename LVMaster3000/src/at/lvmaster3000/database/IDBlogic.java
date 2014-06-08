package at.lvmaster3000.database;

import android.content.Context;
import at.lvmaster3000.database.demodata.DDTestsetA;
import at.lvmaster3000.database.lists.Dates;
import at.lvmaster3000.database.lists.Exams;
import at.lvmaster3000.database.lists.Lectures;
import at.lvmaster3000.database.lists.Resources;
import at.lvmaster3000.database.lists.Tasks;
import at.lvmaster3000.database.logic.DBLExams;
import at.lvmaster3000.database.logic.DBLLectures;
import at.lvmaster3000.database.logic.DBLResources;
import at.lvmaster3000.database.logic.DBLTasks;
import at.lvmaster3000.database.objects.Exam;
import at.lvmaster3000.database.objects.Lecture;
import at.lvmaster3000.database.objects.Resource;
import at.lvmaster3000.database.objects.Task;

public class IDBlogic {

	private DBLLectures lectures = null;
	private DBLTasks tasks = null;
	private DBLExams exames = null;
	private DBLResources resources = null;
	
	/**
	 * 
	 * @param context
	 */
	public IDBlogic(Context context) {
		DDTestsetA TestA = new DDTestsetA(context);
        TestA.FillDb();
		
		this.lectures = new DBLLectures(context);
		this.tasks = new DBLTasks(context);
		this.exames = new DBLExams(context);
		this.resources = new DBLResources(context);
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
		return this.resources.deleteResource(resource.getId());
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
