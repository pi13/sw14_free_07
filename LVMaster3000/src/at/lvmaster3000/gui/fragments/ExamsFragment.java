package at.lvmaster3000.gui.fragments;

import java.util.List;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import at.lvmaster3000.R;
import at.lvmaster3000.database.IDBlogic;
import at.lvmaster3000.database.objects.Exam;
import at.lvmaster3000.database.objects.Lecture;
import at.lvmaster3000.gui.adapters.ExamListAdapter;
import at.lvmaster3000.gui.interfaces.IDeleteItems;

public class ExamsFragment extends UIFragmentBase implements OnItemClickListener, OnClickListener {
	
	private ExamListAdapter adapter;
	private ListView list;
	private List<Exam> exams;
	private Context context;
	private IDBlogic dbLogic;
	private IDeleteItems deleteExamsListener;
	
	public static ExamsFragment newInstance(Context context, IDBlogic dbLogic) 
	{
		ExamsFragment base = new ExamsFragment();
		base.context = context;		
		base.dbLogic = dbLogic;
		base.exams = dbLogic.getExams(0).getExams();
		
//		dbLogic.getExams(0, false).printExamList();		
		dbLogic.getExams(0).printExamList();
		
		base.adapter = new ExamListAdapter(context, base.exams, base);


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
			deleteExamsListener = (IDeleteItems) activity;
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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switchToFragemnt(ExamDetailsFragment.newInstance((Exam) adapter.getItem(position), context, dbLogic), getResources().getString(R.string.exam_details_frag));
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_btn:
			showDialog();
			break;
		case R.id.delete_list_item_btn:
			deleteExamsListener.DeleteExamItem((Exam)adapter.getItem((Integer)v.getTag()));		
			break;
		}
	}
	
	public void updateExamList(Exam exam)
	{
		exams.add(exam);
		dbLogic.addExam(exam);
		adapter.notifyDataSetChanged();
	}
	
	public void deleteExam(Exam exam)
	{
		exams.remove(exam);
		dbLogic.deleteExam(exam);
		adapter.notifyDataSetChanged();
	}
	
	private void showDialog() {
	    DialogFragment newFragment = AddExamFragment.newInstance(context);
	    newFragment.show(getFragmentManager(), "add_exam_dialog");
	}

}
