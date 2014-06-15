package at.lvmaster3000.gui.adapters;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import at.lvmaster3000.R;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.gui.fragments.TasksFragment;

public class HomeDateListAdapter extends BaseAdapter{

	private Context context;
	private List<Date> dates;
	private TasksFragment taskFragment;
	
	public HomeDateListAdapter(Context context, List<Date> dates)
	{
		this.context = context;
		this.dates = dates;
	}
	
	@Override
	public int getCount() {
		return dates.size();
	}

	@Override
	public Object getItem(int position) {
		return dates.get(position);
	}

	@Override
	public long getItemId(int position) {
		return dates.get(position).getID();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null)
		{
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.date_list_item, null);
		}
		
		TextView title = (TextView)convertView.findViewById(R.id.date_item_label);
	    Calendar cal = Calendar.getInstance();
	    
//	    cal.setTime(new java.util.Date(dates.get(position).getTimestamp()*1000));
//		title.setText(cal.get(Calendar.DAY_OF_MONTH) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.YEAR));
	    
	    java.util.Date date = new java.util.Date();
		date.setTime((long)dates.get(position).getTimestamp() * 1000);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");		
		title.setText(sdf.format(date));
				
		return convertView;
	}
}
