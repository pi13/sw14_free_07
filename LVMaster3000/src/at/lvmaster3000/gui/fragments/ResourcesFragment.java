package at.lvmaster3000.gui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import at.lvmaster3000.R;
import at.lvmaster3000.database.IDBlogic;
import at.lvmaster3000.gui.adapters.ResourceListAdapter;

public class ResourcesFragment extends UIFragmentBase implements OnItemClickListener {
	
	private IDBlogic idLogic;
	
	public ResourcesFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		idLogic = new IDBlogic(getActivity().getApplicationContext());
		final ListView list = (ListView) inflater.inflate(R.layout.fragment_list_layout, container, false);
        attachAdapter(list);
        //list.setOnItemClickListener(this);
        return list;
    }
	
	private void attachAdapter(ListView list)
	{
		ResourceListAdapter resourceLA = new ResourceListAdapter(list.getContext().getApplicationContext(), 
																	idLogic.getResources(0).getResource());
		list.setAdapter(resourceLA);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
}
