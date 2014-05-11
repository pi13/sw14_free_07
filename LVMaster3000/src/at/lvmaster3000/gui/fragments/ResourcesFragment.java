package at.lvmaster3000.gui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import at.lvmaster3000.R;

public class ResourcesFragment extends Fragment {
	public ResourcesFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		final ListView list = (ListView) inflater.inflate(R.layout.fragment_list_layout, container, false);
        attachAdapter(list);
        //list.setOnItemClickListener(this);
        return list;
    }
	
	private void attachAdapter(ListView list)
	{
		ArrayAdapter<String> items = new ArrayAdapter<String>(list.getContext().getApplicationContext(), 
														      R.layout.single_list_item, R.id.list_item_label, 
														      getResources().getStringArray(R.array.dummy_items));
		list.setAdapter(items);
	}
}
