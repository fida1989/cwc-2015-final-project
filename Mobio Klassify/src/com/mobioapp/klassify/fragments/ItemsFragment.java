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
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mobioapp.klassify.R;
import com.mobioapp.klassify.adapters.ItemAdapter;
import com.mobioapp.klassify.models.Item;
import com.mobioapp.klassify.utils.Database;
import com.mobioapp.klassify.utils.DetectConnection;
import com.mobioapp.klassify.utils.URLs;
import com.mobioapp.klassify.utils.Values;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class ItemsFragment extends Fragment {
	ArrayList<Item> items = new ArrayList<Item>();
	PullToRefreshListView lv;
	ProgressDialog progress;
	private Spinner spinner1;
	Database database;
	int position_new;
	boolean check = false;
	String checkData;

	public ItemsFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		System.out.println("CAT:::2");
		lv = (PullToRefreshListView) getActivity().findViewById(
				R.id.item_listView1);

		lv.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// Your code to refresh the list contents

				check = true;

				if (checkData.contentEquals("Most Recent")) {
					if (DetectConnection.checkInternetConnection(getActivity())) {
						loadData("");
					} else {
						items.clear();

						try {

							database.open();
							items = database.getItemCategoryList("",
									Values.cat, Values.sub_cat);
							database.close();
						} catch (Exception e) {
							e.printStackTrace();
						}

						lv.onRefreshComplete();
						if (items.size() > 0) {
							lv.setAdapter(new ItemAdapter(getActivity(), items));
							Values.loaded = true;
						} else {
							Toast.makeText(getActivity(), " No Items! ",
									Toast.LENGTH_SHORT).show();
						}
					}
				} else if (checkData.contentEquals("Price (High > Low)")) {
					if (DetectConnection.checkInternetConnection(getActivity())) {
						loadData("/R/H");
					} else {
						items.clear();

						try {

							database.open();
							items = database.getItemCategoryList("/R/H",
									Values.cat, Values.sub_cat);
							database.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
						lv.onRefreshComplete();
						if (items.size() > 0) {
							lv.setAdapter(new ItemAdapter(getActivity(), items));
							Values.loaded = true;
						} else {
							Toast.makeText(getActivity(), " No Items! ",
									Toast.LENGTH_SHORT).show();
						}
					}

				} else if (checkData.contentEquals("Price (Low > High)")) {
					if (DetectConnection.checkInternetConnection(getActivity())) {
						loadData("/R/L");
					} else {
						items.clear();

						try {

							database.open();
							items = database.getItemCategoryList("/R/L",
									Values.cat, Values.sub_cat);
							database.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
						lv.onRefreshComplete();
						if (items.size() > 0) {
							lv.setAdapter(new ItemAdapter(getActivity(), items));
							Values.loaded = true;
						} else {
							Toast.makeText(getActivity(), " No Items! ",
									Toast.LENGTH_SHORT).show();
						}
					}

				} else if (checkData.contentEquals("Most Viewed")) {
					if (DetectConnection.checkInternetConnection(getActivity())) {
						loadData("/R/M");
					} else {
						items.clear();

						try {

							database.open();
							items = database.getItemCategoryList("/R/M",
									Values.cat, Values.sub_cat);
							database.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
						lv.onRefreshComplete();
						if (items.size() > 0) {
							lv.setAdapter(new ItemAdapter(getActivity(), items));
							Values.loaded = true;
						} else {
							Toast.makeText(getActivity(), " No Items! ",
									Toast.LENGTH_SHORT).show();
						}
					}
				}

			}
		});

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

		database = new Database(getActivity());

		spinner1 = (Spinner) getActivity().findViewById(R.id.spinner1);
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				checkData = spinner1.getItemAtPosition(arg2).toString();

				// TODO Auto-generated method stub
				switch (arg2) {
				case 0:
					if (DetectConnection.checkInternetConnection(getActivity())) {
						loadData("");
					} else {
						items.clear();

						try {

							database.open();
							items = database.getItemCategoryList("",
									Values.cat, Values.sub_cat);
							database.close();
						} catch (Exception e) {
							e.printStackTrace();
						}

						if (items.size() > 0) {
							lv.setAdapter(new ItemAdapter(getActivity(), items));
							Values.loaded = true;
						} else {
							Toast.makeText(getActivity(), " No Items! ",
									Toast.LENGTH_SHORT).show();
						}
					}

					break;
				case 1:

					if (DetectConnection.checkInternetConnection(getActivity())) {
						loadData("/R/H");
					} else {
						items.clear();

						try {

							database.open();
							items = database.getItemCategoryList("/R/H",
									Values.cat, Values.sub_cat);
							database.close();
						} catch (Exception e) {
							e.printStackTrace();
						}

						if (items.size() > 0) {
							lv.setAdapter(new ItemAdapter(getActivity(), items));
							Values.loaded = true;
						} else {
							Toast.makeText(getActivity(), " No Items! ",
									Toast.LENGTH_SHORT).show();
						}
					}

					break;

				case 2:

					if (DetectConnection.checkInternetConnection(getActivity())) {
						loadData("/R/L");
					} else {
						items.clear();

						try {

							database.open();
							items = database.getItemCategoryList("/R/L",
									Values.cat, Values.sub_cat);
							database.close();
						} catch (Exception e) {
							e.printStackTrace();
						}

						if (items.size() > 0) {
							lv.setAdapter(new ItemAdapter(getActivity(), items));
							Values.loaded = true;
						} else {
							Toast.makeText(getActivity(), " No Items! ",
									Toast.LENGTH_SHORT).show();
						}
					}

					break;
				case 3:

					if (DetectConnection.checkInternetConnection(getActivity())) {
						loadData("/R/M");
					} else {
						items.clear();

						try {

							database.open();
							items = database.getItemCategoryList("/R/M",
									Values.cat, Values.sub_cat);
							database.close();
						} catch (Exception e) {
							e.printStackTrace();
						}

						if (items.size() > 0) {
							lv.setAdapter(new ItemAdapter(getActivity(), items));
							Values.loaded = true;
						} else {
							Toast.makeText(getActivity(), " No Items! ",
									Toast.LENGTH_SHORT).show();
						}
					}
					break;

				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		// loadData("");

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

	private void loadData(final String url) {
		items.clear();
		System.out.println(URLs.ITEM_URL + Values.cat + "/" + Values.sub_cat
				+ url);

		AsyncHttpClient client = new AsyncHttpClient();
		client.get(URLs.ITEM_URL + Values.cat + "/" + Values.sub_cat + url,
				new AsyncHttpResponseHandler() {

					@Override
					public void onStart() {
						System.out.println("CAT:::4");
						// called before request is started
						if (!check) {
							progress = new ProgressDialog(getActivity());
							progress.setMessage("Fetching Data...");
							progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
							progress.setIndeterminate(true);
							progress.show();
						}

					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] response) {
						// called when response HTTP status is "200 OK"
						System.out.println("CAT:::5");
						if (!check) {
							progress.dismiss();
						} else {
							lv.onRefreshComplete();
						}

						// db.deleteContacts();

						try {
							// System.out.println("JSON:::" + new
							// String(response));
							// Toast.makeText(MainActivity.this, new
							// String(response), Toast.LENGTH_LONG).show();
							JSONObject jsonObj = new JSONObject(new String(
									response));
							JSONArray jsonArray = jsonObj
									.getJSONArray("details");

							try {
								database.open();
								database.deleteItem(url, Values.cat,
										Values.sub_cat);
								database.close();
							} catch (Exception e) {
								e.printStackTrace();
							}

							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject c = jsonArray.getJSONObject(i);

								String id = c.getString("id");
								String title = c.getString("title");

								String image = c.getString("image_thumb_1");
								String price = c.getString("price");
								String phone = c.getString("poster_phone");
								String owner = c.getString("poster_name");
								String email = c.getString("poster_email");
								String location = c.getString("location")
										+ "- " + c.getString("city");
								String type = c.getString("cate_2_name");
								String description = c.getString("details");

								items.add(new Item(id, title, image, price,
										phone, owner, email, location, type,
										description));

								try {
									database.open();
									database.addItem(new Item(id, title, image,
											price, phone, owner, email,
											location, type, description), url,
											Values.cat, Values.sub_cat);
									database.close();

								} catch (Exception e) {
									e.printStackTrace();
								}

							}

							if (items.size() > 0) {
								lv.setAdapter(new ItemAdapter(getActivity(),
										items));
								Values.loaded = true;
							} else {
								Toast.makeText(getActivity(), " No Items! ",
										Toast.LENGTH_SHORT).show();
							}

						} catch (Exception e) {
							e.printStackTrace();
							Toast.makeText(getActivity(), " No Items! ",
									Toast.LENGTH_SHORT).show();
						}

					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] errorResponse, Throwable e) {
						// called when response HTTP status is "4XX" (eg.
						// 401, 403, 404)
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_items, container,
				false);

		return rootView;
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.context_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.add_fav:
			String name = "";

			try {
				AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
						.getMenuInfo();
				int position = info.position;
				database.open();
				boolean check = database.isAlreadyAdded(items.get(position)
						.getId());

				if (check) {

					Toast toast = Toast.makeText(getActivity(),
							"Already added!", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();

				} else {
					database.addFavItem(items.get(position));
					Toast toast = Toast.makeText(getActivity(),
							"Added successfully!", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}

				database.close();
			} catch (Exception e) {
				// TODO: handle exception
			}

			return true;

		default:
			return super.onContextItemSelected(item);
		}
	}
}
