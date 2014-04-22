package at.lvmaster3000.database.logic;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import at.lvmaster3000.database.helper.HLPDates;
import at.lvmaster3000.database.helper.HLPLectures;
import at.lvmaster3000.database.helper.HLPRelations;
import at.lvmaster3000.database.helper.HLPTasks;
import at.lvmaster3000.database.lists.Dates;
import at.lvmaster3000.database.lists.Exams;
import at.lvmaster3000.database.lists.Lectures;
import at.lvmaster3000.database.lists.Tasks;
import at.lvmaster3000.database.objects.Lecture;
import at.lvmaster3000.settings.DBsettings;

public class DBLLectures {

	private HLPLectures hlplectures;
	private HLPTasks hlptasks;
	private HLPDates hlpdates;
	
	/**
	 * 
	 * @param context
	 */
	public DBLLectures(Context context) {
		this.hlplectures = new HLPLectures(context);
		this.hlptasks = new HLPTasks(context);
		this.hlpdates = new HLPDates(context);
	}
	
	/**
	 * 
	 * @param number
	 * @param name
	 * @param comment
	 * @param type
	 * @param required
	 * @param compulsory
	 * @return
	 */
	public long addLecture(String number, String name, String comment, String type, int required, int compulsory) {
		this.hlplectures.openCon();
		long id = this.hlplectures.addLecture(number, name, comment, type, required, compulsory);
		this.hlplectures.closeCon();
		
		return id; 
	}
	
	/**
	 * 
	 * @param lecture
	 * @return
	 */
	public long addLecture(Lecture lecture) {
		return this.addLecture(lecture.getNumber(), lecture.getName(), lecture.getComment(), lecture.getType(), lecture.getRequired(), lecture.getCompulsory());
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public int deleteLecture(long id) {
		this.hlplectures.openCon();
		int res = this.hlplectures.deleteLecture(id);		
		this.hlplectures.closeCon();
		
		return res;
	}
	
	/**
	 * 
	 * @param lecture
	 */
	public int deleteLecture(Lecture lecture) {
		return this.deleteLecture(lecture.getID());
	}
	
	/**
	 * Get lecture by lecture number
	 * @param number
	 * @return
	 */
	public Lecture getLectureByNumber(String number){
		Lecture lecture = new Lecture();
		
		String query = "SELECT * FROM " + HLPLectures.TABLE_NAME + " WHERE number = '" + number + "'";
		
        Cursor cursor = hlplectures.openCon().rawQuery(query, null);
        if(cursor != null) {        	
        	if(cursor.moveToFirst()) {
        		lecture.cursorToLecture(cursor);
        	}
        } else {
        	Log.w(DBsettings.LOG_TAG_LECTURES, "Cursor is NULL!!");        	
        }
        hlplectures.closeCon();
		
		return lecture;
	}
	
	/**
	 * 
	 * @param limit If 0, no limit is set
	 * @return
	 */
	public Lectures getLectures(int limit) {
		Lectures lectures = new Lectures();
		
		String query = "SELECT * FROM " + HLPLectures.TABLE_NAME;
		
		if(limit > 0) {
			query += " LIMIT " + limit;
		}
		
        Cursor cursor = hlplectures.openCon().rawQuery(query, null);
        if(cursor != null) {                	
    		lectures.cursorToLectureList(cursor);        	
        } else {
        	Log.w(DBsettings.LOG_TAG_LECTURES, "Cursor is NULL!!");        	
        }
        hlplectures.closeCon();
		
		return lectures;
	}
	
	public Tasks getTasksForLecture(Lecture lecture) {
		Tasks tasks = new Tasks();
		
		String query = "SELECT * FROM " + HLPTasks.TABLE_NAME;
		query += " LEFT JOIN " + HLPRelations.TABLE_NAME + " ON (" + HLPTasks.TABLE_NAME + "." + HLPTasks.COL_ID + " = " + HLPRelations.COL_TASK_ID + ")";
		query += " LEFT JOIN " + HLPDates.TABLE_NAME + " ON (" + HLPDates.TABLE_NAME + "." + HLPDates.COL_ID + " = " + HLPRelations.COL_DATE_ID + ")";
		query += " WHERE " + HLPRelations.TABLE_NAME + "." + HLPRelations.COL_LECTURE_ID + " = " + lecture.getID();
		query += " AND " + HLPRelations.COL_EXAM_ID + " = 0";
		query += " AND " + HLPRelations.COL_SRCTABLE + " = '" + HLPLectures.TABLE_NAME + "';";
		
		Log.i(DBsettings.LOG_TAG_TASKS, query);
		
		Cursor cursor = this.hlptasks.openCon().rawQuery(query, null);
		if(cursor != null) {
			tasks.cursorToTaskList(cursor);
		} else {
        	Log.w(DBsettings.LOG_TAG_TASKS, "Cursor is NULL!!");        	
        }
		this.hlptasks.closeCon();
		
		return tasks;
	}
	
	public Dates getDatesForLecture(Lecture lecture) {
		Dates dates = new Dates();
		
		String query = "SELECT * FROM " + HLPDates.TABLE_NAME;
		query += " LEFT JOIN " + HLPRelations.TABLE_NAME + " ON (" + HLPDates.TABLE_NAME + "." + HLPDates.COL_ID + " = " + HLPRelations.COL_DATE_ID + ")";
		query += " WHERE " + HLPRelations.TABLE_NAME + "." + HLPRelations.COL_LECTURE_ID + " = " + lecture.getID();
		query += " AND " + HLPRelations.COL_EXAM_ID + " = 0";
		query += " AND " + HLPRelations.COL_TASK_ID + " = 0";
		query += " AND " + HLPRelations.COL_SRCTABLE + " = '" + HLPLectures.TABLE_NAME + "';";
		
		Log.i(DBsettings.LOG_TAG_DATES, query);
		
		Cursor cursor = this.hlpdates.openCon().rawQuery(query, null);
		if(cursor != null) {
			dates.cursorToDateList(cursor);
		} else {
        	Log.w(DBsettings.LOG_TAG_TASKS, "Cursor is NULL!!");        	
        }
		this.hlpdates.closeCon();
		
		return dates;
	}

	public Exams getExamsForLecture(Lecture lecture) {
		// TODO Auto-generated method stub
		return null;
	}
}
