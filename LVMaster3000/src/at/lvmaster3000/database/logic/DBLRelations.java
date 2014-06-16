package at.lvmaster3000.database.logic;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import at.lvmaster3000.database.helper.HLPRelations;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Exam;
import at.lvmaster3000.database.objects.Relation;
import at.lvmaster3000.database.objects.Task;
import at.lvmaster3000.settings.DBsettings;

public class DBLRelations {

	private HLPRelations hlpRelations;
	
	public DBLRelations(Context context) {
		this.hlpRelations = new HLPRelations(context);
	}
	
	public void resetTable() {
		this.hlpRelations.openCon();
		this.hlpRelations.resetTable();
		this.hlpRelations.closeCon();
	}
	
	public long addRelation(String srctable, long lectureID, long examID, long taskID, long dateID, long resID) {
		this.hlpRelations.openCon();
		long ret = this.hlpRelations.addRelation(srctable, lectureID, examID, taskID, dateID, resID);
		this.hlpRelations.closeCon();
		
		return ret;
	}
	
	public long addRelation(Relation relation) {
		long ret = this.addRelation(relation.getSrcTable(), relation.getLectureId(), relation.getExamId(), relation.getTaskId(), relation.getDateId(), relation.getResId());
		relation.setId(ret);
		
		return ret;
	}
	
	public int deleteRelation(long relationId) {
		this.hlpRelations.openCon();
		int ret = this.hlpRelations.deleteRelation(relationId);
		this.hlpRelations.closeCon();
		return ret;
	}
	
	public int deleteRelation(Relation relation) {
		return this.deleteRelation(relation.getId());
	}
	
	public int deleteRelationByDate(Date date){
		int ret = this.hlpRelations.openCon().delete(HLPRelations.TABLE_NAME, HLPRelations.COL_DATE_ID + " = " + date.getID(), null);
		this.hlpRelations.closeCon();
		return ret;
	}
	
	public Relation getRelationById(long id) {
		Relation relation = new Relation();
		
		String query = "SELECT * FROM " + HLPRelations.TABLE_NAME;
		query += " WHERE " + HLPRelations.COL_ID + " = " + id;
		
		Log.i(DBsettings.LOG_TAG_RELATIONS, query);
		
		Cursor cursor = this.hlpRelations.openCon().rawQuery(query, null);
        if(cursor != null) { 
        	if(cursor.getCount() < 1) {
        		return null;
        	}
        	
        	if(cursor.moveToFirst()) {
        		relation.cursorToRelation(cursor);   
        	}
        } else {
        	Log.w(DBsettings.LOG_TAG_RELATIONS, "Cursor is NULL!!");        	
        }
        this.hlpRelations.closeCon();
		
		return relation;
	}
	
	public Relation getRelationsByDate(Date date) {
		Relation relation = new Relation();
		
		String query = "SELECT * FROM " + HLPRelations.TABLE_NAME;
		query += " WHERE " + HLPRelations.COL_DATE_ID + " = " + date.getID();
		
		Log.i(DBsettings.LOG_TAG_RELATIONS, query);
		
		Cursor cursor = this.hlpRelations.openCon().rawQuery(query, null);
        if(cursor != null) {        
        	if(cursor.getCount() < 1) {
        		return null;
        	}
        	
        	if(cursor.moveToFirst()){
        		relation.cursorToRelation(cursor);   
        	}
        } else {
        	Log.w(DBsettings.LOG_TAG_RELATIONS, "Cursor is NULL!!");        	
        }
        this.hlpRelations.closeCon();
		
		return relation;
	}
	
	public Relation getRelationByExamWithDateSet(Exam exam) {
		Relation relation = new Relation();
		
		String query = "SELECT * FROM " + HLPRelations.TABLE_NAME;
		query += " WHERE " + HLPRelations.COL_EXAM_ID + " = " + exam.getId();
		query += " AND " + HLPRelations.COL_DATE_ID + " > 0";
		
		Log.i(DBsettings.LOG_TAG_RELATIONS, query);
		
		Cursor cursor = this.hlpRelations.openCon().rawQuery(query, null);
        if(cursor != null) {
        	if(cursor.getCount() != 1) {
        		this.hlpRelations.closeCon();
        		Log.e(DBsettings.LOG_TAG_RELATIONS, "CNT: " + cursor.getCount());
        		return null;
        	}
        	
        	cursor.moveToFirst();
        	relation.cursorToRelation(cursor);   	
        } else {
        	Log.w(DBsettings.LOG_TAG_RELATIONS, "Cursor is NULL!!");        	
        }
        this.hlpRelations.closeCon();
		
		return relation;
	}
	
	public Relation getRelationByTaskWithDateSet(Task task) {
		Relation relation = new Relation();
		
		String query = "SELECT * FROM " + HLPRelations.TABLE_NAME;
		query += " WHERE " + HLPRelations.COL_TASK_ID + " = " + task.getId();
		query += " AND " + HLPRelations.COL_DATE_ID + " > 0";
		
		Log.i(DBsettings.LOG_TAG_RELATIONS, query);
		
		Cursor cursor = this.hlpRelations.openCon().rawQuery(query, null);
        if(cursor != null) {
        	if(cursor.getCount() != 1) {
        		this.hlpRelations.closeCon();
        		Log.e(DBsettings.LOG_TAG_RELATIONS, "CNT: " + cursor.getCount());
        		return null;
        	}
        	
        	cursor.moveToFirst();
        	relation.cursorToRelation(cursor); 
        } else {
        	Log.w(DBsettings.LOG_TAG_RELATIONS, "Cursor is NULL!!");        	
        }
        
        this.hlpRelations.closeCon();
        
        return relation;
	}
	
	public void printRelations() {
		this.hlpRelations.openCon();
		this.hlpRelations.allEntriesToLog();
		this.hlpRelations.closeCon();
	}
	
}
