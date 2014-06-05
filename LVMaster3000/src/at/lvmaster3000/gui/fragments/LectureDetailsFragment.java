package at.lvmaster3000.gui.fragments;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Spinner;
import at.lvmaster3000.R;
import at.lvmaster3000.database.objects.Lecture;
import at.lvmaster3000.gui.Group;
import at.lvmaster3000.gui.adapters.GroupExpandableListAdapter;

public class LectureDetailsFragment extends UIFragmentBase implements
		OnClickListener {

	private Context context;
	private SparseArray<Group> groups;
	private Lecture lecture;

	private EditText lvNumber;
	private EditText lvName;
	private EditText lvComment;
	private Spinner lvType;
	private ArrayAdapter<CharSequence> lvTypeAdapter;

	int isRequired;
	int isCompulsory;

	public static LectureDetailsFragment newInstance(Lecture lecture,
			Context context) {
		LectureDetailsFragment details = new LectureDetailsFragment();

		details.context = context;
		details.lvTypeAdapter = ArrayAdapter.createFromResource(
				details.context, R.array.lecture_types, R.layout.spinner_item);
		details.lvTypeAdapter.setDropDownViewResource(R.layout.spinner_item);

		details.groups = new SparseArray<Group>();
		details.lecture = lecture;

		return details;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_lecture_detail,
				container, false);
		lvNumber = (EditText) view.findViewById(R.id.details_diag_lect_lectId);
		lvName = (EditText) view.findViewById(R.id.details_lect_lectName);
		lvComment = (EditText) view
				.findViewById(R.id.details_diag_lect_comment);
		lvType = (Spinner) view.findViewById(R.id.details_diag_lect_lectType);
		lvType.setAdapter(lvTypeAdapter);

		CheckBox isRequred = (CheckBox) view
				.findViewById(R.id.details_dialog_lect_required);
		isRequred.setOnClickListener(this);

		CheckBox isCompulsory = (CheckBox) view
				.findViewById(R.id.details_dialog_lect_compulsory);
		isCompulsory.setOnClickListener(this);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		lvNumber.setText(lecture.getNumber());
		lvName.setText(lecture.getName());
		lvComment.setText(lecture.getComment());
		lvType.setSelection(lvTypeAdapter.getPosition(lecture.getType()));

		CheckBox isRequred = (CheckBox) getView().findViewById(
				R.id.details_dialog_lect_required);
		CheckBox isCompulsory = (CheckBox) getView().findViewById(
				R.id.details_dialog_lect_compulsory);

		if (lecture.getRequired() == 1)
			isRequred.setChecked(true);
		
		if (lecture.getCompulsory() == 1)
			isCompulsory.setChecked(true);

		CreateDummyData();
		ExpandableListView listview = (ExpandableListView) getView()
				.findViewById(R.id.lecture_items);
		GroupExpandableListAdapter adapter = new GroupExpandableListAdapter(
				this, this.groups);
		listview.setAdapter(adapter);
	}

	private void CreateDummyData() {
		Group test = new Group("Tasks");
		test.setChildren(new ArrayList<String>(Arrays.asList(getResources()
				.getStringArray(R.array.dummy_items))));
		this.groups.append(0, test);

		test = new Group("Exams");
		test.setChildren(new ArrayList<String>(Arrays.asList(getResources()
				.getStringArray(R.array.dummy_items))));
		this.groups.append(1, test);

		test = new Group("Resources");
		test.setChildren(new ArrayList<String>(Arrays.asList(getResources()
				.getStringArray(R.array.dummy_items))));
		this.groups.append(2, test);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.details_dialog_lect_compulsory:
			onCheckboxClicked(v);
			break;

		case R.id.details_dialog_lect_required:
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
			} else {
				isCompulsory = 0;
			}
			break;
		case R.id.add_dialog_lect_required:
			if (checked) {
				isRequired = 1;
			} else {
				isRequired = 0;
			}
			break;
		}
	}
}
