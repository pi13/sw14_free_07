package at.lvmaster3000.gui;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class Group {

	public String groupName;
	private List<String> children = new ArrayList<String>();
	private String log_tag = "GROUP_ITEM";

	public Group(String string) {
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
	
	public String getChild(int position){
		return this.children.get(position);
	}
	
	public void setChildren(List<String> children){
		this.children = children;
	}
}
