package at.lvmaster3000;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import at.lvmaster3000.database.IDBlogic;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Exam;
import at.lvmaster3000.database.objects.Lecture;
import at.lvmaster3000.database.objects.Resource;
import at.lvmaster3000.database.objects.Task;
import at.lvmaster3000.gui.NavDrawerItem;
import at.lvmaster3000.gui.adapters.NavDrawerListAdapter;
import at.lvmaster3000.gui.fragments.AddExamFragment;
import at.lvmaster3000.gui.fragments.AddLectureFragment;
import at.lvmaster3000.gui.fragments.AddResourceFragment;
import at.lvmaster3000.gui.fragments.AddTaskFragment;
import at.lvmaster3000.gui.fragments.DateDetailsFragment;
import at.lvmaster3000.gui.fragments.ExamDetailsFragment;
import at.lvmaster3000.gui.fragments.ExamsFragment;
import at.lvmaster3000.gui.fragments.HomeFragment;
import at.lvmaster3000.gui.fragments.LectureDetailsFragment;
import at.lvmaster3000.gui.fragments.LecturesFragment;
import at.lvmaster3000.gui.fragments.ResourceDetailsFragment;
import at.lvmaster3000.gui.fragments.ResourcesFragment;
import at.lvmaster3000.gui.fragments.TaskDetailsFragment;
import at.lvmaster3000.gui.fragments.TasksFragment;
import at.lvmaster3000.gui.fragments.TestFragment;
import at.lvmaster3000.gui.interfaces.IDeleteItems;
import at.lvmaster3000.gui.interfaces.IDialogListener;
import at.lvmaster3000.gui.interfaces.IExpandableListItemSelected;
import at.lvmaster3000.gui.interfaces.IUpdateDBObject;

public class MainActivity extends Activity implements IDialogListener, IUpdateDBObject, IDeleteItems, IExpandableListItemSelected{
	
	private final int POS_LECTURES = 1;
	private final int POS_TASKS = 2;
	private final int POS_EXAMS = 3;
	private final int POS_RESOURCES = 4;
	
