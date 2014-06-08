package at.lvmaster3000.database.logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import at.lvmaster3000.database.helper.HLPCoworkers;
import at.lvmaster3000.database.helper.HLPDates;
import at.lvmaster3000.database.helper.HLPExams;
import at.lvmaster3000.database.helper.HLPRelations;
import at.lvmaster3000.database.helper.HLPResources;
import at.lvmaster3000.database.lists.Coworkers;
import at.lvmaster3000.database.lists.Exams;
import at.lvmaster3000.database.lists.Resources;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Exam;
import at.lvmaster3000.database.objects.Relation;
import at.lvmaster3000.settings.DBsettings;

public class DBLExams {
	
	private HLPExams hlpExams;
//	private HLPDates hlpDates;
//	private HLPResources hlpResources;
//	private HLPCoworkers hlpCoworkers;
//	private HLPRelations hlpRelations;

	private DBLRelations dblRelations;
	private DBLDates dblDates;
	
	/**
	 * 
	 * @param context
	 */
	public DBLExams(Context context) {
		hlpExams = new HLPExams(context);
//		hlpRelations = new HLPRelations(context);
//		hlpDates = new HLPDates(context);
//		hlpResources = new HLPResources(context);
//		hlpCoworkers = new HLPCoworkers(context);
		
		this.dblRelations = new DBLRelations(context);
		this.dblDates = new DBLDates(context);
	}
	
	public void resetTable() {
		this.hlpExams.openCon();
		this.hlpExams.resetTable();
		this.hlpExams.closeCon();
	}

	/**
	 * 
	 * @param exam
	 * @return
	 */
	public long addExam(Exam exam) {
		long id = this.addExam(exam.getTitle(), exam.getComment(), exam.getLecture_id());
		exam.setId(id);
		return id;
	}

	/**
	 * 
	 * @param title
	 * @param comment
	 * @param lectureId
	 * @return
	 */
	public long addExam(String title, String comment, long lectureId){
		this.hlpExams.openCon();
		long id = this.hlpExams.addExam(title, comment, lectureId);
		this.hlpExams.closeCon();
		return id;
	}
	
	/**
	 * 
	 * @param limit
	 * @return
	 */
	public Exams getExams(int limit) {
		Exams exams = new Exams();
		
		String query = "SELECT * FROM " + HLPExams.TABLE_NAME;
		
		if(limit > 0) {
			query += " LIMIT " + limit;
		}
		
        Cursor cursor = this.hlpExams.openCon().rawQuery(query, null);
        if(cursor != null) {
        	exams.cursorToExamList(cursor);        	
        } else {
        	Log.w(DBsettings.LOG_TAG_EXAMS, "Cursor is NULL!!");        	
        }
        this.hlpExams.closeCon();
		
		return exams;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public int deleteExam(long id) {
		this.hlpExams.openCon();
		int res = hlpExams.deleteExam(id);
		this.hlpExams.closeCon();
		return res;
	}
	
	/**
	 * 
	 * @param exam
	 * @return
	 */
	public int deleteExam(Exam exam){
		return this.deleteExam(exam.getId());
	}

	/**
	 * 
	 * @param exam
	 * @return
	 */
	public int updateExam(Exam exam) {
		ContentValues values = new ContentValues();
		
		if(!exam.getTitle().isEmpty()) {
			values.put(HLPExams.COL_TITLE, exam.getTitle());
		}
		
		if(!exam.getComment().isEmpty()) {
			values.put(HLPExams.COL_COMMENT, exam.getComment());
		}
		
		if(exam.getLecture_id() > 0) {
			values.put(HLPExams.COL_LECTURE_ID, exam.getLecture_id());
		}
		
		int ret = this.hlpExams.openCon().update(HLPExams.TABLE_NAME, values, "_id = " + exam.getId(), null);
		
		Log.i(DBsettings.LOG_TAG_EXAMS, "Update res.: " + ret);
		
		return ret;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Exam getExamById(long id) {
		Exam exam = new Exam();
		
		String query = "SELECT * FROM " + HLPExams.TABLE_NAME + " WHERE _id = '" + id + "'";
		
        Cursor cursor = this.hlpExams.openCon().rawQuery(query, null);
        if(cursor != null) {
        	if(cursor.getCount() < 1) {
        		this.hlpExams.closeCon();
        		return null;
        	}
        	
        	if(cursor.moveToFirst()) {
        		exam.cursorToExam(cursor);
        	}
        } else {
        	Log.w(DBsettings.LOG_TAG_LECTURES, "Cursor is NULL!!");        	
        }
        this.hlpExams.closeCon();
		
		return exam;
	}

	/**
	 * 
	 * 
	 * @param examId
	 * @param date
	 * @return
	 */
	public boolean setExamDate(Exam exam, Date date) {
		boolean worked = false;
				
		Relation relation = this.dblRelations.getRelationByExamWithDateSet(exam);
		if(relation != null) {
			this.dblDates.deleteDate(relation.getDateId());
			this.dblRelations.deleteRelation(relation);
		}
		
		long dateId = this.dblDates.addDate(date);
		long relId = this.dblRelations.addRelation(new Relation(0, HLPExams.TABLE_NAME, 0, exam.getId(), 0, dateId, 0));
		
		this.dblRelations.getRelationById(relId).printRelation();
		
		if(dateId != -1 && relId != -1)
			worked = true;

		return worked;
	}
	
	public Date getExamDate(Exam exam) {
		Relation relation = this.dblRelations.getRelationByExamWithDateSet(exam);
		if(relation == null) {
			return null;
		}
		
		return this.dblDates.getDateByRelation(relation);
	}

	/**
	 * 
	 * @param examId
	 * @return
	 */
	public Resources getExamResources(Exam exam) {
		Resources resources = new Resources();
		
		String query = "SELECT * FROM " + HLPResources.TABLE_NAME;
		query += " LEFT JOIN " + HLPRelations.TABLE_NAME + "  ON (" + HLPResources.TABLE_NAME + "." + HLPResources.COL_ID + " = " + HLPRelations.COL_RES_ID + ")";
		query += " WHERE " + HLPRelations.COL_EXAM_ID + " = " + exam.getId();
		query += " AND " + HLPRelations.COL_SRCTABLE + " = '" + HLPExams.TABLE_NAME + "';";
		
		Log.i(DBsettings.LOG_TAG_TASKS, query);
		
		Cursor cursor = this.hlpExams.openCon().rawQuery(query, null);
		if(cursor != null) {			
			resources.cursorToResourceList(cursor);        	
        } else {
        	Log.w(DBsettings.LOG_TAG_EXAMS, "Cursor is NULL!!");        	
        }
		
		this.hlpExams.closeCon();
		
		return resources;
	}

	public Coworkers getExamCoworkers(Exam exam){
		// TODO Auto-generated method stub
		return null;
	}

}
