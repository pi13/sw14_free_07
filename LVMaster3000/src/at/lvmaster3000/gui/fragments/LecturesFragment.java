package at.lvmaster3000.gui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import at.lvmaster3000.R;
import at.lvmaster3000.database.IDBlogic;
import at.lvmaster3000.gui.adapters.LectureListAdapter;

public class LecturesFragment extends UIFragmentBase implements OnItemClickListener{
	
	private IDBlogic dbLogic;
	
	public LecturesFragment() {
		
	}
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
		this.dbLogic = new IDBlogic(this.getActivity().getApplicationContext());
        ListView list = (ListView) inflater.inflate(R.layout.fragment_list_layout, container, false);
        attachAdapter(list);
        list.setOnItemClickListener(this);
        return list;
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
		switchToFragemnt(new SingleLectureFragment());
	}
	
	private void attachAdapter(ListView list)
	{
		LectureListAdapter lectureLA = new LectureListAdapter(list.getContext().getApplicationContext(), this.dbLogic.getLectures(0).getLectures());
		list.setAdapter(lectureLA);
	}
}
