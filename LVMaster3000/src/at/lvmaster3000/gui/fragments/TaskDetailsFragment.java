package at.lvmaster3000.gui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import at.lvmaster3000.R;
import at.lvmaster3000.database.IDBlogic;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Task;
import at.lvmaster3000.gui.interfaces.IUpdateDBObject;

public class TaskDetailsFragment extends UIFragmentBase implements OnClickListener{

	private Context context;
	private IDBlogic dbLogic;

	private EditText taskTitle;
	private EditText taskComment;
	private DatePicker datePicker;
	private CheckBox setDate;
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
		setDate = (CheckBox)view.findViewById(R.id.detail_task_has_date);
		setDate.setOnClickListener(this);
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
		
		task.printTask();
		
		if(task.getDate() != null)
		{
			
			Log.w("TEST_", "id: " + task.getId());
			
			if(task.getDate().getID() > 0) {
				setDate.setChecked(true);
				datePicker.getCalendarView().setDate(task.getDate().getTimestamp()*1000);
				datePicker.setVisibility(View.VISIBLE);
			} else {
				setDate.setChecked(false);
			}
		} else {
			setDate.setChecked(false);
		}
		
	}
	
	private void updateTask() {
		if(initDone)
		{
			task.setTitle((taskTitle.getText().toString()));
			task.setComment((taskComment.getText().toString()));
						
			if(setDate.isChecked()){					
				if(task.getDate() != null) {
					task.getDate().setTimestamp(datePicker.getCalendarView().getDate()/1000);
					task.getDate().setType("");
					task.getDate().setLocation("");
					task.getDate().setComment("");
				} else {
					task.setDate(new Date(0, datePicker.getCalendarView().getDate()/1000, "", "", ""));
				}
			} else {
				if(task.getDate() != null) {
					if(task.getDate().getID() > 0) {
						dbLogic.deleteDate(task.getDate());
					}
				}
				
				task.setDate(null);				
			}
			
//			task.printTask();
			
			updateTaskListener.updateTask(task);
		}
		Toast.makeText(context, "Saved :)", Toast.LENGTH_LONG).show();

	}
	
	private void onCheckboxClicked(View view) {
		boolean checked = ((CheckBox) view).isChecked();

		switch (view.getId()) {
		case R.id.detail_task_has_date:
			if (checked) {
				datePicker.setVisibility(View.VISIBLE);
				if(task.getDate() != null){
					datePicker.getCalendarView().setDate(task.getDate().getTimestamp()*1000);
				}
			} else {
				datePicker.setVisibility(View.GONE);
			}
			break;
		}
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.detail_task_has_date:
			onCheckboxClicked(v);
			break;
		}
	}
}
