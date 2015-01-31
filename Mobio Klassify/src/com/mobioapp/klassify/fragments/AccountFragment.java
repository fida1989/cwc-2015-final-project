package com.mobioapp.klassify.fragments;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mobioapp.klassify.R;
import com.mobioapp.klassify.RegisterActivity;
import com.mobioapp.klassify.models.User;
import com.mobioapp.klassify.utils.DetectConnection;
import com.mobioapp.klassify.utils.EmailChecker;
import com.mobioapp.klassify.utils.PasswordChecker;
import com.mobioapp.klassify.utils.URLs;

public class AccountFragment extends Fragment implements OnClickListener {

	Button register;
	Button login;
	Button logout;
	EditText email, pass;
	String user_email, user_pass;
	String result;
	String jsonresult;
	String jsonstring;
	String json;
	String send_jsonstring;
	ProgressDialog mProgress;
	LinearLayout lin;
	SharedPreferences pref;

	public AccountFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		pref = getActivity().getSharedPreferences("MyPref", 0);

		email = (EditText) getActivity().findViewById(R.id.editloginEmail);
		pass = (EditText) getActivity().findViewById(R.id.editloginPass);

		login = (Button) getActivity().findViewById(R.id.loginoption);
		login.setOnClickListener(this);

		register = (Button) getActivity().findViewById(R.id.registeroption);
		register.setOnClickListener(this);



	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_account, container,
				false);

		return rootView;
	}

	private void doLogin() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("email", user_email);
		params.put("password", user_pass);
		client.post(getActivity(), URLs.LOGIN_URL, params,
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
						mProgress = new ProgressDialog(getActivity());
						mProgress.setMessage("Logging  In...");
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

							System.out.println("Message: " + msg);

							if (msg.equals("success")) {

								JSONObject user = jsonResult
										.getJSONObject("user_details");
								System.out.println("Success JSON: "
										+ user.toString());
								// System.out.println("User ID: " +
								// user.getString("id"));
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

								Toast.makeText(getActivity(),
										"Login Successful!", Toast.LENGTH_SHORT)
										.show();

							

								Fragment fragment = new ProfileViewFragment();
								FragmentManager fragmentManager = getFragmentManager();
								fragmentManager.beginTransaction()
										.replace(R.id.content_frame, fragment)
										.commit();

							}

							else {

								Toast.makeText(
										getActivity(),
										"Login Failure! Check username/password!",
										Toast.LENGTH_LONG).show();

							}

						} catch (Exception e) {
							e.printStackTrace();
							Toast.makeText(getActivity(), "Error!",
									Toast.LENGTH_SHORT).show();
							System.out.println("JSON Parse Error: " + result);
						}

					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] errorResponse, Throwable e) {
						// called when response HTTP status is "4XX" (eg. 401,
						// 403, 404)
						Toast.makeText(getActivity(), "Error!",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onRetry(int retryNo) {
						// called when request is retried
					}
				});
	}

	public void getFormData() {
		user_email = email.getText().toString().trim();
		user_pass = pass.getText().toString().trim();

		if (user_email.length() > 0 && user_pass.length() > 0) {
			if (EmailChecker.isValidEmail(user_email)
					&& PasswordChecker.isValidPassword(user_pass)) {
			
				doLogin();
			} else {
				Toast.makeText(getActivity(), "Enter Valid Email!",
						Toast.LENGTH_SHORT).show();
			}

		} else {

			Toast.makeText(getActivity(), "Fill Required Info!",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.loginoption:
			if(DetectConnection.checkInternetConnection(getActivity())){
				getFormData();
			}else{
				Toast.makeText(getActivity(), "No Internet!",
						Toast.LENGTH_SHORT).show();
			}
	
			break;
		case R.id.registeroption:
			Intent i = new Intent(getActivity(), RegisterActivity.class);
			startActivity(i);
			break;

		default:
			break;
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (pref.getBoolean("log_track", false)) {
			Toast.makeText(getActivity(), "Already Logged In!",
					Toast.LENGTH_LONG).show();

			User.Id = pref.getString("user_id", "");
			Fragment fragment = new ProfileViewFragment();
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();

		}

		
	}

}
