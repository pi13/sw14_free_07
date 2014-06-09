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
import at.lvmaster3000.gui.adapters.LectureListAdapter;

public class HomeFragment extends UIFragmentBase {

	private Context context;
	private IDBlogic dbLogic;
	private ListView dates;
	private ListAdapter adapter;
	
	public static HomeFragment newInstance(Context context, IDBlogic dbLogic) 
	{
		HomeFragment base = new HomeFragment();
		base.context = context;		
		base.dbLogic = dbLogic;

		return base;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        datePicker  = (DatePicker) rootView.findViewById(R.id.home_dates);
        
        //Calendar cal = Calendar.getInstance();
        CalendarView cv = datePicker.getCalendarView();
        
        for (Date date : dbLogic.getDates(15).getDates()) 
        {
        	cv.setDate(date.getTimestamp()*1000);
		}
        return rootView;
    }
	

}
