package at.lvmaster3000.gui.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import at.lvmaster3000.R;
import at.lvmaster3000.database.objects.Task;

public class TaskListAdapter extends BaseAdapter {

	private Context context;
	private List<Task> tasks;
	
	public TaskListAdapter(Context context, List<Task> tasks)
	{
		this.context = context;
		this.tasks = tasks;
	}
	
	@Override
	public int getCount() {
		return tasks.size();
	}

	@Override
	public Object getItem(int position) {
		return tasks.get(position);
	}

	@Override
	public long getItemId(int position) {
		return tasks.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null)
		{
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.single_list_item, null);
		}
		
		TextView title = (TextView)convertView.findViewById(R.id.list_item_label);
		title.setText(tasks.get(position).getTitle());
		
		return convertView;
	}

}
