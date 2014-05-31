package at.lvmaster3000.gui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import at.lvmaster3000.R;
import at.lvmaster3000.database.IDBlogic;
import at.lvmaster3000.database.objects.Lecture;
import at.lvmaster3000.gui.adapters.LectureListAdapter;

public class LecturesFragment extends UIFragmentBase implements OnItemClickListener{
	
	private IDBlogic dbLogic;
	private Context context;
	private LectureListAdapter adapter;
	
	public LecturesFragment() {
		
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
		this.context = this.getActivity().getApplicationContext();
		this.dbLogic = new IDBlogic(this.context);
        
		ListView list = (ListView) inflater.inflate(R.layout.fragment_list_layout, container, false);
        adapter = new LectureListAdapter(this.context, this.dbLogic.getLectures(0).getLectures());
        attachAdapter(list, adapter);
        
        list.setOnItemClickListener(this);
        
        return list;
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
		switchToFragemnt(LectureDetailsFragment.newInstance((Lecture)adapter.getItem(position)));
	}
}
