package com.mobioapp.klassify.test;

import com.mobioapp.klassify.MainActivity;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;

public class KlassifyTestActivity extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity mActivity;
	private Button mView, mView2;
	private String resourceString, resourceString2;

	public KlassifyTestActivity() {
		super(MainActivity.class);
		// ("com.example.hello", MainActivity.class);
		// TODO Auto-generated constructor stub
	}

	protected void setUp() throws Exception {
		super.setUp();
		mActivity = this.getActivity();
		mView = (Button) mActivity
				.findViewById(com.mobioapp.klassify.R.id.search_button1);
		resourceString = "Search Ads";
		mView2 = (Button) mActivity
				.findViewById(com.mobioapp.klassify.R.id.viewads_button1);
		resourceString2 = "Hello";
	}

	public void testPreconditions() {
		assertNotNull(mView);
		assertNotNull(mView2);
	}

	public void testText() {
		assertEquals(resourceString, (String) mView.getText());
		assertEquals(resourceString2, (String) mView2.getText());

	}
}
