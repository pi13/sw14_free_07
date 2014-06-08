package at.lvmaster3000.database.logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import at.lvmaster3000.database.helper.HLPDates;
import at.lvmaster3000.database.helper.HLPExams;
import at.lvmaster3000.database.helper.HLPLectures;
import at.lvmaster3000.database.helper.HLPRelations;
import at.lvmaster3000.database.helper.HLPResources;
import at.lvmaster3000.database.helper.HLPTasks;
import at.lvmaster3000.database.lists.Dates;
import at.lvmaster3000.database.lists.Exams;
import at.lvmaster3000.database.lists.Lectures;
import at.lvmaster3000.database.lists.Resources;
import at.lvmaster3000.database.lists.Tasks;
import at.lvmaster3000.database.objects.Lecture;
import at.lvmaster3000.database.objects.Relation;
import at.lvmaster3000.database.objects.Resource;
import at.lvmaster3000.settings.DBsettings;

public class DBLLectures {

	private HLPLectures hlpLectures;
	private HLPTasks hlptasks;
	private HLPDates hlpDates;
	private HLPExams hlpExams;
	private HLPResources hlpResources;
	
	private DBLRelations dblRelations;
	private DBLResources dblResources;
	
	/**
	 * 
	 * @param context
	 */
	public DBLLectures(Context context) {
		this.hlpLectures = new HLPLectures(context);
		this.hlptasks = new HLPTasks(context);
		this.hlpDates = new HLPDates(context);
		this.hlpExams = new HLPExams(context);
		this.hlpResources = new HLPResources(context);
		this.dblRelations = new DBLRelations(context);
		this.dblResources = new DBLResources(context);
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
		this.hlpLectures.openCon();
		long id = this.hlpLectures.addLecture(number, name, comment, type, required, compulsory);
		this.hlpLectures.closeCon();
		
		return id; 
	}
	
	/**
	 * 
	 * @param lecture
	 * @return insert id
	 */
	public long addLecture(Lecture lecture) {
		long lid = this.addLecture(lecture.getNumber(), lecture.getName(), lecture.getComment(), lecture.getType(), lecture.getRequired(), lecture.getCompulsory());
		lecture.setID(lid);
		
		return lid;
	}
	
