package at.lvmaster3000.gui.fragments;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;
import at.lvmaster3000.R;
import at.lvmaster3000.database.IDBlogic;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.gui.interfaces.IUpdateDBObject;

public class DateDetailsFragment extends UIFragmentBase{
	private Context context;
	private IDBlogic dbLogic;

	private DatePicker datePicker;
	private TimePicker timePicker;
	
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
		timePicker = (TimePicker) view.findViewById(R.id.detail_date_time);

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
		long dateMili = date.getTimestamp()*1000;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new java.util.Date(dateMili));
		
		timePicker.setCurrentHour(cal.get(Calendar.HOUR));
		timePicker.setCurrentMinute(cal.get(Calendar.MINUTE));
		datePicker.getCalendarView().setDate(dateMili); 
	}
	
	private void updateDate() {
		if(initDone)
		{
			Calendar cal = Calendar.getInstance();
			cal.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
			date.setTimestamp(cal.getTimeInMillis() / 1000);
			updateDateListener.updateDate(date);
		}
		
		Toast.makeText(context, "Saved :)", Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		getActivity().invalidateOptionsMenu();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.date_fragment_details, menu);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.date_fragment_details, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_save:
			updateDate();

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
