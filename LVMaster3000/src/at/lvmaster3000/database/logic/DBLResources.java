package at.lvmaster3000.database.logic;

import android.content.Context;
import android.database.Cursor;
import android.test.RenamingDelegatingContext;
import android.util.Log;
import at.lvmaster3000.database.helper.HLPExams;
import at.lvmaster3000.database.helper.HLPResources;
import at.lvmaster3000.database.lists.Exams;
import at.lvmaster3000.database.lists.Resources;
import at.lvmaster3000.database.objects.Resource;
import at.lvmaster3000.settings.DBsettings;

public class DBLResources {

	private HLPResources hlpResources;
	
	public DBLResources(Context context) {
		this.hlpResources = new HLPResources(context);
	}

	public long addResource(Resource resource) {
		hlpResources.openCon();
		long id = this.hlpResources.addResource(resource.getTitle());
		hlpResources.closeCon();
		return id;
	}
	
	public int deleteResource(long resourceID){
		this.hlpResources.openCon();
		int res = this.hlpResources.deleteResource(resourceID);
		this.hlpResources.closeCon();
		return res;
	}

	public Resources getResources(int limit) {
		Resources resources = new Resources();
		
		String query = "SELECT * FROM " + HLPResources.TABLE_NAME;
		
		if(limit > 0) {
			query += " LIMIT " + limit;
		}
		
        Cursor cursor = hlpResources.openCon().rawQuery(query, null);
        if(cursor != null) {                	
        	resources.cursorToResourceList(cursor);        	
        } else {
        	Log.w(DBsettings.LOG_TAG_RESOURCES, "Cursor is NULL!!");        	
        }
        hlpResources.closeCon();
		
		return resources;	
	}

}
