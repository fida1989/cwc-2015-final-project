package com.mobioapp.klassify;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mobioapp.klassify.adapters.CategoryShowAdapter;
import com.mobioapp.klassify.fragments.AccountFragment;
import com.mobioapp.klassify.fragments.CategoriesFragment;
import com.mobioapp.klassify.fragments.FavoritesFragment;
import com.mobioapp.klassify.fragments.HomeFragment;
import com.mobioapp.klassify.fragments.MyAdsFragment;
import com.mobioapp.klassify.fragments.PostAdFragment;
import com.mobioapp.klassify.fragments.ProfileViewFragment;
import com.mobioapp.klassify.fragments.SearchAdFragment;
import com.mobioapp.klassify.models.Category;
import com.mobioapp.klassify.models.CategoryNew;
import com.mobioapp.klassify.models.City;
import com.mobioapp.klassify.models.Location;
import com.mobioapp.klassify.models.NewSubCategory;
import com.mobioapp.klassify.models.SubCategory;
import com.mobioapp.klassify.models.SubCategoryNew;
import com.mobioapp.klassify.models.User;
import com.mobioapp.klassify.utils.DetectConnection;
import com.mobioapp.klassify.utils.URLs;
import com.mobioapp.klassify.utils.Values;

public class MainActivity extends Activity {
	private DrawerLayout mDrawerLayout;
	public static ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	ProgressDialog progress;
	private CharSequence mDrawerTitle;
	public static CharSequence mTitle;
	private String[] mMenuTitles;
	SharedPreferences pref;
	public static ActionBar ab;
	City city = null;
	Location location = null;
	List<Category> categoryList = new ArrayList<Category>();
	List<NewSubCategory> data = new ArrayList<NewSubCategory>();
	List<SubCategory> mSubCategoryList = new ArrayList<SubCategory>();
	CategoryNew cat = null;
	SubCategoryNew sub_cat = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ab = getActionBar();
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);

		mTitle = mDrawerTitle = getTitle();
		mMenuTitles = getResources().getStringArray(R.array.menu_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mMenuTitles));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}

		// Pre-loading data for later use
		if (DetectConnection.checkInternetConnection(MainActivity.this)) {
			loadLocations();
			loadCategories();
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {
		case R.id.action_post:
			startActivity(new Intent(MainActivity.this,
					InstantAdPostAtivity.class));
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		Fragment fragment;
		FragmentManager fragmentManager;
		switch (position) {
		case 0:
			// update the main content by replacing fragments
			fragment = new HomeFragment();
			fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
			break;

		case 1:
			// update the main content by replacing fragments
			fragment = new MyAdsFragment();
			fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
			break;

		case 2:
			// update the main content by replacing fragments
			fragment = new FavoritesFragment();
			fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
			break;
		case 3:
			if (pref.getBoolean("log_track", false)) {
				Toast.makeText(getApplicationContext(), "Already Logged In!",
						Toast.LENGTH_LONG).show();

				User.Id = pref.getString("user_id", "");
				fragment = new ProfileViewFragment();

			} else {
				fragment = new AccountFragment();
			}

			fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
			break;

		case 4:
			// update the main content by replacing fragments
			fragment = new CategoriesFragment();
			fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
			break;
		case 5:
			// update the main content by replacing fragments
			fragment = new SearchAdFragment();
			fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
			break;
		default:
			break;
		}

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(mMenuTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
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

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		/*
		 * if (Values.register) { Values.register = false; Fragment fragment =
		 * new AccountFragment(); FragmentManager fragmentManager =
		 * getFragmentManager(); fragmentManager.beginTransaction()
		 * .replace(R.id.content_frame, fragment).commit();
		 * 
		 * }
		 */
	}

	private void loadLocations() {
		

		AsyncHttpClient client = new AsyncHttpClient();
		client.get(URLs.LOC_URL, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				System.out.println("CAT:::4");
				// called before request is started
				

			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				// called when response HTTP status is "200 OK"
				

				try {

					
					JSONObject jsonObj = new JSONObject(new String(response));

					System.out.println("Json " + jsonObj);

					JSONArray jsonArray = jsonObj.getJSONArray("details");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject c = jsonArray.getJSONObject(i);

						city = new City();
						city.id = c.getString("id");
						city.name = c.getString("name");

						JSONArray array = c.getJSONArray("city_area");

						for (int j = 0; j < array.length(); j++) {
							JSONObject newC = array.getJSONObject(j);
							location = new Location();
							location.id = newC.getString("id");

							location.name = newC.getString("name");

							city.locations.add(location);

						}

						Values.cities.add(city);

					}

					System.out.println("LOCATION:::" + Values.cities.size());

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] errorResponse, Throwable e) {
				// called when response HTTP status is "4XX" (eg.
				// 401, 403, 404)
			
			}

			@Override
			public void onRetry(int retryNo) {
				// called when request is retried
			}

		});

	}

	private void loadCategories() {

		AsyncHttpClient client = new AsyncHttpClient();
		client.get(URLs.CAT_URL, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// called before request is started

			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				// called when response HTTP status is "200 OK"

				try {

					JSONObject jsonObj = new JSONObject(new String(response));

					System.out.println("Json " + jsonObj);

					JSONArray jsonArray = jsonObj.getJSONArray("details");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject c = jsonArray.getJSONObject(i);

						// String id = c.getString("id");
						// String name = c.getString("name");

						cat = new CategoryNew();
						cat.id = c.getString("id");
						cat.name = c.getString("name");

						JSONArray array = c.getJSONArray("sub_category");

						for (int j = 0; j < array.length(); j++) {
							JSONObject newC = array.getJSONObject(j);

							sub_cat = new SubCategoryNew();
							sub_cat.id = newC.getString("id");
							sub_cat.name = newC.getString("name");
							cat.sub_cats.add(sub_cat);

						}

						Values.cats.add(cat);

					}

				//	System.out.println("CATEGORY:::" + Values.cats.size());

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] errorResponse, Throwable e) {
				// called when response HTTP status is "4XX" (eg.
				// 401, 403, 404)
				// progress.dismiss();
				//System.out.println("CAT:::Fail");

			}

			@Override
			public void onRetry(int retryNo) {
				// called when request is retried
			}

		});

	}

	@Override
	public void onBackPressed() {
		FragmentManager fm = getFragmentManager();
		if (fm.getBackStackEntryCount() > 0) {
			Log.i("MainActivity", "popping backstack");
			fm.popBackStack();
		} else {
			Log.i("MainActivity", "nothing on backstack, calling super");
			super.onBackPressed();
		}
	}

}