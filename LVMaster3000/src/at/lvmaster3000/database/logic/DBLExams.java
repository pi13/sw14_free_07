package at.lvmaster3000.database.logic;

import android.content.Context;
import android.database.Cursor;
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
	
	public DBLExams(Context context) {
		hlpExams = new HLPExams(context);
		hlpDates = new HLPDates(context);
		hlpResources = new HLPResources(context);
		hlpCoworkers = new HLPCoworkers(context);
	}

	public long addExam(Exam exam) {
		return this.hlpExams.addExam(exam.getTitle(), exam.getComment(), exam.getLecture_id());
	}

	public long addExam(String title, String comment, long lectureId){
		return this.hlpExams.addExam(title, comment, lectureId);
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

	public void deleteExam(long id) {
		hlpExams.deleteExams(id);
	}

	public void editExam(Exam editedExam) {
		// TODO Auto-generated method stub
		
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

	public boolean setDateToExam(long examId, Date date) {
		// TODO Auto-generated method stub
		return false;
	}

	public Resources getAllResourcesOfExam(long examId) {
//		Resources resources = new Resources();
//		
//		String query = "SELECT * FROM " + HLPResources.TABLE_NAME;
//		query += " ";
//		
//		Log.i(DBsettings.LOG_TAG_TASKS, query);
//		
//		Cursor cursor = this.hlptasts.openCon().rawQuery(query, null);
//		if(cursor != null) {
//			tasks.cursorToTaskList(cursor);
//		} else {
//        	Log.w(DBsettings.LOG_TAG_TASKS, "Cursor is NULL!!");        	
//        }
//		
//		return tasks;
	}

	public Coworkers getAllCoworkersOfExam(long examId){
		// TODO Auto-generated method stub
		return null;
	}


}
