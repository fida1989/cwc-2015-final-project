package com.mobioapp.klassify.fragments;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mobioapp.klassify.R;
import com.mobioapp.klassify.adapters.ItemAdapter;
import com.mobioapp.klassify.models.Item;
import com.mobioapp.klassify.utils.URLs;
import com.mobioapp.klassify.utils.Values;

public class SearchAdFragment extends Fragment {

	ArrayList<Item> items = new ArrayList<Item>();
	ListView lv;
	ProgressDialog progress;
	EditText searchEt;
	Button searchBtn;

	public SearchAdFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		
		searchBtn = (Button) getActivity().findViewById(R.id.search_button1);
		searchBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String q = searchEt.getText().toString()+"";
				if(q.length()>1){
					loadData(q);
				}else{
					Toast.makeText(getActivity(), " Enter search query! ",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		
		searchEt = (EditText) getActivity().findViewById(R.id.search_editText1);

		lv = (ListView) getActivity().findViewById(R.id.searchads_listView1);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				Values.cur_item = items.get(arg2);

				Fragment fragment = new ItemViewFragment();
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();

			}
		});

		

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

	private void loadData(String query) {
		items.clear();
		// System.out.println(URLs.HOT_ITEM_URL);

		AsyncHttpClient client = new AsyncHttpClient();
		client.get(URLs.SEARCH_URL + query, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {

				// called before request is started
				progress = new ProgressDialog(getActivity());
				progress.setMessage("Fetching Data...");
				progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progress.setIndeterminate(true);
				progress.show();

			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				// called when response HTTP status is "200 OK"

				progress.dismiss();

				// db.deleteContacts();

				try {
					// System.out.println("JSON:::" + new
					// String(response));
					// Toast.makeText(MainActivity.this, new
					// String(response), Toast.LENGTH_LONG).show();
					JSONObject jsonObj = new JSONObject(new String(response));
					JSONArray jsonArray = jsonObj.getJSONArray("details");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject c = jsonArray.getJSONObject(i);

						String id = c.getString("id");
						String title = c.getString("title");

						String image = c.getString("image_thumb_1");
						String price = c.getString("price");
						String phone = c.getString("poster_phone");
						String owner = c.getString("poster_name");
						String email = c.getString("poster_email");
						String location = c.getString("location") + "- "
								+ c.getString("city");
						String type = c.getString("cate_2_name");
						String description = c.getString("details");

						items.add(new Item(id, title, image, price, phone,
								owner, email, location, type, description));

					}

					if (items.size() > 0) {
						lv.setAdapter(new ItemAdapter(getActivity(), items));
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
		View rootView = inflater.inflate(R.layout.fragment_searchads,
				container, false);

		return rootView;
	}
}
