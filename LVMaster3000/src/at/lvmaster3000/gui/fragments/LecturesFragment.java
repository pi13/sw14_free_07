package at.lvmaster3000.gui.fragments;

import java.util.List;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import at.lvmaster3000.R;
import at.lvmaster3000.database.IDBlogic;
import at.lvmaster3000.database.objects.Lecture;
import at.lvmaster3000.gui.DBObject;
import at.lvmaster3000.gui.adapters.LectureListAdapter;
import at.lvmaster3000.gui.interfaces.IDialogListener;

public class LecturesFragment extends UIFragmentBase implements OnItemClickListener, OnClickListener {

	private LectureListAdapter adapter;
	private ListView list;
	private List<Lecture> lectures;
	private Context context;
	private IDBlogic dbLogic;
	
	public static LecturesFragment newInstance(Context context, IDBlogic dbLogic) 
	{
		LecturesFragment base = new LecturesFragment();
		base.context = context;		
		base.dbLogic = dbLogic;
		base.lectures = dbLogic.getLectures(0).getLectures();
		base.adapter = new LectureListAdapter(context, base.lectures);


		return base;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root =  inflater.inflate(R.layout.fragment_list_layout, container, false);
		
		list = (ListView)root.findViewById(R.id.fragment_list);
		attachAdapter(list, adapter);
		list.setOnItemClickListener(this);
		Button addButton =(Button) root.findViewById(R.id.add_btn);
		addButton.setOnClickListener(this);
		ImageView imgDelete = (ImageView)root.findViewById(R.id.delete_list_item_btn);
		return root;
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switchToFragemnt(LectureDetailsFragment.newInstance((Lecture) adapter.getItem(position), context, dbLogic));
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_btn:
			showDialog();
			break;
		}
	}
	
	public void updateLectureList(Lecture lecture)
	{
		lectures.add(lecture);
		dbLogic.addLecture(lecture);
		adapter.notifyDataSetChanged();
	}
	
	private void showDialog() {
	    DialogFragment newFragment = AddLectureFragment.newInstance(context);
	    newFragment.show(getFragmentManager(), "add_lecture_dialog");
	}
}
