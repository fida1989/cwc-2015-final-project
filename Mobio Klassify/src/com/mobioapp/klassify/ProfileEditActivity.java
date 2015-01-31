package com.mobioapp.klassify;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mobioapp.klassify.models.City;
import com.mobioapp.klassify.models.User;
import com.mobioapp.klassify.utils.DetectConnection;
import com.mobioapp.klassify.utils.URLs;
import com.mobioapp.klassify.utils.Values;

public class ProfileEditActivity extends BaseActivity implements OnClickListener {

	EditText name, address, phone, pass,email;
	ProgressDialog mProgress;
	String user_name, user_address, user_phone, user_password;
	String result;
	Button update;
	Spinner spinner1, spinner2;
	String city, area;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_profileedit);
		
		spinner1 = (Spinner) findViewById(R.id.profile_spinner1);
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(), arg2+"",Toast.LENGTH_SHORT).show();
				addItemsOnSpinner2(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		spinner2 = (Spinner) findViewById(R.id.profile_spinner2);
		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		update = (Button)findViewById(R.id.profile_update);
		update.setOnClickListener(this);
		
		name = (EditText) findViewById(R.id.edit_name);
		//address = (EditText) findViewById(R.id.edit_address);
		phone = (EditText) findViewById(R.id.edit_phone);
		//pass = (EditText) findViewById(R.id.edit_pass);
		email = (EditText) findViewById(R.id.edit_email);
		
		name.setText(User.Name);
		//address.setText(User.Address);
		phone.setText(User.Phone);
		email.setText(User.Email);
		
		addItemsOnSpinner1();
		
	}
	
	// add items into spinner dynamically
		public void addItemsOnSpinner1() {

			List<String> list = new ArrayList<String>();
			for (int i = 0; i < Values.cities.size(); i++) {
				list.add(Values.cities.get(i).name);
			}

			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, list);
			dataAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner1.setAdapter(dataAdapter);
		}
		
		public void addItemsOnSpinner2(int p) {
	        City c = Values.cities.get(p);
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < c.locations.size(); i++) {
				list.add(c.locations.get(i).name);
			}

			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, list);
			dataAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner2.setAdapter(dataAdapter);
		}

	public void getFormData() {

		user_name = name.getText().toString();
		//user_address = address.getText().toString();
		// user_email = email.getText().toString();
		user_phone = phone.getText().toString();
		//user_password = pass.getText().toString();
		city = Values.cities.get(spinner1.getSelectedItemPosition()).id+"";
		area = Values.cities.get(spinner1.getSelectedItemPosition()).locations.get(spinner2.getSelectedItemPosition()).id+"";


		if (user_name.length() > 0 && user_phone.length() > 0) {

			// String pass1 = password.getText().toString();
			

			//new EditTask().execute();
			doEdit();
		} else {

			Toast.makeText(getApplicationContext(), "Fill Required Info!",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.profile_update:
			if(DetectConnection.checkInternetConnection(ProfileEditActivity.this)){
				getFormData();
			}else{
				Toast.makeText(ProfileEditActivity.this, "No Internet!",
						Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
	}
	
	private void doEdit(){
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();

		params.put("id", User.Id);
		params.put("name", user_name);
		params.put("phone", user_phone);
		params.put("p_location", city);
		params.put("p_city", area);
		client.post (ProfileEditActivity.this,URLs.EDIT_URL,params, new AsyncHttpResponseHandler() {

		    @Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				mProgress.dismiss();
			}

			@Override
		    public void onStart() {
		        // called before request is started
				mProgress = new ProgressDialog(ProfileEditActivity.this);
				mProgress.setMessage("Updating...");
				mProgress.show();
		    }

				
		    @Override
		    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
		        // called when response HTTP status is "200 OK"
		    	try {
					JSONObject jsonResult = new JSONObject(new String(response));

					System.out.println("Return JSON Object: "
							+ jsonResult.toString());

					String msg = jsonResult.getString("message");

					if (msg.equals("success")) {

						JSONObject user = jsonResult.getJSONObject("user_details");
						System.out.println("Success JSON: " + user.toString());

						User.Id = user.getString("id");
						User.Name = user.getString("name");
						User.Address = user.getString("address");
						User.Email = user.getString("email");
						User.Phone = user.getString("phone");

						Toast.makeText(getApplicationContext(),
								"Update Successfull!", Toast.LENGTH_SHORT).show();

						finish();

					}

					else {

						Toast.makeText(getApplicationContext(), "Update Failure!",
								Toast.LENGTH_SHORT).show();

					}

				} catch (Exception e) {

					Toast.makeText(getApplicationContext(), "Error!",
							Toast.LENGTH_SHORT).show();
					System.out.println("JSON Parse Error: " + result);
				}
		    	
		    }

		    @Override
		    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
		        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
		    	Toast.makeText(ProfileEditActivity.this, "Error!", Toast.LENGTH_SHORT).show();
		    }

		    @Override
		    public void onRetry(int retryNo) {
		        // called when request is retried
			}
		});
	}

}
