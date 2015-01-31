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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mobioapp.klassify.R;
import com.mobioapp.klassify.adapters.ItemAdapter;
import com.mobioapp.klassify.adapters.MyPostItemAdapter;
import com.mobioapp.klassify.models.Item;
import com.mobioapp.klassify.models.MyPost;
import com.mobioapp.klassify.utils.Database;
import com.mobioapp.klassify.utils.URLs;
import com.mobioapp.klassify.utils.Values;

public class MyAdsFragment extends Fragment {

	ArrayList<Item> items = new ArrayList<Item>();
	ListView lv;
	ProgressDialog progress;
	ArrayList<MyPost> post = new ArrayList<MyPost>();
	Database database;

	public MyAdsFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		System.out.println("CAT:::2");
		lv = (ListView) getActivity().findViewById(R.id.myads_listView1);
		
		database = new Database(getActivity());

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
			

			}
		});

		loadData();

		

	}

	private void loadData() {

		try {

			database.open();
			post = database.getMyPostCategoryList();
			database.close();
			if (post.size() > 0) {
				lv.setAdapter(new MyPostItemAdapter(getActivity(), post));
				Values.loaded = true;
			} else {
				Toast.makeText(getActivity(), " No Items! ", Toast.LENGTH_SHORT)
						.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_myads, container,
				false);

		return rootView;
	}
}
