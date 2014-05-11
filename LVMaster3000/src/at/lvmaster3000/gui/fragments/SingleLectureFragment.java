package at.lvmaster3000.gui.fragments;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import at.lvmaster3000.R;
import at.lvmaster3000.gui.Group;
import at.lvmaster3000.gui.adapters.GroupExpandableListAdapter;

public class SingleLectureFragment extends Fragment {

	private SparseArray<Group> groups;
	
	public SingleLectureFragment() {
		this.groups = new SparseArray<Group>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_lecture, container,
				false);
		return rootView;
	}
	
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
		CreateDummyData();
		ExpandableListView listview = (ExpandableListView)getView().findViewById(R.id.lecture_items);
		GroupExpandableListAdapter adapter = new GroupExpandableListAdapter(this, this.groups);
		listview.setAdapter(adapter);
    }
	
	
	private void CreateDummyData(){
		Group test = new Group("Tasks");
		test.setChildren(new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.dummy_items))));
		this.groups.append(0, test);
		
		test = new Group("Exams");
		test.setChildren(new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.dummy_items))));
		this.groups.append(1, test);
		
		test = new Group("Resources");
		test.setChildren(new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.dummy_items))));
		this.groups.append(2, test);
	}
}
