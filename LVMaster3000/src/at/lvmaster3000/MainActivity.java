package at.lvmaster3000;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
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
import at.lvmaster3000.gui.NavDrawerItem;
import at.lvmaster3000.gui.adapters.ExamListAdapter;
import at.lvmaster3000.gui.adapters.LectureListAdapter;
import at.lvmaster3000.gui.adapters.NavDrawerListAdapter;
import at.lvmaster3000.gui.adapters.ResourceListAdapter;
import at.lvmaster3000.gui.adapters.TaskListAdapter;
import at.lvmaster3000.gui.fragments.ExamsFragment;
import at.lvmaster3000.gui.fragments.HomeFragment;
import at.lvmaster3000.gui.fragments.LecturesFragment;
import at.lvmaster3000.gui.fragments.ResourcesFragment;
import at.lvmaster3000.gui.fragments.TasksFragment;
import at.lvmaster3000.gui.fragments.TestFragment;

public class MainActivity extends Activity {
	
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

		navDrawerItems.add(new NavDrawerItem(navMenuItems[0], navMenuIcons.getResourceId(0, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuItems[1], navMenuIcons.getResourceId(1, -1), true, dbLogic.getLectures(0).getLectures().size()));
		navDrawerItems.add(new NavDrawerItem(navMenuItems[2], navMenuIcons.getResourceId(2, -1), true, dbLogic.getTasks(0).getTasks().size()));
		navDrawerItems.add(new NavDrawerItem(navMenuItems[3], navMenuIcons.getResourceId(3, -1), true, dbLogic.getExams(0).getExam().size()));
		navDrawerItems.add(new NavDrawerItem(navMenuItems[4], navMenuIcons.getResourceId(4, -1), true, dbLogic.getResources(0).getResource().size()));
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
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
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
			fragment = new HomeFragment();
	        args = new Bundle();
	        args.putInt(getResources().getString(R.string.home), position);
	        fragment.setArguments(args);
			break;
			
		case 1:
	        args = new Bundle();
	        args.putInt(getResources().getString(R.string.tasks), position);
			fragment = LecturesFragment.newInstance(context, dbLogic, new LectureListAdapter(context, dbLogic.getLectures(0).getLectures()), args);
			break;
			
		case 2:
	        args = new Bundle();
	        args.putInt(getResources().getString(R.string.tasks), position);
			fragment = TasksFragment.newInstance(context, dbLogic, new TaskListAdapter(context, dbLogic.getTasks(0).getTasks()), args);
			break;
			
		case 3:
	        args = new Bundle();
	        args.putInt(getResources().getString(R.string.tasks), position);
			fragment = ExamsFragment.newInstance(context, dbLogic, new ExamListAdapter(context, dbLogic.getExams(0).getExam()), args);
			break;
			
		case 4:
	        args = new Bundle();
	        args.putInt(getResources().getString(R.string.tasks), position);
			fragment = ResourcesFragment.newInstance(context, dbLogic, new ResourceListAdapter(context, dbLogic.getResources(0).getResource()), args);
			break;
			
		case 5:
			fragment = new TestFragment();
	        args = new Bundle();
	        args.putInt(getResources().getString(R.string.test), position);
	        fragment.setArguments(args);
			break;	
			
		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragManager = getFragmentManager();
			FragmentTransaction fragTrans =  fragManager.beginTransaction();
			fragTrans.replace(R.id.frame_container, fragment);
			//fragTrans.addToBackStack(null);
			fragTrans.commit();

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
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

}
