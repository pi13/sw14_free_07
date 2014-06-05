package at.lvmaster3000.database.objects;

import android.database.Cursor;
import android.util.Log;
import at.lvmaster3000.database.helper.HLPRelations;
import at.lvmaster3000.settings.DBsettings;

public class Relation {

	private long id;
	private String srctable;
	private long lecture_id;
	private long exam_id;
	private long task_id;
	private long date_id;
	private long res_id;
	
	public Relation() {
		
	}
	
	/**
	 * 
	 * @param id
	 * @param srctable
	 * @param lectureId
	 * @param examId
	 * @param taskId
	 * @param dateId
	 * @param resId
	 */
	public Relation(long id, String srctable, long lectureId, long examId, long taskId, long dateId, long resId) {
		this.id = id;
		this.srctable = srctable;
		this.lecture_id = lectureId;
		this.exam_id = examId;
		this.task_id = taskId;
		this.date_id = dateId;
		this.res_id = resId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSrcTable() {
		return srctable;
	}

	public void setSrcTable(String srctable) {
		this.srctable = srctable;
	}

	public long getLectureId() {
		return lecture_id;
	}

	public void setLectureId(long lecture_id) {
		this.lecture_id = lecture_id;
	}

	public long getExamId() {
		return exam_id;
	}

	public void setExamId(long exam_id) {
		this.exam_id = exam_id;
	}

	public long getTaskId() {
		return task_id;
	}

	public void setTaskId(long task_id) {
		this.task_id = task_id;
	}
	public long getDateId() {
		return date_id;
	}

	public void setDateId(long date_id) {
		this.date_id = date_id;
	}

	public long getResId() {
		return res_id;
	}

	public void setResId(long res_id) {
		this.res_id = res_id;
	}
	
	public Relation cursorToRelation(Cursor cursor) {
		this.id = cursor.getLong(cursor.getColumnIndex(HLPRelations.COL_ID)); 
		this.srctable = cursor.getString(cursor.getColumnIndex(HLPRelations.COL_SRCTABLE));
		this.lecture_id = cursor.getLong(cursor.getColumnIndex(HLPRelations.COL_LECTURE_ID));
		this.exam_id = cursor.getLong(cursor.getColumnIndex(HLPRelations.COL_EXAM_ID)); 
		this.task_id = cursor.getLong(cursor.getColumnIndex(HLPRelations.COL_TASK_ID)); 
		this.date_id = cursor.getLong(cursor.getColumnIndex(HLPRelations.COL_DATE_ID)); 
		this.res_id = cursor.getLong(cursor.getColumnIndex(HLPRelations.COL_RES_ID)); 
		
		return this;
	}
	
	public void printRelation() {
		String msg = "ID: " + this.id;
		msg += " | SrcTable: " + this.srctable;
		msg += " | Lecture ID: " + this.lecture_id;
		msg += " | Exam ID: " + this.exam_id;
		msg += " | Task ID: " + this.task_id;
		msg += " | Date ID: " + this.date_id;
		msg += " | Resource ID: " + this.res_id;

		Log.d(DBsettings.LOG_TAG_RELATIONS, msg);
	}
	
}
