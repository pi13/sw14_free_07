package at.lvmaster3000.gui.adapters;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import at.lvmaster3000.R;
import at.lvmaster3000.database.objects.Lecture;
import at.lvmaster3000.gui.fragments.LecturesFragment;
import at.lvmaster3000.gui.interfaces.IDialogListener;
import at.lvmaster3000.interfaces.IDeleteItems;

public class LectureListAdapter extends BaseAdapter implements OnClickListener{

	private Context context;
	private List<Lecture> lectures;
	private LecturesFragment lectFragment;
	
	public LectureListAdapter(Context context, List<Lecture> lectures, LecturesFragment lectFrag)
	{
		this.context = context;
		this.lectures = lectures;
		this.lectFragment = lectFrag;
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
		
		ImageButton imgBtn = (ImageButton)convertView.findViewById(R.id.delete_list_item_btn);
		imgBtn.setTag(position);
		imgBtn.setOnClickListener(this);
		
		return convertView;
	}

	@Override
	public void onClick(View v) {
		lectFragment.onClick(v);
	}
}
