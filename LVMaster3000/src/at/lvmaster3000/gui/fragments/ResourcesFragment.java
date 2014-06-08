package at.lvmaster3000.gui.fragments;

import java.util.List;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import at.lvmaster3000.R;
import at.lvmaster3000.database.IDBlogic;
import at.lvmaster3000.database.objects.Resource;
import at.lvmaster3000.gui.adapters.ResourceListAdapter;

public class ResourcesFragment extends UIFragmentBase implements OnItemClickListener, OnClickListener {
	
	private ResourceListAdapter adapter;
	private ListView list;
	private List<Resource> resources;
	private Context context;
	private IDBlogic dbLogic;
	
	public static ResourcesFragment newInstance(Context context, IDBlogic dbLogic) 
	{
		ResourcesFragment base = new ResourcesFragment();
		base.context = context;		
		base.dbLogic = dbLogic;
		base.resources = dbLogic.getResources(0).getResources();
		base.adapter = new ResourceListAdapter(context, base.resources);


		return base;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root =  inflater.inflate(R.layout.fragment_list_layout, container, false);
		
		list = (ListView)root.findViewById(R.id.fragment_list);
		attachAdapter(list, adapter);
		list.setOnItemClickListener(this);
		Button addButton =(Button) root.findViewById(R.id.add_btn);
		addButton.setOnClickListener(this);
		return root;
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//switchToFragemnt(LectureDetailsFragment.newInstance((Lecture) adapter.getItem(position), context, dbLogic));
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_btn:
			showDialog();
			break;
		}
	}
	
	public void updateResourceList(Resource resource)
	{
		resources.add(resource);
		dbLogic.addResource(resource);
		adapter.notifyDataSetChanged();
	}
	
	private void showDialog() {
	    DialogFragment newFragment = AddResourceFragment.newInstance(context);
	    newFragment.show(getFragmentManager(), "add_resource_dialog");
	}
}
