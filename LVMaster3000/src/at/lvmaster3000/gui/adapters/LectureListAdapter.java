package at.lvmaster3000.gui.adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import at.lvmaster3000.R;
import at.lvmaster3000.database.objects.Lecture;

public class LectureListAdapter extends BaseAdapter {

	private Context context;
	private List<Lecture> lectures;
	
	public LectureListAdapter(Context context, List<Lecture> lectures)
	{
		this.context = context;
		this.lectures = lectures;
	}
	
	@Override
	public int getCount() {
		return lectures.size();
	}

	@Override
	public Object getItem(int position) {
		return lectures.get(position);
	}

	@Override
	public long getItemId(int position) {
		return lectures.get(position).getID();
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
		title.setText(lectures.get(position).getName());
		
		return convertView;
	}

}
