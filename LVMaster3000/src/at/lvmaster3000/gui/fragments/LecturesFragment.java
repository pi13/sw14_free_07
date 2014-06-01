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

public class LecturesFragment extends UIFragmentBase implements OnItemClickListener {

	private LectureListAdapter adapter;
	private ListView list;
	private IDBlogic dbLogic;
	private Context context;
	
	public static LecturesFragment newInstance(Context context, IDBlogic dbLogic, LectureListAdapter adapter, Bundle fragment_args) 
	{
		LecturesFragment base = new LecturesFragment();

		base.context = context;
		base.dbLogic = dbLogic;
		base.adapter = adapter;
		
		base.setArguments(fragment_args);

		return base;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root =  inflater.inflate(R.layout.fragment_list_layout, container, false);
		
		list = (ListView)root.findViewById(R.id.fragment_list);
		attachAdapter(list, adapter);
		list.setOnItemClickListener(this);
		return root;
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switchToFragemnt(LectureDetailsFragment.newInstance((Lecture) adapter.getItem(position)));
	}
}
