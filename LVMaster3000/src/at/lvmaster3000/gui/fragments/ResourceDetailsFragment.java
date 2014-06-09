package at.lvmaster3000.gui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import at.lvmaster3000.R;
import at.lvmaster3000.database.IDBlogic;
import at.lvmaster3000.database.objects.Resource;
import at.lvmaster3000.gui.interfaces.IUpdateDBObject;

public class ResourceDetailsFragment extends UIFragmentBase{

	private Context context;
	private IDBlogic dbLogic;

	private EditText resTitle;
	
	private Resource resource;
	private Boolean initDone;
	
	private IUpdateDBObject updateResourceListener;
	
	public static ResourceDetailsFragment newInstance(Resource resource, Context context, IDBlogic dbLogic) 
	{
		ResourceDetailsFragment base = new ResourceDetailsFragment();
	
		base.context = context;		
		base.dbLogic = dbLogic;
		base.resource = resource;

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
			updateResourceListener = (IUpdateDBObject) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_resource_details, container, false);
		resTitle = (EditText) view.findViewById(R.id.details_res_title);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		updateFileds();
		initDone = true;
	}
	
	private void updateFileds()
	{
		resTitle.setText(resource.getTitle()); 
	}
	
	private void updateResource() {
		if(initDone)
		{
			resource.setTitle((resTitle.getText().toString()));
			updateResourceListener.updateResource(resource);
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		updateResource();
	}


}
