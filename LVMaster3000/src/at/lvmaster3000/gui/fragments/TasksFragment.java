package at.lvmaster3000.gui.fragments;

import java.util.List;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import at.lvmaster3000.R;
import at.lvmaster3000.database.IDBlogic;
import at.lvmaster3000.database.objects.Exam;
import at.lvmaster3000.database.objects.Task;
import at.lvmaster3000.gui.adapters.TaskListAdapter;
import at.lvmaster3000.interfaces.IDeleteItems;

public class TasksFragment extends UIFragmentBase implements OnItemClickListener, OnClickListener{
	
	
	private TaskListAdapter adapter;
	private ListView list;
	private List<Task> tasks;
	private Context context;
	private IDBlogic dbLogic;
	private IDeleteItems deleteTaskListener;
	
	public static TasksFragment newInstance(Context context, IDBlogic dbLogic) 
	{
		TasksFragment base = new TasksFragment();
		base.context = context;		
		base.dbLogic = dbLogic;
		base.tasks = dbLogic.getTasks(0).getTasks();
		base.adapter = new TaskListAdapter(context, base.tasks, base);


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
			deleteTaskListener = (IDeleteItems) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root =  inflater.inflate(R.layout.fragment_list_layout, container, false);
		
		list = (ListView)root.findViewById(R.id.fragment_list);
		attachAdapter(list, adapter);
		list.setOnItemClickListener(this);
		Button addButton =(Button) root.findViewById(R.id.add_btn);
		addButton.setOnClickListener(this);
		return root;
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//switchToFragemnt(LectureDetailsFragment.newInstance((Lecture) adapter.getItem(position), context, dbLogic));
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_btn:
			showDialog();
			break;
		case R.id.delete_list_item_btn:
			deleteTaskListener.DeleteTaskItem((Task)adapter.getItem((Integer)v.getTag()));		
			break;
		}
	}
	
	public void updateTaskList(Task task)
	{
		tasks.add(task);
		dbLogic.addTask(task);
		adapter.notifyDataSetChanged();
	}
	
	public void deleteTask(Task task)
	{
		tasks.remove(task);
		dbLogic.deleteTask(task);
		adapter.notifyDataSetChanged();
	}
	
	private void showDialog() {
	    DialogFragment newFragment = AddTaskFragment.newInstance(context);
	    newFragment.show(getFragmentManager(), "add_task_dialog");
	}
	
}
