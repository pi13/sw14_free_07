package at.lvmaster3000.database.logic;

import android.content.Context;
import android.test.RenamingDelegatingContext;
import at.lvmaster3000.database.helper.HLPTasks;
import at.lvmaster3000.database.lists.Tasks;
import at.lvmaster3000.database.objects.Task;

public class DBLTasks {
	
	private HLPTasks hlptasks = null;

	public DBLTasks(Context context) {
		this.hlptasks = new HLPTasks(context);
	}

	public long addTask(Task task) {
		
		return 0;
	}
	
	public int deleteTask(long taskID) {
		this.hlptasks.openCon();
		int res = this.hlptasks.deleteTask(taskID);
		this.hlptasks.closeCon();
		return res;
	}
	
	public int deleteTask(Task task){
		return this.deleteTask(task.getId());	
	}

	public Tasks getTasks(int limit) {
		
		return null;
	}

}
