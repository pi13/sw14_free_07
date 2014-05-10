package at.lvmaster3000.gui;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.LauncherActivity.ListItem;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import at.lvmaster3000.R;

public class LecturesFragment extends Fragment implements OnItemClickListener{
	
	public LecturesFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        final ListView list = (ListView) inflater.inflate(R.layout.fragment_list_layout, container, false);
        attachAdapter(list);
        list.setOnItemClickListener(this);
        return list;
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.frame_container, new SingleLectureFragment()).commit();
	}
	
	private void attachAdapter(ListView list)
	{
		ArrayAdapter<String> items = new ArrayAdapter<String>(list.getContext().getApplicationContext(), 
														      R.layout.single_list_item, R.id.list_item_label, 
														      getResources().getStringArray(R.array.dummy_items));
		list.setAdapter(items);
	}
}
