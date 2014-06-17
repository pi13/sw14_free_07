package at.lvmaster3000.gui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import at.lvmaster3000.R;
import at.lvmaster3000.database.IDBlogic;
import at.lvmaster3000.database.demodata.DDTestsetA;
import at.lvmaster3000.database.demodata.DDTestsetB;
import at.lvmaster3000.gui.adapters.NavDrawerListAdapter;

public class TestFragment extends UIFragmentBase {

	private Context context;
	private IDBlogic dbLogic;
	
	private Activity activity;
	
	public static TestFragment newInstance(Context context, IDBlogic dbLogic, NavDrawerListAdapter adapter, Activity activity) 
	{
		TestFragment base = new TestFragment();
		base.context = context;		
		base.dbLogic = dbLogic;
		base.activity = activity;
		
		DDTestsetB setB = new DDTestsetB(context);
		setB.fillDb();
		
//		DDTestsetA TestA = new DDTestsetA(context);
//		TestA.FillDb();
//		TestA.getStuffForLecture();
//		
//		Log.e("TEST_", "adapter count: " + adapter.getCount());
//		
//		NavDrawerItem item = (NavDrawerItem)adapter.getItem(1);
//		Log.e("TEST_", "title: " + item.getTitle());
		
//		item.setCount(111);
		
//		base.forceShowActionBarOverflowMenu();
		
		Toast.makeText(context, "Demodata loaded, have fun :)", Toast.LENGTH_LONG).show();
		
		return base; 
	}  
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();
    }
        
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {    	
    	return super.onCreateView(inflater, container, savedInstanceState);
    }
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {		
		menu.clear();
	    inflater.inflate(R.menu.actions, menu);
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {	
		menu.clear();
		MenuInflater inflater = activity.getMenuInflater();
	    inflater.inflate(R.menu.actions, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_settings:
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
