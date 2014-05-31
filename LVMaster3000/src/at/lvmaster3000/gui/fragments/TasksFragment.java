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
	
	private IDBlogic dbLogic;
	private Context context;
	
	public TasksFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
		this.context = getActivity().getApplicationContext();
		dbLogic = new IDBlogic(this.context);
		
		final ListView list = (ListView) inflater.inflate(R.layout.fragment_list_layout, container, false);
		TaskListAdapter adapter = new TaskListAdapter(this.context, dbLogic.getTasks(0).getTasks());
        attachAdapter(list, adapter);

        return list;
    }
	
}
