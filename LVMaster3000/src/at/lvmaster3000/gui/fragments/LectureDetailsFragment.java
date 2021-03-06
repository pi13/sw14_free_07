package at.lvmaster3000.gui.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import at.lvmaster3000.R;
import at.lvmaster3000.database.IDBlogic;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Exam;
import at.lvmaster3000.database.objects.Lecture;
import at.lvmaster3000.database.objects.Resource;
import at.lvmaster3000.database.objects.Task;
import at.lvmaster3000.gui.DateGroup;
import at.lvmaster3000.gui.ExamGroup;
import at.lvmaster3000.gui.ResourceGroup;
import at.lvmaster3000.gui.TaskGroup;
import at.lvmaster3000.gui.adapters.DateExpandableListAdapters;
import at.lvmaster3000.gui.adapters.ExamExpandableListAdapter;
import at.lvmaster3000.gui.adapters.ResourceExpandableListAdapter;
import at.lvmaster3000.gui.adapters.TaskExpandableListAdapter;
import at.lvmaster3000.gui.adapters.TaskListAdapter;
import at.lvmaster3000.gui.interfaces.IDeleteItems;
import at.lvmaster3000.gui.interfaces.IExpandableListItemSelected;
import at.lvmaster3000.gui.interfaces.IUpdateDBObject;

