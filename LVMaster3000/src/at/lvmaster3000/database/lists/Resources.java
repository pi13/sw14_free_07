package at.lvmaster3000.database.lists;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import at.lvmaster3000.database.objects.Resource;


public class Resources {
	private List<Resource> resources;
	
	public Resources() {
		this.resources = new ArrayList<Resource>();
	}
	
	public void addResource(Resource resource) {
		this.resources.add(resource);		
	}
	
	public List<Resource> getResources() {
		return this.resources;
	}
	
	public void cursorToResourceList(Cursor cursor) {
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			this.resources.add(new Resource().cursorToResource(cursor));
			cursor.moveToNext();
		}
	}
	
	public void printResourceList() {
		for(int i = 0; i < this.resources.size(); i++) {
			this.resources.get(i).printResource();
		}
	}
}
