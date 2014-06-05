package at.lvmaster3000.database.logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import at.lvmaster3000.database.helper.HLPLectures;
import at.lvmaster3000.database.helper.HLPTasks;
import at.lvmaster3000.database.lists.Tasks;
import at.lvmaster3000.database.objects.Task;
import at.lvmaster3000.settings.DBsettings;

public class DBLTasks {
	
	private HLPTasks hlpTasks = null;

	/**
	 * 
	 * @param context
	 */
	public DBLTasks(Context context) {
		this.hlpTasks = new HLPTasks(context);
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
		return this.addTask(task.getTitle(), task.getComment());
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
		
		int ret = this.hlpTasks.openCon().update(HLPTasks.TABLE_NAME, values, "_id = " + task.getId(), null);
		
		Log.i(DBsettings.LOG_TAG_TASKS, "Update res.: " + ret);
		
		return ret;
	}

}
