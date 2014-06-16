package at.lvmaster3000.gui.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import at.lvmaster3000.R;
import at.lvmaster3000.database.IDBlogic;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Exam;
import at.lvmaster3000.database.objects.Lecture;
import at.lvmaster3000.gui.interfaces.IDialogListener;

public class AddExamFragment  extends DialogFragment implements OnClickListener {

	private IDialogListener dialogListener;

	private Context context;
	private Exam exam;
	private EditText examTitle;
	private EditText examComment;
	private DatePicker datePicker;
	
	private long lectureId;	
	private IDBlogic dbLogic;

	public static AddExamFragment newInstance(Context context) {
		AddExamFragment dialog = new AddExamFragment();
		dialog.context = context;
		return dialog;
	}

	public Exam getExam() {
		return exam;
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
			dialogListener = (IDialogListener) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.add_exam_fragment, container);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		Button ok = (Button) view.findViewById(R.id.ok_btn);
		Button cancel = (Button) view.findViewById(R.id.cancel_btn);
		ok.setOnClickListener(this);
		cancel.setOnClickListener(this);

		examTitle = (EditText) view.findViewById(R.id.add_exam_title);
		examComment = (EditText) view.findViewById(R.id.add_exam_comment);
		datePicker = (DatePicker)view.findViewById(R.id.add_exam_date);
		
		Bundle bundle = getArguments();
		if(bundle != null) {
			this.lectureId = bundle.getLong("lectureId");		
			this.dbLogic = new IDBlogic(context);
		}

		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ok_btn:
			exam = new Exam(0, examTitle.getText().toString(), examComment.getText().toString(), 0, 
							new Date(0, datePicker.getCalendarView().getDate()/1000, "","",""));
			exam.printExam();
			if(this.lectureId > 0) {
				Lecture lecture = new Lecture();
				lecture.setID(this.lectureId);
				this.dbLogic.addExamToLecture(exam, lecture);
				dialogListener.onExamAddToLecture(this);
			} else {
				dialogListener.onExamAdd(this);
			}			
			
			break;

		case R.id.cancel_btn:
			dialogListener.onDialogNegativeClick(this);
			break;
		}
	}

}
