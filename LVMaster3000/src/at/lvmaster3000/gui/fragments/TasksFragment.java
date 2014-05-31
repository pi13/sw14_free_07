package at.lvmaster3000.gui.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import at.lvmaster3000.R;
import at.lvmaster3000.database.IDBlogic;
import at.lvmaster3000.gui.adapters.TaskListAdapter;

public class TasksFragment extends UIFragmentBase{
	private IDBlogic idbLogic;
	private Context context;
	public TasksFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
		this.context = getActivity().getApplicationContext();
		idbLogic = new IDBlogic(this.context);
		final ListView list = (ListView) inflater.inflate(R.layout.fragment_list_layout, container, false);
        attachAdapter(list);
        //list.setOnItemClickListener(this);
        return list;
    }
	
	private void attachAdapter(ListView list)
	{
		TaskListAdapter taskLA = new TaskListAdapter(this.context, idbLogic.getTasks(0).getTasks());												  
		list.setAdapter(taskLA);
	}
}
