package com.mobioapp.klassify.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.BaseSliderView.OnSliderClickListener;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mobioapp.klassify.MainActivity;
import com.mobioapp.klassify.R;
import com.mobioapp.klassify.models.Item;
import com.mobioapp.klassify.utils.URLs;
import com.mobioapp.klassify.utils.Values;

public class HomeFragment extends Fragment {
	TextView name, desc;
	ImageView image;
	private SliderLayout mDemoSlider, mDemoSlider2;
	HashMap<String, String> url_maps = new HashMap<String, String>();
	ProgressDialog progress;
	ArrayList<Item> hot_items = new ArrayList<Item>();
	ArrayList<Item> editor_items = new ArrayList<Item>();
	int pos = 0;
	Button post_ad;
	SharedPreferences pref; 
	Button viewAd;
	
	
	public HomeFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		pref = getActivity()
				.getSharedPreferences("MyPref", 0); 
	
		
		viewAd = (Button)getActivity().findViewById(R.id.viewads_button1);
		viewAd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Fragment fragment = new CategoriesFragment();
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();
				
				MainActivity.mDrawerList.setItemChecked(4, true);
				MainActivity.ab.setTitle(getActivity().getResources().getStringArray(R.array.menu_array)[4]);
				MainActivity.mTitle = getActivity().getResources().getStringArray(R.array.menu_array)[4];
			}
		});

		mDemoSlider = (SliderLayout) getActivity().findViewById(R.id.slider);

		mDemoSlider2 = (SliderLayout) getActivity().findViewById(R.id.slider2);

		loadData();

	}

	private void loadData() {

		AsyncHttpClient client = new AsyncHttpClient();
		client.get(URLs.HOT_ITEM_URL, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				System.out.println("CAT:::4");
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
				System.out.println("CAT:::5");
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
						String thumb = c.getString("image_thumb_1");
						String price = c.getString("price");
						String email = c.getString("poster_email");
						String phone = c.getString("poster_phone");
						String owner = c.getString("poster_name");
						String location = c.getString("location");
						String type= c.getString("cate_2_name");
						String description = c.getString("details");
						
						
						
						hot_items.add(new Item(id, title, thumb, price, phone,
								owner, email,location,type,description));

					}

					if (hot_items.size() > 0) {
						loadSliders();
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
				progress.dismiss();

			}

			@Override
			public void onRetry(int retryNo) {
				// called when request is retried
			}

		});

	}

	private void loadSliders() {
		url_maps.clear();
		for (int i = 0; i < hot_items.size(); i++) {
			url_maps.put(hot_items.get(i).getTitle(), hot_items.get(i)
					.getImage());
		}
		// String name : url_maps.keySet()
		pos = 0;
		for (String name : url_maps.keySet()) {
			TextSliderView textSliderView = new TextSliderView(getActivity());
			// initialize a SliderLayout
			textSliderView.description(name).image(url_maps.get(name))
					.setScaleType(BaseSliderView.ScaleType.CenterCrop);
			System.out.println("IMG:::" + url_maps.get(name));
			// add your extra information
			textSliderView.getBundle().putInt("extra", pos);
			textSliderView
					.setOnSliderClickListener(new OnSliderClickListener() {

						@Override
						public void onSliderClick(BaseSliderView slider) {
							// TODO Auto-generated method stub
							Values.cur_item = hot_items.get(
									Integer.parseInt(slider.getBundle().get("extra")+""));
							Fragment fragment = new ItemViewFragment();
							FragmentManager fragmentManager = getFragmentManager();
							fragmentManager.beginTransaction()
									.replace(R.id.content_frame, fragment).commit();
						}
					});
			mDemoSlider.addSlider(textSliderView);
			pos++;

		}
		mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
		mDemoSlider
				.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
		mDemoSlider.setCustomAnimation(new DescriptionAnimation());
		mDemoSlider.setDuration(5000);
		//
		url_maps.clear();
		Collections.reverse(hot_items);
		for (int i = 0; i < hot_items.size(); i++) {
			url_maps.put(hot_items.get(i).getTitle(), hot_items.get(i)
					.getImage());
		}		
		pos = 0;
		for (String name : url_maps.keySet()) {
			TextSliderView textSliderView = new TextSliderView(getActivity());
			// initialize a SliderLayout
			textSliderView.image(url_maps.get(name)).setScaleType(
					BaseSliderView.ScaleType.CenterCrop);
			System.out.println("IMG:::" + url_maps.get(name));
			// add your extra information
			textSliderView.getBundle().putInt("extra", pos);
			textSliderView
					.setOnSliderClickListener(new OnSliderClickListener() {

						@Override
						public void onSliderClick(BaseSliderView slider) {
							// TODO Auto-generated method stub
							Values.cur_item = hot_items.get(
									Integer.parseInt(slider.getBundle().get("extra")+""));
							Fragment fragment = new ItemViewFragment();
							FragmentManager fragmentManager = getFragmentManager();
							fragmentManager.beginTransaction()
									.replace(R.id.content_frame, fragment).commit();
						}
					});
			mDemoSlider2.addSlider(textSliderView);
			pos++;
		}
		mDemoSlider2.setPresetTransformer(SliderLayout.Transformer.Default);
		mDemoSlider2
				.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
		mDemoSlider2.setCustomAnimation(new DescriptionAnimation());
		mDemoSlider2.setDuration(5000);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);

		return rootView;
	}
}
