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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import at.lvmaster3000.R;
import at.lvmaster3000.database.IDBlogic;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Task;
import at.lvmaster3000.gui.interfaces.IUpdateDBObject;

public class TaskDetailsFragment extends UIFragmentBase{

	private Context context;
	private IDBlogic dbLogic;

	private EditText taskTitle;
	private EditText taskComment;
	private DatePicker datePicker;
	private Boolean initDone;
	
	private Task task;
	
	private IUpdateDBObject updateTaskListener;
	
	public static TaskDetailsFragment newInstance(Task task, Context context, IDBlogic dbLogic) 
	{
		TaskDetailsFragment base = new TaskDetailsFragment();
		
		base.context = context;		
		base.dbLogic = dbLogic;
		base.task = task;

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
			updateTaskListener = (IUpdateDBObject) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_task_details, container, false);
		taskTitle = (EditText) view.findViewById(R.id.detail_task_title);
		taskComment = (EditText) view.findViewById(R.id.detail_task_comment);
		datePicker = (DatePicker) view.findViewById(R.id.detail_task_date);
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
		taskTitle.setText(task.getTitle()); 
		taskComment.setText(task.getComment());
		datePicker.getCalendarView().setDate(task.getDate().getTimestamp()*1000);
	}
	
	private void updateTask() {
		if(initDone)
		{
			task.setTitle((taskTitle.getText().toString()));
			task.setComment((taskComment.getText().toString()));
			task.getDate().setTimestamp(datePicker.getCalendarView().getDate()/1000);
			updateTaskListener.updateTask(task);
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
	    inflater.inflate(R.menu.tasks_fragment_details, menu);
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {	
		menu.clear();
		MenuInflater inflater = getActivity().getMenuInflater();
	    inflater.inflate(R.menu.tasks_fragment_details, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_save:
			updateTask();
			task.printTask();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
