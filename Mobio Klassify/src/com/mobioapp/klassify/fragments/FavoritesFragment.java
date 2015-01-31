package com.mobioapp.klassify.fragments;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mobioapp.klassify.R;
import com.mobioapp.klassify.adapters.ItemAdapter;
import com.mobioapp.klassify.models.Item;
import com.mobioapp.klassify.utils.Database;
import com.mobioapp.klassify.utils.DetectConnection;
import com.mobioapp.klassify.utils.URLs;
import com.mobioapp.klassify.utils.Values;

public class FavoritesFragment extends Fragment {
	ArrayList<Item> items = new ArrayList<Item>();
	ListView lv;
	ProgressDialog progress;
	Database database;

	public FavoritesFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		System.out.println("CAT:::2");
		lv = (ListView) getActivity().findViewById(R.id.fav_listView1);

		database = new Database(getActivity());
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				Values.cur_item = items.get(arg2);

				Fragment fragment = new ItemViewFragment();
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment)
						.addToBackStack(null).commit();

			}
		});
		registerForContextMenu(lv);
		loadData();

		/*
		 * if (CheckInternet.isNetworkPresent(getActivity())&&!Values.loaded) {
		 * loadData(); } else {
		 * 
		 * 
		 * Toast.makeText(getActivity(), " No Data ! ", Toast.LENGTH_SHORT)
		 * .show();
		 * 
		 * 
		 * }
		 */

	}

	private void loadData() {
		items.clear();
		// System.out.println(URLs.HOT_ITEM_URL);

		try {

			database.open();
			items = database.getFavItemCategoryList();
			database.close();

			if (items.size() > 0) {
				lv.setAdapter(new ItemAdapter(getActivity(), items));
				Values.loaded = true;
			} else {
				Toast.makeText(getActivity(), " No Items! ", Toast.LENGTH_SHORT)
						.show();
			}

		} catch (Exception e) {

		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_favorites,
				container, false);

		return rootView;
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.context_menu_new, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.remove_fav:
			if (DetectConnection.checkInternetConnection(getActivity())) {

				String name = "";

				try {
					AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
							.getMenuInfo();
					int position = info.position;
					database.open();
					database.deleteFavItem(items.get(position).getId());

					try {

						database.open();
						items = database.getFavItemCategoryList();
						database.close();

						if (items.size() > 0) {
							lv.setAdapter(new ItemAdapter(getActivity(), items));
							Values.loaded = true;
						} else {
							Toast.makeText(getActivity(), " No Items! ",
									Toast.LENGTH_SHORT).show();
						}

					} catch (Exception e) {

					}

					database.close();

				} catch (Exception e) {
					// TODO: handle exception
				}
			} else {
				Toast.makeText(getActivity(), " No Internet! ",
						Toast.LENGTH_SHORT).show();
			}
			return true;

		default:
			return super.onContextItemSelected(item);
		}
	}
}
