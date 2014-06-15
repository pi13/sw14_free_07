package at.lvmaster3000.database.logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import at.lvmaster3000.database.helper.HLPTasks;
import at.lvmaster3000.database.lists.Tasks;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Relation;
import at.lvmaster3000.database.objects.Task;
import at.lvmaster3000.settings.DBsettings;

public class DBLTasks {
	
	private HLPTasks hlpTasks;
	private DBLDates dblDates;
	private DBLRelations dblRelations;

	/**
	 * 
	 * @param context
	 */
	public DBLTasks(Context context) {
		this.hlpTasks = new HLPTasks(context);
		this.dblDates = new DBLDates(context);
		this.dblRelations = new DBLRelations(context);
	}
	
	public void resetTable() {
		this.hlpTasks.openCon();
		this.hlpTasks.resetTable();
		this.hlpTasks.closeCon();
	}
	
	public void resetTablesInvolved() {
		this.resetTable();
		this.dblDates.resetTable();
		this.dblRelations.resetTable();
	}
	
	/**
	 * 
	 * @param title
	 * @param comment
	 * @return
	 */
	public long addTask(String title, String comment) {
		this.hlpTasks.openCon();
		long id = this.hlpTasks.addTask(title, comment);
		this.hlpTasks.closeCon();
		return id;
	}

	/**
	 * 
	 * @param task
	 * @return
	 */
	public long addTask(Task task) {
		long tid = this.addTask(task.getTitle(), task.getComment());
		task.setId(tid);
		
		if(task.getDate() != null) {
			this.setTaskDate(task, task.getDate());
		}
		
		return tid;
	}
	
	/**
	 * 
	 * @param taskID
	 * @return
	 */
	public int deleteTask(long taskID) {
		this.hlpTasks.openCon();
		int res = this.hlpTasks.deleteTask(taskID);
		this.hlpTasks.closeCon();
		return res;
	}
	
	/**
	 * 
	 * @param task
	 * @return
	 */
	public int deleteTask(Task task){
		return this.deleteTask(task.getId());	
	}

	/**
	 * 
	 * @param limit
	 * @return
	 */
	public Tasks getTasks(int limit) {
		Tasks tasks = new Tasks();
		String query = "SELECT * FROM " + HLPTasks.TABLE_NAME;
		
		if(limit > 0) {
			query += " LIMIT " + limit;
		}
		
        Cursor cursor = this.hlpTasks.openCon().rawQuery(query, null);
        if(cursor != null) {       
        	tasks.cursorToTaskList(cursor);    	
        } else {
        	Log.w(DBsettings.LOG_TAG_TASKS, "Cursor is NULL!!");        	
        }
        this.hlpTasks.closeCon();
		
		return tasks;
	}
	
	/**
	 * 
	 * @param task
	 * @return
	 */
	public int updateTask(Task task) {
		ContentValues values = new ContentValues();
		
		if(!task.getTitle().isEmpty()) {
			values.put(HLPTasks.COL_TITLE, task.getTitle());
		}
		
		if(!task.getComment().isEmpty()) {
			values.put(HLPTasks.COL_COMMENT, task.getComment());
		}
		
		if(task.getDate() != null) {
			this.dblDates.updateDate(task.getDate());
		}
		
		int ret = this.hlpTasks.openCon().update(HLPTasks.TABLE_NAME, values, "_id = " + task.getId(), null);
		
		Log.i(DBsettings.LOG_TAG_TASKS, "Update res.: " + ret);
		
		return ret;
	}
	
	/**
	 * 
	 * @param task
	 * @param date
	 * @return
	 */
	public boolean setTaskDate(Task task, Date date) {
		boolean worked = false;
		
		Relation relation = this.dblRelations.getRelationByTaskWithDateSet(task);
		if(relation != null) {
			this.dblDates.deleteDate(relation.getDateId());
			this.dblRelations.deleteRelation(relation);
		}
		
		long dateId = this.dblDates.addDate(date);
		long relId = this.dblRelations.addRelation(new Relation(0, HLPTasks.TABLE_NAME, 0, 0, task.getId(), dateId, 0));
		
		if(dateId != -1 && relId != -1)
			worked = true;

		return worked;
	}
	
	/**
	 * 
	 * @param task
	 * @return
	 */
	public Date getTaskDate(Task task) {
		Relation relation = this.dblRelations.getRelationByTaskWithDateSet(task);
		if(relation == null) {
			return null;
		}
		
		return this.dblDates.getDateByRelation(relation);
	}

}
