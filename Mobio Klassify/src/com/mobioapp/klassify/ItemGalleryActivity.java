package com.mobioapp.klassify;

import java.io.File;
import java.util.HashMap;
import java.util.Random;

import org.apache.http.Header;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.BaseSliderView.OnSliderClickListener;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

public class ItemGalleryActivity extends BaseActivity {
	private SliderLayout mDemoSlider;
	HashMap<String, String> url_maps = new HashMap<String, String>();
ProgressDialog mProgress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_gallery);

		mDemoSlider = (SliderLayout) findViewById(R.id.slider);

		url_maps.put("Image 1", "http://i.imgur.com/GTDB6xN.png");
		url_maps.put("Image 2", "http://i.imgur.com/IRcscwB.png");
		url_maps.put("Image 3", "http://i.imgur.com/x3FdMpo.png");
		for (final String name : url_maps.keySet()) {
			final TextSliderView textSliderView = new TextSliderView(this);
			// initialize a SliderLayout
			textSliderView.description(name).image(url_maps.get(name))
					.setScaleType(BaseSliderView.ScaleType.CenterInside);
			// System.out.println("IMG:::" + url_maps.get(name));
			// add your extra information
			textSliderView.getBundle().putString("extra", name);
			textSliderView
					.setOnSliderClickListener(new OnSliderClickListener() {

						@Override
						public void onSliderClick(final BaseSliderView slider) {
							// TODO Auto-generated method stub

							// Creating the instance of PopupMenu
							PopupMenu popup = new PopupMenu(
									ItemGalleryActivity.this, textSliderView
											.getView());
							// Inflating the Popup using xml file
							popup.getMenuInflater().inflate(R.menu.pop_menu,
									popup.getMenu());

							// registering popup with OnMenuItemClickListener
							popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
								public boolean onMenuItemClick(MenuItem item) {
									// url_maps.get(name)+""

									if (item.getOrder() == 0) {
									/*	Toast.makeText(
												ItemGalleryActivity.this,
												slider.getUrl(),
												Toast.LENGTH_SHORT).show();*/
										downloadImage(slider.getUrl());
									} else {

									}

									return true;
								}
							});

							popup.show(); // showing popup menu
						}
					});
			mDemoSlider.addSlider(textSliderView);

		}
		mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
		mDemoSlider
				.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
		mDemoSlider.setCustomAnimation(new DescriptionAnimation());
		mDemoSlider.setDuration(5000);
		//

	}
	
	private void downloadImage(String url){
	    File sdCardDirectory = Environment.getExternalStorageDirectory();
	    final File image = new File(sdCardDirectory, "klassify_"+ new Random().nextInt(10000)+".png" );
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new FileAsyncHttpResponseHandler(image) {
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				mProgress.dismiss();
			}

			@Override
			public void onStart() {
				// called before request is started
				mProgress = new ProgressDialog(ItemGalleryActivity.this);
				mProgress.setMessage("Downloading...");
				mProgress.show();
			}
			
		    @Override
		    public void onSuccess(int statusCode, Header[] headers, File response) {
		        // Do something with the file `response`
		    	
		    	Toast.makeText(
						ItemGalleryActivity.this,
						"Image Saved At"+image.getAbsolutePath(),
						Toast.LENGTH_SHORT).show();
		    }

			@Override
			public void onFailure(int arg0, Header[] arg1, Throwable arg2,
					File arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(ItemGalleryActivity.this, "Error!",
						Toast.LENGTH_SHORT).show();
				
			}
		});
	}

}
