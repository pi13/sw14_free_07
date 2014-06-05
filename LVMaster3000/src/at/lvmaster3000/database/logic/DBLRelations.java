package at.lvmaster3000.database.logic;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import at.lvmaster3000.database.helper.HLPDates;
import at.lvmaster3000.database.helper.HLPRelations;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Exam;
import at.lvmaster3000.database.objects.Relation;
import at.lvmaster3000.settings.DBsettings;

public class DBLRelations {

	private HLPRelations hlpRelations;
	
	public DBLRelations(Context context) {
		this.hlpRelations = new HLPRelations(context);
	}
	
	public long addRelation(Relation relation) {
		this.hlpRelations.openCon();
		long ret = this.hlpRelations.addRelation(relation.getSrcTable(), relation.getLectureId(), relation.getExamId(), relation.getTaskId(), relation.getDateId(), relation.getResId());
		this.hlpRelations.closeCon();
		return ret;
	}
	
	public int deleteRelation(Relation relation) {
		this.hlpRelations.openCon();
		int ret = this.hlpRelations.deleteRelation(relation.getId());
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
        	cursor.moveToFirst();
        	relation.cursorToRelation(cursor);   	
        } else {
        	Log.w(DBsettings.LOG_TAG_TASKS, "Cursor is NULL!!");        	
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
        	cursor.moveToFirst();
        	relation.cursorToRelation(cursor);   	
        } else {
        	Log.w(DBsettings.LOG_TAG_TASKS, "Cursor is NULL!!");        	
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
        	if(cursor.getCount() > 1) {
        		this.hlpRelations.closeCon();
        		Log.e(DBsettings.LOG_TAG_TASKS, "This should never happen. CNT: " + cursor.getCount());
        		return null;
        	}
        	
        	if(cursor.getCount() < 1) {
        		this.hlpRelations.closeCon();
        		Log.e(DBsettings.LOG_TAG_TASKS, "This are not the droids u're searching. CNT: " + cursor.getCount());
        		return null;
        	}
        	
        	cursor.moveToFirst();
        	relation.cursorToRelation(cursor);   	
        } else {
        	Log.w(DBsettings.LOG_TAG_TASKS, "Cursor is NULL!!");        	
        }
        this.hlpRelations.closeCon();
		
		return relation;
	}
	
}