public class LectureDetailsFragment extends UIFragmentBase implements
		OnClickListener, OnGroupClickListener {

	private Context context;
	private SparseArray<ExamGroup> examsGroup;
	private SparseArray<TaskGroup> tasksGroup;
	private SparseArray<ResourceGroup> resourcesGroup;
	private SparseArray<DateGroup> dateGroup;

	private Lecture lecture;
	private IUpdateDBObject updateLectureListener;
	private IExpandableListItemSelected expandableItemListener;
	private IDeleteItems deleteListener;
	private IDBlogic dbLogic;

	private EditText lvNumber;
	private EditText lvName;
	private EditText lvComment;
	private Spinner lvType;
	private RelativeLayout inputContainer;

	private ArrayAdapter<CharSequence> lvTypeAdapter;
	private ExamExpandableListAdapter examsAdapter;
	private TaskExpandableListAdapter tasksAdapter;
	private ResourceExpandableListAdapter resourcesAdapter;
	private DateExpandableListAdapters dateAdapter;

	int isRequired;
	int isCompulsory;

	private Boolean initDone;

	private boolean logContainerToggle;
	private boolean examsExpanded;
	private boolean tasksExpanded;
	private boolean resourcesExpanded;
	private boolean datesExpanded;

	private ExpandableListView listviewExams;
	private ExpandableListView listviewTasks;
	private ExpandableListView listviewResources;
	private ExpandableListView listviewDates;

	public static LectureDetailsFragment newInstance(Lecture lecture,
			Context context, IDBlogic dbLogic) {
		LectureDetailsFragment details = new LectureDetailsFragment();

		details.context = context;
		details.lvTypeAdapter = ArrayAdapter.createFromResource(
				details.context, R.array.lecture_types, R.layout.spinner_item);
		details.lvTypeAdapter.setDropDownViewResource(R.layout.spinner_item);

		details.examsGroup = new SparseArray<ExamGroup>();
		details.tasksGroup = new SparseArray<TaskGroup>();
		details.resourcesGroup = new SparseArray<ResourceGroup>();
		details.dateGroup = new SparseArray<DateGroup>();

		details.lecture = lecture;
		details.dbLogic = dbLogic;

		details.initDone = false;

		return details;
	}

	public IExpandableListItemSelected getExpandableItemListener() {
		return expandableItemListener;
	}

	public IDeleteItems getDeleteListener() {
		return deleteListener;
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
			updateLectureListener = (IUpdateDBObject) activity;
			expandableItemListener = (IExpandableListItemSelected) activity;
			deleteListener = (IDeleteItems) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_lecture_detail,
				container, false);
		lvNumber = (EditText) view.findViewById(R.id.details_lect_lectId);
		lvName = (EditText) view.findViewById(R.id.details_lect_lectName);
		lvComment = (EditText) view.findViewById(R.id.details_lect_comment);
		inputContainer = (RelativeLayout) view
				.findViewById(R.id.details_lecture_fields);

		lvType = (Spinner) view.findViewById(R.id.details_lect_lectType);
		lvType.setAdapter(lvTypeAdapter);

		CheckBox isRequred = (CheckBox) view
				.findViewById(R.id.details_lect_required);
		isRequred.setOnClickListener(this);

		CheckBox isCompulsory = (CheckBox) view
				.findViewById(R.id.details_lect_compulsory);
		isCompulsory.setOnClickListener(this);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		updateFileds();

		listviewExams = (ExpandableListView) getView().findViewById(
				R.id.details_lect_exams);

		listviewTasks = (ExpandableListView) getView().findViewById(
				R.id.details_lect_tasks);

		listviewResources = (ExpandableListView) getView().findViewById(
				R.id.details_lect_resources);

		listviewDates = (ExpandableListView) getView().findViewById(
				R.id.details_lect_dates);

		addGroups(dbLogic);

		examsAdapter = new ExamExpandableListAdapter(this, examsGroup);
		tasksAdapter = new TaskExpandableListAdapter(this, tasksGroup);
		resourcesAdapter = new ResourceExpandableListAdapter(this,
				resourcesGroup);
		dateAdapter = new DateExpandableListAdapters(this, dateGroup);

		listviewExams.setAdapter(examsAdapter);
		listviewTasks.setAdapter(tasksAdapter);
		listviewResources.setAdapter(resourcesAdapter);
		listviewDates.setAdapter(dateAdapter);

		logContainerToggle = false;
		examsExpanded = false;
		tasksExpanded = false;
		resourcesExpanded = false;
		datesExpanded = false;

		listviewExams.setOnGroupClickListener(this);
		listviewTasks.setOnGroupClickListener(this);
		listviewResources.setOnGroupClickListener(this);
		listviewDates.setOnGroupClickListener(this);

		initDone = true;
	}

	private void updateFileds() {
		lvNumber.setText(lecture.getNumber());
		lvName.setText(lecture.getName());
		lvComment.setText(lecture.getComment());
		lvType.setSelection(lvTypeAdapter.getPosition(lecture.getType()));

		CheckBox isRequredChb = (CheckBox) getView().findViewById(
				R.id.details_lect_required);
		CheckBox isCompulsoryChb = (CheckBox) getView().findViewById(
				R.id.details_lect_compulsory);

		if (lecture.getRequired() == 1) {
			isRequired = 1;
			isRequredChb.setChecked(true);
		}

		if (lecture.getCompulsory() == 1) {
			isCompulsory = 1;
			isCompulsoryChb.setChecked(true);
		}
	}

	/**
	 * Load sublists for lecture
	 * 
	 * @param dbLogic
	 */
	private void addGroups(IDBlogic dbLogic) {
		ExamGroup exam = new ExamGroup(getResources().getString(R.string.exams));
		exam.setChildren(dbLogic.getExamsForLecture(lecture).getExams());
		examsGroup.append(0, exam);

		TaskGroup task = new TaskGroup(getResources().getString(R.string.tasks));
		task.setChildren(dbLogic.getTasksForLecture(lecture).getTasks());
		tasksGroup.append(0, task);

		ResourceGroup res = new ResourceGroup(getResources().getString(
				R.string.resources));
		res.setChildren(dbLogic.getResourcesForLecture(lecture).getResources());
		resourcesGroup.append(0, res);

		DateGroup dates = new DateGroup(getResources()
				.getString(R.string.dates));
		dates.setChildren(dbLogic.getDatesForLecture(lecture).getDates());
		dateGroup.append(0, dates);
	}

	public void deleteExam(Exam exam) {
		dbLogic.deleteExam(exam);
		examsGroup.get(0).getChildren().remove(exam);
		examsAdapter.notifyDataSetChanged();
	}

	public void deleteTask(Task task) {
		dbLogic.deleteTask(task);
		tasksGroup.get(0).getChildren().remove(task);
		tasksAdapter.notifyDataSetChanged();
	}

	public void deleteResource(Resource resource) {
		dbLogic.deleteResource(resource);
		resourcesGroup.get(0).getChildren().remove(resource);
		resourcesAdapter.notifyDataSetChanged();
	}

	public void deleteDate(Date date) {
		dbLogic.deleteDate(date);
		dateGroup.get(0).getChildren().remove(date);
		dateAdapter.notifyDataSetChanged();

	}

	public void updateTaskList(Task task) {
		
		tasksGroup.get(0).getChildren().add(task);
		tasksAdapter.notifyDataSetChanged();
	}

	public void updateExamList(Exam exam) {
		examsGroup.get(0).getChildren().add(exam);
		examsAdapter.notifyDataSetChanged();
	}

	public void updateResourceList(Resource res) {
		resourcesGroup.get(0).getChildren().add(res);
		resourcesAdapter.notifyDataSetChanged();
	}

	public void updateDateList(Date date) {
		dateGroup.get(0).getChildren().add(date);
		dateAdapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.details_lect_compulsory:
			onCheckboxClicked(v);
			break;

		case R.id.details_lect_required:
			onCheckboxClicked(v);
			break;
		}
	}

	private void onCheckboxClicked(View view) {
		boolean checked = ((CheckBox) view).isChecked();

		switch (view.getId()) {
		case R.id.details_lect_compulsory:
			if (checked) {
				isCompulsory = 1;
			} else {
				isCompulsory = 0;
			}
			break;
		case R.id.details_lect_required:
			if (checked) {
				isRequired = 1;
			} else {
				isRequired = 0;
				break;
			}
		}
	}

	private void updateLecture() {
		if (initDone) {
			lecture.setNumber(lvNumber.getText().toString());
			lecture.setName(lvName.getText().toString());
			lecture.setComment(lvComment.getText().toString());
			lecture.setType(lvType.getSelectedItem().toString());
			lecture.setRequired(isRequired);
			lecture.setCompulsory(isCompulsory);
			updateLectureListener.updateLecture(lecture);
		}

		Toast.makeText(context, "Saved :)", Toast.LENGTH_LONG).show();

		Log.i("TEST_", "updateLecture");
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
		inflater.inflate(R.menu.logic_fragment_details, menu);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.logic_fragment_details, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		DialogFragment newFragment;

		Bundle bundle = new Bundle();
		bundle.putLong("lectureId", lecture.getID());

		switch (item.getItemId()) {
		case R.id.action_save:

			updateLecture();

			return true;
		case R.id.action_addDate:
			newFragment = AddDateFragment.newInstance(context);
			newFragment.setArguments(bundle);
			newFragment.show(getFragmentManager(),
					getResources().getString(R.string.add_date_frag));
			return true;
		case R.id.action_addTask:

			newFragment = AddTaskFragment.newInstance(context);
			newFragment.setArguments(bundle);
			newFragment.show(getFragmentManager(),
					getResources().getString(R.string.add_task_frag));

			Log.w("TEST_", "action_addTask");
			return true;
		case R.id.action_addExam:

			newFragment = AddExamFragment.newInstance(context);
			newFragment.setArguments(bundle);
			newFragment.show(getFragmentManager(),
					getResources().getString(R.string.add_exam_frag));

			Log.w("TEST_", "action_addExam");
			return true;
		case R.id.action_addResource:

			newFragment = AddResourceFragment.newInstance(context);
			newFragment.setArguments(bundle);
			newFragment.show(getFragmentManager(),
					getResources().getString(R.string.add_res_frag));

			Log.w("TEST_", "action_addResource");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		switch (parent.getId()) {
		case R.id.details_lect_exams:
			return onExamGroupClick(parent, v, groupPosition, id);
		case R.id.details_lect_tasks:
			return onTaskGroupClick(parent, v, groupPosition, id);
		case R.id.details_lect_resources:
			return onResourceGroupClick(parent, v, groupPosition, id);
		case R.id.details_lect_dates:
			return onDateGroupClick(parent, v, groupPosition, id);
		}
		return false;
	}

	private boolean onDateGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		if (!datesExpanded) {
			datesExpanded = true;
		} else {
			datesExpanded = false;
		}

		// Log.w("TEST_", "expaned: " + examsExpanded + " / " +
		// tasksExpanded + " / " + resourcesExpanded);

		if (inputContainer.getVisibility() == View.VISIBLE) {
			inputContainer.setVisibility(View.GONE);
		}

		if (inputContainer.getVisibility() == View.GONE && !examsExpanded
				&& !tasksExpanded && !resourcesExpanded && !datesExpanded) {
			inputContainer.setVisibility(View.VISIBLE);
		}

		listviewExams.collapseGroup(0);
		examsExpanded = false;
		listviewTasks.collapseGroup(0);
		tasksExpanded = false;
		listviewResources.collapseGroup(0);
		resourcesExpanded = false;

		return false;
	}

	private boolean onResourceGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		if (!resourcesExpanded) {
			resourcesExpanded = true;
		} else {
			resourcesExpanded = false;
		}

		// Log.w("TEST_", "expaned: " + examsExpanded + " / " +
		// tasksExpanded + " / " + resourcesExpanded);

		if (inputContainer.getVisibility() == View.VISIBLE) {
			inputContainer.setVisibility(View.GONE);
		}

		if (inputContainer.getVisibility() == View.GONE && !examsExpanded
				&& !tasksExpanded && !resourcesExpanded && !datesExpanded) {
			inputContainer.setVisibility(View.VISIBLE);
		}

		listviewExams.collapseGroup(0);
		examsExpanded = false;
		listviewTasks.collapseGroup(0);
		tasksExpanded = false;
		listviewDates.collapseGroup(0);
		datesExpanded = false;

		return false;
	}

	private boolean onTaskGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		if (!tasksExpanded) {
			tasksExpanded = true;
		} else {
			tasksExpanded = false;
		}

		// Log.w("TEST_", "expaned: " + examsExpanded + " / " +
		// tasksExpanded + " / " + resourcesExpanded);

		if (inputContainer.getVisibility() == View.VISIBLE) {
			inputContainer.setVisibility(View.GONE);
		}

		if (inputContainer.getVisibility() == View.GONE && !examsExpanded
				&& !tasksExpanded && !resourcesExpanded && !datesExpanded) {
			inputContainer.setVisibility(View.VISIBLE);
		}

		listviewExams.collapseGroup(0);
		examsExpanded = false;
		listviewResources.collapseGroup(0);
		resourcesExpanded = false;
		listviewDates.collapseGroup(0);
		datesExpanded = false;

		return false;
	}

	private boolean onExamGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		if (!examsExpanded) {
			examsExpanded = true;
		} else {
			examsExpanded = false;
		}

		// Log.w("TEST_", "expaned: " + examsExpanded + " / " +
		// tasksExpanded + " / " + resourcesExpanded);

		if (inputContainer.getVisibility() == View.VISIBLE) {
			inputContainer.setVisibility(View.GONE);
		}

		if (inputContainer.getVisibility() == View.GONE && !examsExpanded
				&& !tasksExpanded && !resourcesExpanded && !datesExpanded) {
			inputContainer.setVisibility(View.VISIBLE);
		}

		listviewTasks.collapseGroup(0);
		tasksExpanded = false;
		listviewResources.collapseGroup(0);
		resourcesExpanded = false;
		listviewDates.collapseGroup(0);
		datesExpanded = false;

		return false;
	}
}
