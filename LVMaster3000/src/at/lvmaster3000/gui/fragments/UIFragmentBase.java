package at.lvmaster3000.gui.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ListView;
import at.lvmaster3000.R;

public class UIFragmentBase extends Fragment {
	
	protected void switchToFragemnt(Fragment fragment, String fragmentTag)
	{
		if (fragment != null) {
			FragmentManager fragManager = getFragmentManager();
			FragmentTransaction fragTrans =  fragManager.beginTransaction();
			fragTrans.replace(R.id.frame_container,fragment,  fragmentTag);
			fragTrans.addToBackStack(null);
			fragTrans.commit();
		}
		else
		{
			Log.e("UIFragmentBase", "Error in switching to fragment: " + fragment.getId());
		}
	}
	
	protected void attachAdapter(ListView list, BaseAdapter adapter)
	{
		if(list != null && adapter != null)
		{
			list.setAdapter(adapter);
		}
		else
		{
			if(list == null)
			{
				Log.e("UIFragmentBase","List cannot be null!");
			}
			
			if(adapter == null)
			{
				Log.e("UIFragmentBase","ListAdapter cannot be null!");
			}
			
		}
	}

}
