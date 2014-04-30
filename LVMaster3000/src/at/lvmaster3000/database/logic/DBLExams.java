package at.lvmaster3000.database.logic;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import at.lvmaster3000.database.helper.HLPCoworkers;
import at.lvmaster3000.database.helper.HLPDates;
import at.lvmaster3000.database.helper.HLPExams;
import at.lvmaster3000.database.helper.HLPLectures;
import at.lvmaster3000.database.helper.HLPRelations;
import at.lvmaster3000.database.helper.HLPResources;
import at.lvmaster3000.database.lists.Coworkers;
import at.lvmaster3000.database.lists.Exams;
import at.lvmaster3000.database.lists.Resources;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Exam;
import at.lvmaster3000.settings.DBsettings;

public class DBLExams {
	
	private HLPExams hlpExams;
	private HLPDates hlpDates;
	private HLPResources hlpResources;
	private HLPCoworkers hlpCoworkers;
	private HLPRelations hlpRelations;
	
	public DBLExams(Context context) {
		hlpExams = new HLPExams(context);
		hlpRelations = new HLPRelations(context);
		hlpDates = new HLPDates(context);
		hlpResources = new HLPResources(context);
		hlpCoworkers = new HLPCoworkers(context);
	}

	public long addExam(Exam exam) {
		hlpExams.openCon();
		long id = this.hlpExams.addExam(exam.getTitle(), exam.getComment(), exam.getLecture_id());
		hlpExams.closeCon();
		return id;
	}

	public long addExam(String title, String comment, long lectureId){
		hlpExams.openCon();
		long id = this.hlpExams.addExam(title, comment, lectureId);
		hlpExams.closeCon();
		return id;
	}
	
	public Exams getAllExams(int limit) {
		Exams exams = new Exams();
		
		String query = "SELECT * FROM " + HLPExams.TABLE_NAME;
		
		if(limit > 0) {
			query += " LIMIT " + limit;
		}
		
        Cursor cursor = hlpExams.openCon().rawQuery(query, null);
        if(cursor != null) {                	
        	exams.cursorToExamList(cursor);        	
        } else {
        	Log.w(DBsettings.LOG_TAG_EXAMS, "Cursor is NULL!!");        	
        }
        hlpExams.closeCon();
		
		return exams;
	}

	public int deleteExam(long id) {
		hlpExams.openCon();
		int res = hlpExams.deleteExams(id);
		hlpExams.closeCon();
		return res;
	}

	public void editExam(Exam editedExam) {
		String query = "UPDATE " + HLPExams.TABLE_NAME + " SET " + HLPExams.COL_LECTURE_ID + " = ?";
		query += ", " + HLPExams.COL_TITLE   + " = ?";
		query += ", " + HLPExams.COL_COMMENT + " = ?";
	    query += " WHERE " + HLPExams.COL_ID + " = ?";
		
	    SQLiteStatement stmt = this.hlpExams.openCon().compileStatement(query);
	    stmt.bindLong(1, editedExam.getLecture_id());
	    stmt.bindString(2, editedExam.getTitle());
	    stmt.bindString(3, editedExam.getComment());
	    stmt.bindLong(4, editedExam.getId());
	    stmt.execute();
        hlpExams.closeCon();
	}

	public Exam getExamById(long id) {
		Exam exam = new Exam();
		
		String query = "SELECT * FROM " + HLPExams.TABLE_NAME + " WHERE _id = '" + id + "'";
		
        Cursor cursor = hlpExams.openCon().rawQuery(query, null);
        if(cursor != null) {        	
        	if(cursor.moveToFirst()) {
        		exam.cursorToExam(cursor);
        	}
        } else {
        	Log.w(DBsettings.LOG_TAG_LECTURES, "Cursor is NULL!!");        	
        }
        hlpExams.closeCon();
		
		return exam;
	}

	/**
	 * 
	 * 
	 * @param examId
	 * @param date
	 * @return
	 */
	public boolean setNewExamDate(long examId, Date date) {
		boolean worked = false;
		
		hlpDates.openCon();
		long dateId = hlpDates.addDate(date.getTimestamp(), date.getLocation(), date.getType(), date.getComment());
		hlpDates.closeCon();
		
		hlpRelations.openCon();
		long relId = hlpRelations.addRelation(HLPExams.TABLE_NAME, 0, examId, 0, dateId, 0);
		hlpRelations.closeCon();
		
		if(dateId != -1 && relId != -1)
			worked = true;

		return worked;
	}

	public Resources getAllResourcesOfExam(long examId) {
		Resources resources = new Resources();
		
		String query = "SELECT * FROM " + HLPResources.TABLE_NAME + " res ";
		query += " INNER JOIN " + HLPRelations.TABLE_NAME + " rel ";
		query += " ON res." + HLPResources.COL_ID + " = rel." + HLPRelations.COL_RES_ID;
		query += " WHERE " + HLPRelations.COL_EXAM_ID + " = " + examId;
		
		Log.i(DBsettings.LOG_TAG_TASKS, query);
		
		Cursor cursor = this.hlpRelations.openCon().rawQuery(query, null);
		if(cursor != null) {                	
			resources.cursorToResourceList(cursor);        	
        } else {
        	Log.w(DBsettings.LOG_TAG_EXAMS, "Cursor is NULL!!");        	
        }
		
		this.hlpRelations.closeCon();
		
		return resources;
	}

	public Coworkers getAllCoworkersOfExam(long examId){
		// TODO Auto-generated method stub
		return null;
	}

	public Date getDateOfExam(long examId) {
		// TODO Auto-generated method stub
		return null;
	}


}
