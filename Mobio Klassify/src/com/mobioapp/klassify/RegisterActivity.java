package com.mobioapp.klassify;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mobioapp.klassify.models.City;
import com.mobioapp.klassify.models.User;
import com.mobioapp.klassify.utils.DetectConnection;
import com.mobioapp.klassify.utils.EmailChecker;
import com.mobioapp.klassify.utils.PasswordChecker;
import com.mobioapp.klassify.utils.URLs;
import com.mobioapp.klassify.utils.Values;

public class RegisterActivity extends BaseActivity implements OnClickListener{

	Button submit;
	EditText name, location, email, phone, password;
	String user_name, user_location, user_email, user_phone, user_password;
	String city, area;
	String result;
	String jsonresult;
	String jsonstring;
	String json;
	String send_jsonstring;
	ProgressDialog mProgress;
	String user;
	List<NameValuePair> nameValuePairs;
	boolean server;
	SharedPreferences pref;
	private Spinner spinner1, spinner2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		pref = getApplicationContext().getSharedPreferences("MyPref", 0);

		spinner1 = (Spinner) findViewById(R.id.register_spinner1);
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
		spinner2 = (Spinner) findViewById(R.id.register_spinner2);
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
		name = (EditText) findViewById(R.id.editgName);

		// location = (EditText) findViewById(R.id.editglocation);

		email = (EditText) findViewById(R.id.editgemail);
		phone = (EditText) findViewById(R.id.editgphone);
		password = (EditText) findViewById(R.id.editgpass);
		submit = (Button) findViewById(R.id.reg_submit);
		submit.setOnClickListener(this);
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
		//user_location = location.getText().toString();
		user_email = email.getText().toString();
		user_phone = phone.getText().toString();
		user_password = password.getText().toString();
		city = Values.cities.get(spinner1.getSelectedItemPosition()).id+"";
		area = Values.cities.get(spinner1.getSelectedItemPosition()).locations.get(spinner2.getSelectedItemPosition()).id+"";

		if (user_name.length() > 0 
				&& user_email.length() > 0 && user_password.length() > 0
				&& user_phone.length() > 0) {

			// String email1 = email.getText().toString();
			if (!EmailChecker.isValidEmail(user_email)) {
				email.setError("Invalid Email");
				return;
			}

			// String pass1 = password.getText().toString();
			if (!PasswordChecker.isValidPassword(user_password)) {
				password.setError("Password length must be 6 digits");
				return;
			}

			// new RegisterTask().execute();
			doRegister();
		} else {

			Toast.makeText(getApplicationContext(), "Fill Required Info!",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.reg_submit:
			if(DetectConnection.checkInternetConnection(RegisterActivity.this)){
				getFormData();
			}else{
				Toast.makeText(RegisterActivity.this, "No Internet!",
						Toast.LENGTH_SHORT).show();
			}
	
			break;

		default:
			break;
		}
	}

	private void doRegister() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();

		params.put("name", user_name);
		params.put("address", user_location);
		params.put("email", user_email);
		params.put("password", user_password);
		params.put("phone", user_phone);
		params.put("p_location", city);
		params.put("p_city", area);
		client.post(RegisterActivity.this, URLs.REGISTRATION_URL, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						super.onFinish();
						mProgress.dismiss();
					}

					@Override
					public void onStart() {
						// called before request is started
						mProgress = new ProgressDialog(RegisterActivity.this);
						mProgress.setMessage("Registering...");
						mProgress.show();
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] response) {
						// called when response HTTP status is "200 OK"
						try {
							JSONObject jsonResult = new JSONObject(new String(
									response));

							System.out.println("Return JSON Object: "
									+ jsonResult.toString());

							String msg = jsonResult.getString("message");

							if (msg.equals("success")) {

								JSONObject user = jsonResult
										.getJSONObject("user_details");
								System.out.println("Success JSON: "
										+ user.toString());

								User.Id = user.getString("id");
								User.Name = user.getString("name");
								User.Address = user.getString("address");
								User.Email = user.getString("email");
								User.Phone = user.getString("phone");

								Editor editor = pref.edit();
								editor.putString("user_id",
										user.getString("id"));
								// Storing
								editor.putBoolean("log_track", true); // string
								editor.commit(); // commit changes

								Toast.makeText(getApplicationContext(),
										"Registration Successfull!",
										Toast.LENGTH_SHORT).show();

								// Values.register = true;

								finish();

							}

							else {

								Toast.makeText(getApplicationContext(),
										"Registration Failure!",
										Toast.LENGTH_SHORT).show();

							}

						} catch (Exception e) {

							Toast.makeText(getApplicationContext(), "Error!",
									Toast.LENGTH_SHORT).show();
							System.out.println("JSON Parse Error: " + result);
						}

					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] errorResponse, Throwable e) {
						// called when response HTTP status is "4XX" (eg. 401,
						// 403, 404)
						Toast.makeText(RegisterActivity.this, "Error!",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onRetry(int retryNo) {
						// called when request is retried
					}
				});
	}

	/*@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		switch (arg1.getId()) {
		case R.id.register_spinner1:
			Toast.makeText(getApplicationContext(), arg2+"",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.register_spinner2:

			break;
		default:
			break;
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}*/

}
