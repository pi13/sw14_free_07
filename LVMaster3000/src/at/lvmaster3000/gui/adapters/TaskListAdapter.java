package at.lvmaster3000.gui.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import at.lvmaster3000.R;
import at.lvmaster3000.database.objects.Task;
import at.lvmaster3000.gui.fragments.TasksFragment;

public class TaskListAdapter extends BaseAdapter implements OnClickListener{

	private Context context;
	private List<Task> tasks;
	private TasksFragment taskFragment;
	
	public TaskListAdapter(Context context, List<Task> tasks, TasksFragment taskFrag)
	{
		this.context = context;
		this.tasks = tasks;
		this.taskFragment = taskFrag;
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
		
		ImageButton imgBtn = (ImageButton)convertView.findViewById(R.id.delete_list_item_btn);
		imgBtn.setTag(position);
		imgBtn.setOnClickListener(this);
		
		return convertView;
	}

	@Override
	public void onClick(View v) {
		taskFragment.onClick(v);
		
	}

}
