package at.lvmaster3000.gui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import at.lvmaster3000.R;
import at.lvmaster3000.database.IDBlogic;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Exam;
import at.lvmaster3000.gui.interfaces.IUpdateDBObject;

public class ExamDetailsFragment extends UIFragmentBase{

	private Context context;
	private IDBlogic dbLogic;

	private EditText examTitle;
	private EditText examComment;
	private DatePicker examDate;
	private Boolean initDone;
	
	private Exam exam;
	
	private IUpdateDBObject updateExamListener;
	
	public static ExamDetailsFragment newInstance(Exam exam, Context context, IDBlogic dbLogic) 
	{
		ExamDetailsFragment base = new ExamDetailsFragment();
		
		base.context = context;		
		base.dbLogic = dbLogic;
		base.exam = exam;

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
			updateExamListener = (IUpdateDBObject) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_exam_details, container, false);
		examTitle = (EditText) view.findViewById(R.id.detail_exam_title);
		examComment = (EditText) view.findViewById(R.id.detail_exam_comment);
		examDate = (DatePicker) view.findViewById(R.id.detail_exam_date);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		updateFileds();
		initDone = true;
	}
	
	private void updateFileds()
	{
		examTitle.setText(exam.getTitle());
		examComment.setText(exam.getComment());
		
		examDate.getCalendarView().setDate(exam.getDate().getTimestamp()*1000);
	}
	
	private void updateExam() {
		if(initDone)
		{
			exam.setTitle((examTitle.getText().toString()));
			exam.setComment((examComment.getText().toString()));
			exam.getDate().setTimestamp(examDate.getCalendarView().getDate()/1000);
			updateExamListener.updateLectureExam(exam);
		}

		Toast.makeText(context, "Saved :)", Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		getActivity().invalidateOptionsMenu();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.exam_fragment_details, menu);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.exam_fragment_details, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_save:
			updateExam();
			exam.printExam();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
}
