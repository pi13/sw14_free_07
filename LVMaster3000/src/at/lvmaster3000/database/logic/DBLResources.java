package at.lvmaster3000.database.logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import at.lvmaster3000.database.helper.HLPResources;
import at.lvmaster3000.database.lists.Resources;
import at.lvmaster3000.database.objects.Resource;
import at.lvmaster3000.settings.DBsettings;

public class DBLResources {

	private HLPResources hlpResources;
	
	/**
	 * 
	 * @param context
	 */
	public DBLResources(Context context) {
		this.hlpResources = new HLPResources(context);
	}

	public void resetTable() {
		this.hlpResources.openCon();
		this.hlpResources.resetTable();
		this.hlpResources.closeCon();
	}
	
	/**
	 * 
	 * @param title
	 * @return
	 */
	public long addResource(String title) {
		hlpResources.openCon();
		long rid = this.hlpResources.addResource(title);
		hlpResources.closeCon();
		return rid;
	}
	
	/**
	 * 
	 * @param resource
	 * @return
	 */
	public long addResource(Resource resource) {
		long rid = this.addResource(resource.getTitle());
		resource.setId(rid);
		
		return rid;
	}
	
	/**
	 * 
	 * @param resourceId
	 * @return
	 */
	public int deleteResource(long resourceId) {
		this.hlpResources.openCon();
		int res = this.hlpResources.deleteResource(resourceId);
		this.hlpResources.closeCon();
		
		return res;
	}
	
	/**
	 * 
	 * @param resource
	 * @return
	 */
	public int deleteResource(Resource resource){
		return this.deleteResource(resource.getId());
	}

	/**
	 * 
	 * @param limit
	 * @return
	 */
	public Resources getResources(int limit) {
		Resources resources = new Resources();
		
		String query = "SELECT * FROM " + HLPResources.TABLE_NAME;
		
		if(limit > 0) {
			query += " LIMIT " + limit;
		}
		
        Cursor cursor = this.hlpResources.openCon().rawQuery(query, null);
        if(cursor != null) { 
        	if(cursor.getCount() < 1) {
        		this.hlpResources.closeCon();
        		return null;
        	}
        	
        	resources.cursorToResourceList(cursor);        	
        } else {
        	Log.w(DBsettings.LOG_TAG_RESOURCES, "Cursor is NULL!!");        	
        }
        this.hlpResources.closeCon();
		
		return resources;	
	}
	
	/**
	 * 
	 * @param resource
	 * @return
	 */
	public int updateResource(Resource resource) {
		ContentValues values = new ContentValues();
		
		if(!resource.getTitle().isEmpty()) {
			values.put(HLPResources.COL_TITLE, resource.getTitle());
		}
		
		int ret = this.hlpResources.openCon().update(HLPResources.TABLE_NAME, values, "_id = " + resource.getId(), null);
		
		Log.i(DBsettings.LOG_TAG_RESOURCES, "Update res.: " + ret);
		
		return ret;
	}

}