	/**
	 * 
	 * @param resource
	 * @param lecture
	 * @return 
	 */
	public long addResourceToLecture(Resource resource, Lecture lecture) {
		long rid = 0;
		long lid = 0;
		
		if(resource.getId() > 0) {
			rid = resource.getId();
		} else {
			this.dblResources.addResource(resource);
		}
		
		if(lecture.getID() > 0) {
			lid = lecture.getID();
		} else {
			lid = this.addLecture(lecture);
			lecture.setID(lid);
		}		
		
		return this.dblRelations.addRelation(new Relation(0, HLPLectures.TABLE_NAME, lid, 0, 0, 0, rid));
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public int deleteLecture(long id) {
		this.hlpLectures.openCon();
		int res = this.hlpLectures.deleteLecture(id);		
		this.hlpLectures.closeCon();
		
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
	 * 
	 * @param lecture
	 * @return
	 */
	public int updateLecture(Lecture lecture) {		
		ContentValues values = new ContentValues();
		
		if(!lecture.getNumber().isEmpty()){
			values.put(HLPLectures.COL_NUMBER, lecture.getNumber());
		}
		
		if(!lecture.getName().isEmpty()) {
			values.put(HLPLectures.COL_NAME, lecture.getName());
		}		
		
		if(!lecture.getComment().isEmpty()) {
			values.put(HLPLectures.COL_COMMENT, lecture.getComment());
		}
		
		if(!lecture.getType().isEmpty()) {
			values.put(HLPLectures.COL_TYPE, lecture.getType());
		}
		
		if(lecture.getRequired() > -1) {
			values.put(HLPLectures.COL_REQUIRED, lecture.getRequired());
		}
		
		if(lecture.getCompulsory() > -1) {
			values.put(HLPLectures.COL_COMPULSORY, lecture.getCompulsory());
		}
	
		int ret = hlpLectures.openCon().update(HLPLectures.TABLE_NAME, values, "_id = " + lecture.getID(), null);
		
		Log.i(DBsettings.LOG_TAG_LECTURES, "Update res.: " + ret);
		
		return ret;
	}
	
	/**
	 * Get lecture by lecture number
	 * @param number
	 * @return
	 */
	public Lecture getLectureByNumber(String number){
		Lecture lecture = new Lecture();
		
		String query = "SELECT * FROM " + HLPLectures.TABLE_NAME + " WHERE number = '" + number + "'";
		
        Cursor cursor = hlpLectures.openCon().rawQuery(query, null);
        if(cursor != null) {
        	if(cursor.getCount() < 1) {
        		this.hlpLectures.closeCon();
        		return null;
        	}
        	
        	if(cursor.moveToFirst()) {
        		lecture.cursorToLecture(cursor);
        	}
        } else {
        	Log.w(DBsettings.LOG_TAG_LECTURES, "Cursor is NULL!!");        	
        }
        
        this.hlpLectures.closeCon();
		
		return lecture;
	}
	
	/**
	 * Get lecture by lecture id
	 * @param lectureID
	 * @return
	 */
	public Lecture getLectureByID(long lectureID){
		Lecture lecture = new Lecture();
		
		String query = "SELECT * FROM " + HLPLectures.TABLE_NAME + " WHERE _id = '" + lectureID + "'";
		
        Cursor cursor = hlpLectures.openCon().rawQuery(query, null);
        if(cursor != null) {
        	if(cursor.getCount() < 1) {
        		this.hlpLectures.closeCon();
        		return null;
        	}
        	
        	if(cursor.moveToFirst()) {
        		lecture.cursorToLecture(cursor);
        	}
        } else {
        	Log.w(DBsettings.LOG_TAG_LECTURES, "Cursor is NULL!!");        	
        }
        
        this.hlpLectures.closeCon();
		
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
		
        Cursor cursor = hlpLectures.openCon().rawQuery(query, null);
        if(cursor != null) {        	
    		lectures.cursorToLectureList(cursor);        	
        } else {
        	Log.w(DBsettings.LOG_TAG_LECTURES, "Cursor is NULL!!");        	
        }
        
        this.hlpLectures.closeCon();
		
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
		
		Cursor cursor = this.hlpDates.openCon().rawQuery(query, null);
		if(cursor != null) {
			dates.cursorToDateList(cursor);
		} else {
        	Log.w(DBsettings.LOG_TAG_TASKS, "Cursor is NULL!!");        	
        }
		
		this.hlpDates.closeCon();
		
		return dates;
	}

	public Exams getExamsForLecture(Lecture lecture) {
		Exams exams = new Exams();
		
		String query = "SELECT * FROM " + HLPExams.TABLE_NAME;
		
		query += " LEFT JOIN " + HLPRelations.TABLE_NAME + " ON (" + HLPExams.TABLE_NAME + "." + HLPExams.COL_ID + " = " + HLPRelations.COL_EXAM_ID + ")";
		query += " WHERE " + HLPRelations.TABLE_NAME + "." + HLPRelations.COL_LECTURE_ID + " = " + lecture.getID();	
		query += " AND " + HLPRelations.COL_TASK_ID + " = 0";
		query += " AND " + HLPRelations.COL_SRCTABLE + " = '" + HLPLectures.TABLE_NAME + "';";
		
		Log.i(DBsettings.LOG_TAG_EXAMS, query);
		
		Cursor cursor = this.hlpExams.openCon().rawQuery(query, null);
		if(cursor != null) {
			exams.cursorToExamList(cursor);
		} else {
        	Log.w(DBsettings.LOG_TAG_EXAMS, "Cursor is NULL!!");        	
        }
		
		this.hlpExams.closeCon();
		
		return exams;
	}
	
	public Resources getResourcesForLecture(Lecture lecture) {
		Resources resources = new Resources();
		
		String query = "SELECT * FROM " + HLPResources.TABLE_NAME;
		query += " LEFT JOIN " + HLPRelations.TABLE_NAME + " ON (" + HLPResources.TABLE_NAME + "." + HLPResources.COL_ID + " = " + HLPRelations.COL_EXAM_ID + ")";
		query += " WHERE " + HLPRelations.TABLE_NAME + "." + HLPRelations.COL_LECTURE_ID + " = " + lecture.getID();
		query += " AND " + HLPRelations.COL_SRCTABLE + " = '" + HLPLectures.TABLE_NAME + "';";
		
		Log.i(DBsettings.LOG_TAG_RESOURCES, query);
		
		Cursor cursor = this.hlpResources.openCon().rawQuery(query, null);
		if(cursor != null) {
			resources.cursorToResourceList(cursor);
		} else {
        	Log.w(DBsettings.LOG_TAG_RESOURCES, "Cursor is NULL!!");        	
        }
		this.hlpResources.closeCon();
		
		return resources;
	}
}
