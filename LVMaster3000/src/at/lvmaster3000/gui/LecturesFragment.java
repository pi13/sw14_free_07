package at.lvmaster3000.gui;


import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import at.lvmaster3000.R;

public class LecturesFragment extends ListFragment{
	
	public LecturesFragment() {
		// TODO Auto-generated constructor stub
	}
	
	  @Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    String[] values = getResources().getStringArray(R.array.dummy_items);
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
	        R.layout.single_list_item, R.id.label, values);
	    setListAdapter(adapter);
	  }

	  @Override
	  public void onListItemClick(ListView l, View v, int position, long id) {
	    // do something with the data
	  }
}