	private IDBlogic dbLogic;
	private Context context;
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] navMenuItems;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	
	private FragmentManager fragManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		context = getApplicationContext();
		dbLogic = new IDBlogic(context);
		mTitle = mDrawerTitle = getTitle();

		navMenuItems = getResources().getStringArray(R.array.nav_drawer_items);
		navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		//TODO: update list when new item is added
		navDrawerItems.add(new NavDrawerItem(navMenuItems[0], navMenuIcons.getResourceId(0, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuItems[1], navMenuIcons.getResourceId(1, -1), true, dbLogic.getLectures(0).getLectures().size()));
		navDrawerItems.add(new NavDrawerItem(navMenuItems[2], navMenuIcons.getResourceId(2, -1), true, dbLogic.getTasks(0).getTasks().size()));
		navDrawerItems.add(new NavDrawerItem(navMenuItems[3], navMenuIcons.getResourceId(3, -1), true, dbLogic.getExams(0).getExams().size()));
		navDrawerItems.add(new NavDrawerItem(navMenuItems[4], navMenuIcons.getResourceId(4, -1), true, dbLogic.getResources(0).getResources().size()));
		navDrawerItems.add(new NavDrawerItem(navMenuItems[5], navMenuIcons.getResourceId(5, -1)));

		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
		adapter = new NavDrawerListAdapter(getApplicationContext(),	navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(
				this, 
				mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements	ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
			// display view for selected nav drawer item
			selectItem(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		
		//TODO: need 2 fix
//		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void selectItem(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		Bundle args = null;
		switch (position) {
		case 0:
			fragment = HomeFragment.newInstance(context, dbLogic);
			showFragment(fragment, getResources().getString(R.string.home), position);
			break;
			
		case 1:
			fragment = LecturesFragment.newInstance(context, dbLogic);
			showFragment(fragment, getResources().getString(R.string.lectures), position);
			break;
			
		case 2:
			fragment = TasksFragment.newInstance(context, dbLogic);
			showFragment(fragment, getResources().getString(R.string.tasks), position);			
			break;
			
		case 3:
			fragment = ExamsFragment.newInstance(context, dbLogic);
			showFragment(fragment, getResources().getString(R.string.exams), position);
			break;
			
		case 4:
			fragment = ResourcesFragment.newInstance(context, dbLogic);
			showFragment(fragment, getResources().getString(R.string.resources), position);
			break;
			
		case 5:
			fragment =  TestFragment.newInstance(context, dbLogic, adapter, this);
			showFragment(fragment, getResources().getString(R.string.test), position);
			break;	
			
		default:
			break;
		}
	}

	private void showFragment(Fragment fragment, String tag)
	{
		if (fragment != null) {
			fragManager = getFragmentManager();
			FragmentTransaction fragTrans =  fragManager.beginTransaction();

			if(tag != null)
			{
				fragTrans.replace(R.id.frame_container, fragment, tag);
			}else
			{
				fragTrans.replace(R.id.frame_container, fragment);
			}
			
			fragTrans.addToBackStack(null);
			fragTrans.commit();
			
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
		
	}
	
	private void showFragment(Fragment fragment, String tag, int position)
	{
		if (fragment != null) {
			showFragment(fragment, tag);

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuItems[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
			
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
		
	}
	
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		//mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onLectureAdd(DialogFragment dialog) {
		AddLectureFragment temp = (AddLectureFragment)dialog;
		LecturesFragment lectFrag = (LecturesFragment)fragManager.findFragmentByTag(getResources().getString(R.string.lectures));
		Lecture lecture = temp.getLecture();
		lectFrag.updateLectureList(lecture);
		updateElemetnCount(POS_LECTURES);
		dialog.dismiss();
		
		showFragment(LectureDetailsFragment.newInstance(lecture, context, dbLogic), Long.toString(lecture.getID()));
	}
	
	@Override
	public void onResourceAdd(DialogFragment dialog) {
		AddResourceFragment temp = (AddResourceFragment)dialog;
		ResourcesFragment resFrag = (ResourcesFragment)fragManager.findFragmentByTag(getResources().getString(R.string.resources));
		Resource res = temp.getResource();
		resFrag.updateResourceList(res);
		updateElemetnCount(POS_RESOURCES);
		dialog.dismiss();
		
		showFragment(ResourceDetailsFragment.newInstance(res, context, dbLogic), Long.toString(res.getId()));
	}

	@Override
	public void onExamAdd(DialogFragment dialog) {
		AddExamFragment temp = (AddExamFragment)dialog;
		ExamsFragment examFrag = (ExamsFragment)fragManager.findFragmentByTag(getResources().getString(R.string.exams));
		Exam exam = temp.getExam();
		examFrag.updateExamList(exam);
		updateElemetnCount(POS_EXAMS);
		dialog.dismiss();	
		
		showFragment(ExamDetailsFragment.newInstance(exam, context, dbLogic), Long.toString(exam.getId()));
	}

	@Override
	public void onTaskAdd(DialogFragment dialog) {
		AddTaskFragment temp = (AddTaskFragment)dialog;
		TasksFragment taskFrag = (TasksFragment)fragManager.findFragmentByTag(getResources().getString(R.string.tasks));
		Task task = temp.getTask();
		taskFrag.updateTaskList(task);
		updateElemetnCount(POS_TASKS);
		dialog.dismiss();
		
		showFragment(TaskDetailsFragment.newInstance(task, context, dbLogic), Long.toString(task.getId()));
	}
	
	@Override
	public void onTaskAddToLecture(DialogFragment dialog) {
		LectureDetailsFragment taskFrag = (LectureDetailsFragment)fragManager.findFragmentByTag(getResources().getString(R.string.lecture_details_frag));		
		taskFrag.updateTaskList();		
		dialog.dismiss();
	}
	
	@Override
	public void onExamAddToLecture(DialogFragment dialog) {
		LectureDetailsFragment taskFrag = (LectureDetailsFragment)fragManager.findFragmentByTag(getResources().getString(R.string.lecture_details_frag));		
		taskFrag.updateExamList();
		dialog.dismiss();
	}
	
	@Override
	public void onResourceAddToLecture(DialogFragment dialog) {
		LectureDetailsFragment taskFrag = (LectureDetailsFragment)fragManager.findFragmentByTag(getResources().getString(R.string.lecture_details_frag));		
		taskFrag.updateResourceList();
		dialog.dismiss();
	}


	@Override
	public void onDateAddToLecture(DialogFragment dialog) {
		LectureDetailsFragment dateFrag = (LectureDetailsFragment)fragManager.findFragmentByTag(getResources().getString(R.string.lecture_details_frag));		
		dateFrag.updateDateList();
		dialog.dismiss();
		
	}
	
	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		dialog.dismiss();
		
	}

	@Override
	public void updateLecture(Lecture lecture) {
		dbLogic.updateLecture(lecture);
		
	}

	@Override
	public void updateLectureExam(Exam exam) {
		dbLogic.updateExam(exam);
	}

	@Override
	public void updateTask(Task task) {
		dbLogic.updateTask(task);
	}

	@Override
	public void updateResource(Resource resource) {
		dbLogic.updateResource(resource);
		
	}
	
	@Override
	public void updateDate(Date date) {
		
		dbLogic.updateDate(date);
	}

	@Override
	public void DeleteLectureItem(final Lecture lecture) {
		new AlertDialog.Builder(this)
	    .setTitle("Delete entry")
	    .setMessage("Are you sure you want to delete this entry?")
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	    		LecturesFragment fragment = (LecturesFragment)fragManager.findFragmentByTag(getResources().getString(R.string.lectures));
	    		fragment.deleteLecture(lecture);
	    		updateElemetnCount(POS_LECTURES);
	    		dialog.dismiss();
	        }
	     })
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            dialog.dismiss();
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
	}

	@Override
	public void DeleteExamItem(final Exam exam) {
		new AlertDialog.Builder(this)
	    .setTitle("Delete entry")
	    .setMessage("Are you sure you want to delete this entry?")
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	    		ExamsFragment fragment = (ExamsFragment)fragManager.findFragmentByTag(getResources().getString(R.string.exams));
	    		fragment.deleteExam(exam);
	    		updateElemetnCount(POS_EXAMS);
	    		dialog.dismiss();
	        }
	     })
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            dialog.dismiss(); 
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
		
	}

	@Override
	public void DeleteTaskItem(final Task task) {
		new AlertDialog.Builder(this)
	    .setTitle("Delete entry")
	    .setMessage("Are you sure you want to delete this entry?")
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	    		TasksFragment fragment = (TasksFragment)fragManager.findFragmentByTag(getResources().getString(R.string.tasks));
	    		fragment.deleteTask(task);
	    		updateElemetnCount(POS_TASKS);
	    		dialog.dismiss();
	        }
	     })
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            dialog.dismiss();
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
		
	}

	@Override
	public void DeleteResourceItem(final Resource resource) {
		new AlertDialog.Builder(this)
	    .setTitle("Delete entry")
	    .setMessage("Are you sure you want to delete this entry?")
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	    		ResourcesFragment fragment = (ResourcesFragment)fragManager.findFragmentByTag(getResources().getString(R.string.resources));
	    		fragment.deleteTask(resource);
	    		updateElemetnCount(POS_RESOURCES);
	    		dialog.dismiss();
	        }
	     })
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            dialog.dismiss();
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
		
	}
	
	private void updateElemetnCount(int position)
	{
		switch (position) {
		case POS_LECTURES:
			navDrawerItems.get(position).setCount(dbLogic.getLectures(0).getLectures().size());
			break;
		case POS_TASKS:
			navDrawerItems.get(position).setCount(dbLogic.getTasks(0).getTasks().size());
			break;
		case POS_EXAMS:
			navDrawerItems.get(position).setCount(dbLogic.getExams(0).getExams().size());
			break;
		case POS_RESOURCES:
			navDrawerItems.get(position).setCount(dbLogic.getResources(0).getResources().size());
			break;
		default:
			break;
		}
		
		adapter.notifyDataSetChanged();
	
	}

	@Override
	public void DeleteItem(final Object item) {
		new AlertDialog.Builder(this)
	    .setTitle("Delete entry")
	    .setMessage("Are you sure you want to delete this entry?")
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	LectureDetailsFragment fragment = (LectureDetailsFragment)fragManager.findFragmentByTag(getResources().getString(R.string.lecture_details_frag));
	        	
	        	if(item instanceof Exam){
		    		fragment.deleteExam((Exam)item);
		    		updateElemetnCount(POS_EXAMS);
	        	}
	        	
	        	if(item instanceof Resource){
		    		fragment.deleteResource((Resource)item);
		    		updateElemetnCount(POS_RESOURCES);
	        	}
	        	
	        	if(item instanceof Task){
		    		fragment.deleteTask((Task) item);
		    		updateElemetnCount(POS_TASKS);
	        	}
	        	
	        	
	        	if(item instanceof Date){
		    		fragment.deleteDate((Date)item);
	        	}
	    		
	    		
	    		dialog.dismiss();
	        }
	     })
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            dialog.dismiss();
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
	}
	
	@Override
	public void onExpandableDateSelected(Date date) {
		showFragment(DateDetailsFragment.newInstance(date, context, dbLogic), Long.toString(date.getID()));
	}

	@Override
	public void onExpandableExamSelected(Exam exam) {
		showFragment(ExamDetailsFragment.newInstance(exam, context, dbLogic), Long.toString(exam.getId()));
		
	}

	@Override
	public void onExpandableTaskSelected(Task task) {
		showFragment(TaskDetailsFragment.newInstance(task, context, dbLogic), Long.toString(task.getId()));
		
	}

	@Override
	public void onExpandableResourceSelected(Resource resource) {
		showFragment(ResourceDetailsFragment.newInstance(resource, context, dbLogic), Long.toString(resource.getId()));
	}

	@Override
	public void DeleteDateItem(Date date) {
		// TODO Auto-generated method stub
		
	}
}
