package at.lvmaster3000.gui.fragments;

import android.content.Context;
import at.lvmaster3000.database.IDBlogic;
import at.lvmaster3000.gui.adapters.TaskListAdapter;

public class TestFragment extends UIFragmentBase {

	private Context context;
	private IDBlogic dbLogic;
	
	public static TestFragment newInstance(Context context, IDBlogic dbLogic) 
	{
		TestFragment base = new TestFragment();
		base.context = context;		
		base.dbLogic = dbLogic;

		return base;
	}
}
