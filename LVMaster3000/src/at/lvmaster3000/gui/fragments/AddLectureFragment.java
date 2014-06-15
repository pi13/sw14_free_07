package at.lvmaster3000.gui.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import at.lvmaster3000.R;
import at.lvmaster3000.database.IDBlogic;
import at.lvmaster3000.database.objects.Lecture;
import at.lvmaster3000.gui.adapters.LectureListAdapter;
import at.lvmaster3000.gui.interfaces.IDialogListener;

public class AddLectureFragment extends DialogFragment implements
		OnClickListener {

	private IDialogListener dialogListener;

	private Context context;
	private Lecture lecture;
	private EditText lvNumber;
	private EditText lvName;
	private EditText lvComment;
	private Spinner lvType;
	private ArrayAdapter<CharSequence> lvTypeAdapter;

	int isRequired;
	int isCompulsory;

	public static AddLectureFragment newInstance(Context context) {
		AddLectureFragment dialog = new AddLectureFragment();
		dialog.context = context;
		dialog.lvTypeAdapter = ArrayAdapter.createFromResource(dialog.context,
				R.array.lecture_types, R.layout.spinner_item);
		dialog.lvTypeAdapter.setDropDownViewResource(R.layout.spinner_item);
		return dialog;
	}

	public Lecture getLecture() {
		return lecture;
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
		View view = inflater.inflate(R.layout.add_lecture_fragment, container);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		Button ok = (Button) view.findViewById(R.id.ok_btn);
		Button cancel = (Button) view.findViewById(R.id.cancel_btn);
		ok.setOnClickListener(this);
		cancel.setOnClickListener(this);

		lvNumber = (EditText) view.findViewById(R.id.add_diag_lect_lectId);
		lvName = (EditText) view.findViewById(R.id.add_diag_lect_lectName);
		lvComment = (EditText) view.findViewById(R.id.add_diag_lect_comment);
		lvType = (Spinner) view.findViewById(R.id.add_diag_lect_lectType);
		lvType.setAdapter(lvTypeAdapter);

		CheckBox isRequred = (CheckBox)view.findViewById(R.id.add_dialog_lect_required);
		isRequred.setOnClickListener(this);
		
		CheckBox isCompulsory = (CheckBox)view.findViewById(R.id.add_dialog_lect_compulsory);
		isCompulsory.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ok_btn:
			lecture = new Lecture(0, lvNumber.getText().toString(), lvName
					.getText().toString(), lvComment.getText().toString(),
					lvType.getSelectedItem().toString(), isRequired,isCompulsory);
			
			dialogListener.onLectureAdd(this);
			break;

		case R.id.cancel_btn:
			dialogListener.onDialogNegativeClick(this);
			break;
			
		case R.id.add_dialog_lect_compulsory:
			onCheckboxClicked(v);
			break;
			
		case R.id.add_dialog_lect_required:
			onCheckboxClicked(v);
			break;
		}
	}

	private void onCheckboxClicked(View view) {
		boolean checked = ((CheckBox) view).isChecked();

		switch (view.getId()) {
		case R.id.add_dialog_lect_compulsory:
			if (checked) {
				isCompulsory = 1;
			} 
			else {
				isCompulsory = 0;
			}
			break;
		case R.id.add_dialog_lect_required:
			if (checked) {
				isRequired = 1;
			}
			else {
				isRequired = 0;
			}
			break;
		}
	}
}
