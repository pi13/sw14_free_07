package at.lvmaster3000.gui;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import at.lvmaster3000.database.objects.Exam;

public class ExamGroup {
	protected String groupName;
	private List<Exam> children = new ArrayList<Exam>();
	private String log_tag = "GROUP_ITEM";

	public ExamGroup(String string) {
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
	
	public Exam getChild(int position){
		return this.children.get(position);
	}
	
	public void setChildren(List<Exam> children){
		this.children = children;
	}
}
