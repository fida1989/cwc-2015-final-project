package com.mobioapp.klassify.fragments;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mobioapp.klassify.R;
import com.mobioapp.klassify.adapters.CategoryShowAdapter;
import com.mobioapp.klassify.adapters.SubCategoryShowAdapter;
import com.mobioapp.klassify.models.Category;
import com.mobioapp.klassify.models.NewSubCategory;
import com.mobioapp.klassify.models.SubCategory;
import com.mobioapp.klassify.utils.Database;
import com.mobioapp.klassify.utils.DetectConnection;
import com.mobioapp.klassify.utils.SharedData;
import com.mobioapp.klassify.utils.URLs;
import com.mobioapp.klassify.utils.Values;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class CategoriesFragment extends Fragment {

	CategoryShowAdapter listAdapter;
	PullToRefreshListView expListView;
	List<String> listDataHeader;
	List<String> listDataHeaderChild;
	ProgressDialog progress;
	SharedData sharedData = SharedData.getInstance();
	List<Category> categoryList = new ArrayList<Category>();
	List<NewSubCategory> data = new ArrayList<NewSubCategory>();
	List<SubCategory> mSubCategoryList = new ArrayList<SubCategory>();
	Database database;
	boolean check = false;
	int position_new;

	public CategoriesFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		// get the listview
		expListView = (PullToRefreshListView) getActivity().findViewById(
				R.id.lvExp);

		expListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// Your code to refresh the list contents

				check = true;

				
						
				

				if (DetectConnection.checkInternetConnection(getActivity())) {
					loadData();
				} else {

					try {
						Values.catList.clear();
						Values.subCatList.clear();
						categoryList.clear();
						mSubCategoryList.clear();
						database.open();
						categoryList = database.getCategoryList();
						mSubCategoryList = database.getSubCategoryList();
						database.close();

						if (categoryList.size() > 0) {

							listAdapter = new CategoryShowAdapter(
									getActivity(), categoryList);

							// setting list adapter
							expListView.setAdapter(listAdapter);
						} else {
							Toast.makeText(getActivity(),
									" No Data From Server! ",
									Toast.LENGTH_SHORT).show();
						}
					} catch (Exception e) {

					}
					expListView.onRefreshComplete();
				}
			}
		});

		// preparing list data
		database = new Database(getActivity());

		if (DetectConnection.checkInternetConnection(getActivity())) {
			loadData();
		} else {

			try {
				Values.catList.clear();
				Values.subCatList.clear();
				categoryList.clear();
				mSubCategoryList.clear();
				database.open();
				categoryList = database.getCategoryList();
				mSubCategoryList = database.getSubCategoryList();
				database.close();

				if (categoryList.size() > 0) {

					listAdapter = new CategoryShowAdapter(getActivity(),
							categoryList);

					// setting list adapter
					expListView.setAdapter(listAdapter);
				} else {
					Toast.makeText(getActivity(), " No Data From Server! ",
							Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {

			}

		}

		expListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				position_new = position;

				showSubCategory();
				// Toast.makeText(getApplicationContext(), "TT",
				// Toast.LENGTH_LONG)
				// .show();

			}
		});

	}

	private void loadData() {
		System.out.println("CAT:::3");

		AsyncHttpClient client = new AsyncHttpClient();
		client.get(URLs.CAT_URL, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				System.out.println("CAT:::4");

				if (!check) {
					progress = new ProgressDialog(getActivity());
					progress.setMessage("Fetching Data...");
					progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					progress.setIndeterminate(true);
					progress.show();
				}
				// called before request is started

			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				// called when response HTTP status is "200 OK"
				System.out.println("CAT:::5");
				if (!check) {
					progress.dismiss();
				} else {
					expListView.onRefreshComplete();
					check = false;
				}

				// db.deleteContacts();
				categoryList.clear();
				mSubCategoryList.clear();

				try {
					// System.out.println("JSON:::" + new
					// String(response));
					// Toast.makeText(MainActivity.this, new
					// String(response), Toast.LENGTH_LONG).show();
					JSONObject jsonObj = new JSONObject(new String(response));

					System.out.println("Json " + jsonObj);

					JSONArray jsonArray = jsonObj.getJSONArray("details");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject c = jsonArray.getJSONObject(i);

						String id = c.getString("id");
						String parent_id = c.getString("parent_id");
						String name = c.getString("name");
						String summary = c.getString("summary");
						String image = c.getString("image");

						JSONArray array = c.getJSONArray("sub_category");

						for (int j = 0; j < array.length(); j++) {
							JSONObject newC = array.getJSONObject(j);
							String sub_id = newC.getString("id");
							String sub_parent_id = newC.getString("parent_id");
							String sub_name = newC.getString("name");
							String sub_summary = newC.getString("summary");
							String sub_status = newC.getString("status");

							mSubCategoryList.add(new SubCategory(sub_id,
									sub_parent_id, sub_name, sub_summary,
									sub_status));

							try {
								database.open();
								database.addSubCategory(new SubCategory(sub_id,
										sub_parent_id, sub_name, sub_summary,
										sub_status));
								database.close();
							} catch (Exception e) {
								e.printStackTrace();
							}

						}

						Values.subCatList = mSubCategoryList;

						categoryList.add(new Category(id, parent_id, name, "",
								summary, "", "", image));

						try {
							database.open();
							database.addCategory(new Category(id, parent_id,
									name, "", summary, "", "", image));
							database.close();
						} catch (Exception e) {
							e.printStackTrace();
						}

					}

					sharedData.setCategoryList(categoryList);
					Values.catList = categoryList;

					if (categoryList.size() > 0) {

						listAdapter = new CategoryShowAdapter(getActivity(),
								categoryList);

						// setting list adapter
						expListView.setAdapter(listAdapter);
					} else {
						Toast.makeText(getActivity(), " No Data From Server! ",
								Toast.LENGTH_SHORT).show();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] errorResponse, Throwable e) {
				// called when response HTTP status is "4XX" (eg.
				// 401, 403, 404)
				expListView.onRefreshComplete();
				progress.dismiss();
				System.out.println("CAT:::Fail");

			}

			@Override
			public void onRetry(int retryNo) {
				// called when request is retried
			}

		});

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	public void showSubCategory() {
		final Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.list_sub_cateory_show);
		dialog.setTitle("Select Sub-Category");

		data.clear();
		for (int i = 0; i < mSubCategoryList.size(); i++) {
			if (categoryList.get(position_new).getId()
					.contentEquals(mSubCategoryList.get(i).getParent_id())) {
				data.add(new NewSubCategory(mSubCategoryList.get(i).getId(),
						mSubCategoryList.get(i).getParent_id(),
						mSubCategoryList.get(i).getName(), mSubCategoryList
								.get(i).getSummary(), mSubCategoryList.get(i)
								.getParent_id()));

			}
		}

		ListView list = (ListView) dialog.findViewById(R.id.listViewSub);

		list.setAdapter(new SubCategoryShowAdapter(getActivity(), data));

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				dialog.dismiss();
				Values.cat = data.get(arg2).getParent_id();
				Values.sub_cat = data.get(arg2).getId();
				Fragment fragment = new ItemsFragment();
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment)
						.addToBackStack(null).commit();

			}
		});

		dialog.show();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_categories,
				container, false);

		System.out.println("CAT:::0");

		return rootView;
	}
}
