package at.lvmaster3000.gui.fragments;

import java.util.List;

import android.app.Activity;
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
import at.lvmaster3000.database.objects.Task;
import at.lvmaster3000.gui.adapters.ResourceListAdapter;
import at.lvmaster3000.interfaces.IDeleteItems;

public class ResourcesFragment extends UIFragmentBase implements OnItemClickListener, OnClickListener {
	
	private ResourceListAdapter adapter;
	private ListView list;
	private List<Resource> resources;
	private Context context;
	private IDBlogic dbLogic;
	private IDeleteItems deleteResourceListener;
	
	public static ResourcesFragment newInstance(Context context, IDBlogic dbLogic) 
	{
		ResourcesFragment base = new ResourcesFragment();
		base.context = context;		
		base.dbLogic = dbLogic;
		base.resources = dbLogic.getResources(0).getResources();
		base.adapter = new ResourceListAdapter(context, base.resources, base);


		return base;
	}
	
	
	// Override the Fragment.onAttach() method to instantiate the
	// NoticeDialogListener
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verify that the host activity implements the callback interface
		try {
			// Instantiate the NoticeDialogListener so we can send events to the
			// host
			deleteResourceListener = (IDeleteItems) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
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
		switchToFragemnt(ResourceDetailsFragment.newInstance((Resource) adapter.getItem(position), context, dbLogic), getResources().getString(R.string.res_details_frag));
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_btn:
			showDialog();
			break;
		case R.id.delete_list_item_btn:
			deleteResourceListener.DeleteResourceItem((Resource)adapter.getItem((Integer)v.getTag()));		
			break;
		}
	}
	
	public void updateResourceList(Resource resource)
	{
		resources.add(resource);
		dbLogic.addResource(resource);
		adapter.notifyDataSetChanged();
	}
	
	public void deleteTask(Resource resource)
	{
		resources.remove(resource);
		dbLogic.deleteResource(resource);
		adapter.notifyDataSetChanged();
	}
	
	private void showDialog() {
	    DialogFragment newFragment = AddResourceFragment.newInstance(context);
	    newFragment.show(getFragmentManager(), "add_resource_dialog");
	}
}
