package at.lvmaster3000.gui.adapters;

import android.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import at.lvmaster3000.R;
import at.lvmaster3000.database.objects.Exam;
import at.lvmaster3000.gui.ExamGroup;
import at.lvmaster3000.gui.fragments.LectureDetailsFragment;
import at.lvmaster3000.gui.fragments.LecturesFragment;

public class ExamExpandableListAdapter extends BaseExpandableListAdapter implements OnClickListener{

	private final SparseArray<ExamGroup> groups;
	private LayoutInflater inflater;
	private LectureDetailsFragment fragment;
	
	public ExamExpandableListAdapter(LectureDetailsFragment frag, SparseArray<ExamGroup> groups)
	{
		this.fragment = frag;
		this.groups = groups;
		this.inflater = frag.getActivity().getLayoutInflater();
	}
	
	@Override
	public int getGroupCount() {
		return this.groups.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this.groups.get(groupPosition).getChildrenCount();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this.groups.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return this.groups.get(groupPosition).getChild(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
	    if (convertView == null) {
	        convertView = inflater.inflate(R.layout.exp_list_row_group, null);
	      }
	      ExamGroup group = (ExamGroup) getGroup(groupPosition);
	      ((CheckedTextView) convertView).setText(group.getGroupName());
	      ((CheckedTextView) convertView).setChecked(isExpanded);
	      return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
		Exam child = (Exam) getChild(groupPosition, childPosition);
		TextView text = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.single_list_item, null);
		}
		text = (TextView) convertView.findViewById(R.id.list_item_label);
		text.setClickable(true);
		text.setOnClickListener(this);
		text.setTag(child);
		text.setText(child.getTitle());

		ImageButton imgBtn = (ImageButton)convertView.findViewById(R.id.delete_list_item_btn);
		imgBtn.setTag(child);
		imgBtn.setOnClickListener(this);
				
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.list_item_label:
			fragment.getExpandableItemListener().onExpandableExamSelected((Exam)v.getTag());
			break;
		case R.id.delete_list_item_btn:
			fragment.getDeleteListener().DeleteItem((Exam)v.getTag());
			break;
		
		}		
	}

}
