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
import at.lvmaster3000.database.objects.Resource;
import at.lvmaster3000.gui.fragments.ResourcesFragment;

public class ResourceListAdapter extends BaseAdapter implements OnClickListener {

	private Context context;
	private List<Resource> resources;
	private ResourcesFragment resFragment;
	
	public ResourceListAdapter(Context context, List<Resource> resources, ResourcesFragment resFrag)
	{
		this.context = context;
		this.resources = resources;
		this.resFragment = resFrag;
	}
	
	@Override
	public int getCount() {
		return resources.size();
	}

	@Override
	public Object getItem(int position) {
		return resources.get(position);
	}

	@Override
	public long getItemId(int position) {
		return resources.get(position).getId();
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
		title.setText(resources.get(position).getTitle());
		
		ImageButton imgBtn = (ImageButton)convertView.findViewById(R.id.delete_list_item_btn);
		imgBtn.setTag(position);
		imgBtn.setOnClickListener(this);
		
		return convertView;
	}

	@Override
	public void onClick(View v) {
		resFragment.onClick(v);
		
	}

}
