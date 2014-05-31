package at.lvmaster3000.gui.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;
import at.lvmaster3000.R;

public class UIFragmentBase extends Fragment {
	
	protected void switchToFragemnt(Fragment fragment)
	{
		if (fragment != null) {
			FragmentManager fragManager = getFragmentManager();
			FragmentTransaction fragTrans =  fragManager.beginTransaction();
			fragTrans.replace(R.id.frame_container, fragment);
			fragTrans.addToBackStack(null);
			fragTrans.commit();
		}
		else
		{
			Log.e("UIFragmentBase", "Error in switching to fragment: " + fragment.getId());
		}
	}

}
