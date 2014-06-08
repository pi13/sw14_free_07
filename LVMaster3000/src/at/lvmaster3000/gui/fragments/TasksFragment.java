package at.lvmaster3000.gui.fragments;

import java.util.List;

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
import at.lvmaster3000.database.objects.Task;
import at.lvmaster3000.gui.adapters.TaskListAdapter;

public class TasksFragment extends UIFragmentBase implements OnItemClickListener, OnClickListener{
	
	
	private TaskListAdapter adapter;
	private ListView list;
	private List<Task> tasks;
	private Context context;
	private IDBlogic dbLogic;
	
	public static TasksFragment newInstance(Context context, IDBlogic dbLogic) 
	{
		TasksFragment base = new TasksFragment();
		base.context = context;		
		base.dbLogic = dbLogic;
		base.tasks = dbLogic.getTasks(0).getTasks();
		base.adapter = new TaskListAdapter(context, base.tasks);


		return base;
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
		}
	}
	
	public void updateTaskList(Task task)
	{
		tasks.add(task);
		dbLogic.addTask(task);
		adapter.notifyDataSetChanged();
	}
	
	private void showDialog() {
	    DialogFragment newFragment = AddTaskFragment.newInstance(context);
	    newFragment.show(getFragmentManager(), "add_task_dialog");
	}
	
}
