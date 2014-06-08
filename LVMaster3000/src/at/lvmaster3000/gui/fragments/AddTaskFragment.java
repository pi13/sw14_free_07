package at.lvmaster3000.gui.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import at.lvmaster3000.R;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Task;
import at.lvmaster3000.gui.interfaces.IDialogListener;

public class AddTaskFragment extends DialogFragment implements OnClickListener {

	private IDialogListener dialogListener;

	private Context context;
	private Task task;
	private EditText taskTitle;
	private EditText taskComment;
	private EditText taskLocation;


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

		Button ok = (Button) view.findViewById(R.id.ok_btn);
		Button cancel = (Button) view.findViewById(R.id.cancel_btn);
		ok.setOnClickListener(this);
		cancel.setOnClickListener(this);

		taskTitle = (EditText) view.findViewById(R.id.add_task_title);
		taskComment = (EditText) view.findViewById(R.id.add_task_comment);
		taskLocation = (EditText) view.findViewById(R.id.add_task_location);

		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ok_btn:
			task = new Task(0, taskTitle.getText().toString(), taskComment.getText().toString(), new Date(0, new java.util.Date().getTime(),
																taskLocation.getText().toString(), "", taskComment.getText().toString()));

			dialogListener.onTaskAdd(this);
			break;

		case R.id.cancel_btn:
			dialogListener.onDialogNegativeClick(this);
			break;
		}
	}
}