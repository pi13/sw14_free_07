package at.lvmaster3000.gui.fragments;

import java.util.Calendar;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TimePicker;
import at.lvmaster3000.R;
import at.lvmaster3000.database.IDBlogic;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Lecture;
import at.lvmaster3000.gui.interfaces.IDialogListener;

public class AddDateFragment extends DialogFragment implements OnClickListener {
	private IDialogListener dialogListener;

	private Context context;
	private Date date;
	private DatePicker datePicker;
	private TimePicker timePicker;

	private long lectureId;
	private IDBlogic dbLogic;

	public static AddDateFragment newInstance(Context context) {
		AddDateFragment dialog = new AddDateFragment();
		dialog.context = context;
		return dialog;
	}

	public Date getResource() {
		return date;
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
			dialogListener = (IDialogListener) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.add_date_fragment, container);

		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		
		Button ok = (Button) view.findViewById(R.id.ok_btn);
		Button cancel = (Button) view.findViewById(R.id.cancel_btn);
		ok.setOnClickListener(this);
		cancel.setOnClickListener(this);
		
		datePicker = (DatePicker) view.findViewById(R.id.add_date_date);
		timePicker = (TimePicker) view.findViewById(R.id.add_date_time);

		Bundle bundle = getArguments();
		if (bundle != null) {
			this.lectureId = bundle.getLong("lectureId");
			this.dbLogic = new IDBlogic(context);
		}

		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ok_btn:
			Calendar cal = Calendar.getInstance();
			cal.set(datePicker.getYear(), datePicker.getMonth(),
					datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
			date = new Date(0, cal.getTimeInMillis() / 1000, "", "", "");

			Lecture lecture = new Lecture();
			lecture.setID(this.lectureId);
			this.dbLogic.addDateToLecture(date, lecture);
			dialogListener.onDateAddToLecture(this);

			break;

		case R.id.cancel_btn:
			dialogListener.onDialogNegativeClick(this);
			break;
		}
	}
}
