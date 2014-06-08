package at.lvmaster3000.gui;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import at.lvmaster3000.database.objects.Task;

public class TaskGroup {
	public String groupName;
	private List<Task> children = new ArrayList<Task>();
	private String log_tag = "GROUP_ITEM";

	public TaskGroup(String string) {
		if (string == null) {
			this.groupName = "Unknown";
			Log.w(log_tag, "The provided group name was null");
		} else {
			this.groupName = string;
			Log.w(log_tag, "Added group item: " + string);
		}
	}
	
	public int getChildrenCount(){
		return this.children.size();
	}
	
	public String getGroupName(){
		return this.groupName;
	}
	
	public Task getChild(int position){
		return this.children.get(position);
	}
	
	public void setChildren(List<Task> children){
		this.children = children;
	}
}
