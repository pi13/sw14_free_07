package at.lvmaster3000.gui.fragments;

import android.app.Fragment;
import android.content.Context;
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
	
	private IDBlogic dbLogic;
	private Context context;
	
	public ResourcesFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		this.context = this.getActivity().getApplicationContext();
		this.dbLogic = new IDBlogic(this.context);
		
		ListView list = (ListView) inflater.inflate(R.layout.fragment_list_layout, container, false);
		ResourceListAdapter adapter = new ResourceListAdapter(this.context, dbLogic.getResources(0).getResource());
        attachAdapter(list, adapter);

        return list;
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
}
