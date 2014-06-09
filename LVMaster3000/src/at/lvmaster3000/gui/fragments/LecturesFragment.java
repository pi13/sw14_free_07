package at.lvmaster3000.gui.fragments;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import at.lvmaster3000.gui.adapters.LectureListAdapter;
import at.lvmaster3000.gui.interfaces.IDialogListener;
import at.lvmaster3000.interfaces.IDeleteItems;

public class LecturesFragment extends UIFragmentBase implements OnItemClickListener, OnClickListener {

	private LectureListAdapter adapter;
	private ListView list;
	private List<Lecture> lectures;
	private Context context;
	private IDBlogic dbLogic;
	private IDeleteItems deleteLectureListener;
	
	public static LecturesFragment newInstance(Context context, IDBlogic dbLogic) 
	{
		LecturesFragment base = new LecturesFragment();
		base.context = context;		
		base.dbLogic = dbLogic;
		base.lectures = dbLogic.getLectures(0).getLectures();
		base.adapter = new LectureListAdapter(context, base.lectures, base);

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
			deleteLectureListener = (IDeleteItems) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root =  inflater.inflate(R.layout.fragment_list_layout, container, false);
		
		list = (ListView)root.findViewById(R.id.fragment_list);
		attachAdapter(list, adapter);
		list.setOnItemClickListener(this);
		Button addButton =(Button) root.findViewById(R.id.add_btn);
		addButton.setOnClickListener(this);

		return root;
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
			switchToFragemnt(LectureDetailsFragment.newInstance((Lecture) adapter.getItem(position), context, dbLogic));
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_btn:
			showDialog();
			break;
			
		case R.id.delete_list_item_btn:
			deleteLectureListener.DeleteLectureItem((Lecture)adapter.getItem((Integer)v.getTag()));		
			break;
		}
	}
	
	public void updateLectureList(Lecture lecture)
	{
		lectures.add(lecture);
		dbLogic.addLecture(lecture);
		adapter.notifyDataSetChanged();
	}
	
	public void deleteLecture(Lecture lecture)
	{
		lectures.remove(lecture);
		dbLogic.deleteLecture(lecture);
		adapter.notifyDataSetChanged();
	}
	
	private void showDialog() {
	    DialogFragment newFragment = AddLectureFragment.newInstance(context);
	    newFragment.show(getFragmentManager(), "add_lecture_dialog");
	}
	
//	@Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//        getActivity().invalidateOptionsMenu();
//    }
//	
//	@Override
//	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {		
//		menu.clear();
//	    inflater.inflate(R.menu.actions, menu);
//	}
//	
//	@Override
//	public void onPrepareOptionsMenu(Menu menu) {	
//		menu.clear();
//		MenuInflater inflater = getActivity().getMenuInflater();
//	    inflater.inflate(R.menu.actions, menu);
//	}
//	
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//			case R.id.action_settings:
//				return true;
//			default:
//				return super.onOptionsItemSelected(item);
//		}
//	}
}
