package at.lvmaster3000.database.objects;

import android.database.Cursor;
import android.util.Log;
import at.lvmaster3000.database.helper.HLPExams;
import at.lvmaster3000.database.helper.HLPRelations;
import at.lvmaster3000.database.helper.HLPTasks;
import at.lvmaster3000.settings.DBsettings;

public class Exam {
	private long id;
	private String title;
	private String comment;
	private long lecture_id;
	private Date date;
	
	public Exam() {
		
	}
	
	public Exam(long id, String title, String comment, long lectureId, Date date){
		this.id = id; 
		this.title = title;
		this.comment = comment;
		this.lecture_id = lectureId;
		this.setDate(date);
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public long getLecture_id() {
		return lecture_id;
	}
	public void setLecture_id(long lecture_id) {
		this.lecture_id = lecture_id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Exam cursorToExam(Cursor cursor) {
		int idx = cursor.getColumnIndex(HLPRelations.COL_EXAM_ID);
		
		if(idx < 0) {
			this.id = cursor.getLong(cursor.getColumnIndex(HLPExams.COL_ID));
		} else {
			this.id = cursor.getLong(idx);
		}
		
//		this.id = cursor.getLong(cursor.getColumnIndex(HLPExams.COL_ID)); 
		this.title = cursor.getString(cursor.getColumnIndex(HLPExams.COL_TITLE));
		this.comment = cursor.getString(cursor.getColumnIndex(HLPExams.COL_COMMENT));
		this.lecture_id = cursor.getLong(cursor.getColumnIndex(HLPExams.COL_LECTURE_ID));
		this.date = new Date().cursorToDate(cursor);
		
//		this.printExam();
		
		return this;
	}
	
	public void printExam() {
		String msg = "ID: " + this.id;
		msg += " | Title: " + this.title;
		msg += " | Comment: " + this.comment;
		msg += " | Lecture ID: " +  this.lecture_id;
		Log.d(DBsettings.LOG_TAG_EXAMS, msg);
		if(date != null) {
			date.printDate();
		}
	}

}
