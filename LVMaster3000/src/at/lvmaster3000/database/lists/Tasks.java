package at.lvmaster3000.database.lists;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import at.lvmaster3000.database.objects.Task;

public class Tasks {

	private List<Task> tasks;
	
	public Tasks() {
		this.tasks = new ArrayList<Task>();
	}
	
	public void addTask(Task task) {
		this.tasks.add(task);		
	}
	
	public List<Task> getTasks() {
		return this.tasks;
	}
	
	public void cursorToTaskList(Cursor cursor) {
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			this.tasks.add(new Task().cursorToTask(cursor));
			cursor.moveToNext();
		}
	}
	
	public void printTaskList() {
		for(int i = 0; i < this.tasks.size(); i++) {
			this.tasks.get(i).printTask();
		}
	}
}
