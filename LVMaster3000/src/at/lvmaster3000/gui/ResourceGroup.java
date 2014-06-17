package at.lvmaster3000.gui;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import at.lvmaster3000.database.objects.Resource;

public class ResourceGroup {
	public String groupName;
	private List<Resource> children = new ArrayList<Resource>();
	private String log_tag = "GROUP_ITEM";

	public ResourceGroup(String string) {
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
	
	public Resource getChild(int position){
		return this.children.get(position);
	}
	
	public void setChildren(List<Resource> children){
		this.children = children;
	}
	
	public List<Resource> getChildren(){
		return this.children;
	}
}
