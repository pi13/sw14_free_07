package at.lvmaster3000.gui.fragments;

import java.util.Calendar;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import at.lvmaster3000.R;
import at.lvmaster3000.database.IDBlogic;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.gui.adapters.HomeDateListAdapter;
import at.lvmaster3000.gui.adapters.LectureListAdapter;

public class HomeFragment extends UIFragmentBase {

	private Context context;
	private IDBlogic dbLogic;
	private ListView dates;
	private HomeDateListAdapter adapter;
	
	public static HomeFragment newInstance(Context context, IDBlogic dbLogic) 
	{
		HomeFragment base = new HomeFragment();
		base.context = context;		
		base.dbLogic = dbLogic;
		base.adapter = new HomeDateListAdapter(base.context, base.dbLogic.getDates(10).getDates());
		return base;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        
        dates = (ListView)rootView.findViewById(R.id.home_dates);
        dates.setAdapter(adapter);

        return rootView;
    }
	

}
