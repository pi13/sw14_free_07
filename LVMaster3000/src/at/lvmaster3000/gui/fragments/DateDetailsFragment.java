package at.lvmaster3000.gui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import at.lvmaster3000.R;
import at.lvmaster3000.database.IDBlogic;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.gui.interfaces.IUpdateDBObject;

public class DateDetailsFragment extends UIFragmentBase{
	private Context context;
	private IDBlogic dbLogic;

	private DatePicker datePicker;
	
	private Date date;
	private Boolean initDone;
	
	private IUpdateDBObject updateDateListener;
	
	public static DateDetailsFragment newInstance(Date date, Context context, IDBlogic dbLogic) 
	{
		DateDetailsFragment base = new DateDetailsFragment();
	
		base.context = context;		
		base.dbLogic = dbLogic;
		base.date = date;

		return base;
	}
	
	// Override the Fragment.onAttach() method to instantiate the
	// NoticeDialogListener
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verify that the host activity implements the callback interface
		try {
			// Instantiate the NoticeDialogListener so we can send events to the
			// host
			updateDateListener = (IUpdateDBObject) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_date_details, container, false);
		datePicker = (DatePicker) view.findViewById(R.id.detail_date_date);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		updateFileds();
		initDone = true;
	}
	
	private void updateFileds()
	{
		datePicker.getCalendarView().setDate(date.getTimestamp()*1000); 
	}
	
	private void updateResource() {
		if(initDone)
		{
			date.setTimestamp(datePicker.getCalendarView().getDate()/1000);
			updateDateListener.updateDate(date);
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		updateResource();
	}
}
