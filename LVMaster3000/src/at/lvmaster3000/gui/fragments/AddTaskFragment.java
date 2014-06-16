package at.lvmaster3000.gui.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import at.lvmaster3000.R;
import at.lvmaster3000.database.IDBlogic;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Lecture;
import at.lvmaster3000.database.objects.Task;
import at.lvmaster3000.gui.interfaces.IDialogListener;

public class AddTaskFragment extends DialogFragment implements OnClickListener {

	private IDialogListener dialogListener;

	private Context context;
	private Task task;
	private EditText taskTitle;
	private EditText taskComment;
	private DatePicker datePicker;
	private CheckBox setDate;

	private long lectureId;	
	private IDBlogic dbLogic;

	public static AddTaskFragment newInstance(Context context) {
		AddTaskFragment dialog = new AddTaskFragment();
		dialog.context = context;
		
		return dialog;
	}

	public Task getTask() {
		return task;
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
		View view = inflater.inflate(R.layout.add_task_fragment, container);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		Button ok = (Button) view.findViewById(R.id.ok_btn);
		Button cancel = (Button) view.findViewById(R.id.cancel_btn);
		ok.setOnClickListener(this);
		cancel.setOnClickListener(this);

		taskTitle = (EditText) view.findViewById(R.id.add_task_title);
		taskComment = (EditText) view.findViewById(R.id.add_task_comment);
		datePicker = (DatePicker)view.findViewById(R.id.add_task_date);
		setDate = (CheckBox)view.findViewById(R.id.add_task_has_date);
		setDate.setOnClickListener(this);
		
		Bundle bundle = getArguments();
		if(bundle != null) {
			this.lectureId = bundle.getLong("lectureId");
			this.dbLogic = new IDBlogic(context);
		}

		return view;
	}
	
	private void onCheckboxClicked(View view) {
		boolean checked = ((CheckBox) view).isChecked();

		switch (view.getId()) {
		case R.id.add_task_has_date:
			if (checked) {
				datePicker.setVisibility(View.VISIBLE);
			} else {
				datePicker.setVisibility(View.GONE);
			}
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ok_btn:
			
			Date taskDate = null;
			
			if(setDate.isChecked())
			{
				taskDate = new Date(0, datePicker.getCalendarView().getDate()/1000, "", "", "");
			}
			
			task = new Task(0, taskTitle.getText().toString(), taskComment.getText().toString(), taskDate);
						
			if(this.lectureId > 0) {				
				Lecture lecture = new Lecture();
				lecture.setID(this.lectureId);				
				this.dbLogic.addTaskToLecture(task, lecture);
				dialogListener.onTaskAddToLecture(this);
			} else {
				dialogListener.onTaskAdd(this);
			}
			
			break;

		case R.id.cancel_btn:
			dialogListener.onDialogNegativeClick(this);
			break;
			
		case R.id.add_task_has_date:
			onCheckboxClicked(v);
			break;
		}
	}
}
