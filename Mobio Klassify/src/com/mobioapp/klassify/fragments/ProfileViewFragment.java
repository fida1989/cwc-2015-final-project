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
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mobioapp.klassify.ProfileEditActivity;
import com.mobioapp.klassify.R;
import com.mobioapp.klassify.models.User;
import com.mobioapp.klassify.utils.URLs;

public class ProfileViewFragment extends Fragment implements OnClickListener {

	private Button share, edit, logout;
	private TextView name, address, email, phone;
	SharedPreferences pref;
	ProgressDialog mProgress;

	public ProfileViewFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		pref = getActivity().getSharedPreferences("MyPref", 0);

		name = (TextView) getActivity().findViewById(R.id.name_textView1);
		address = (TextView) getActivity().findViewById(R.id.address_textView2);
		email = (TextView) getActivity().findViewById(R.id.email_textView3);
		phone = (TextView) getActivity().findViewById(R.id.phone_textView4);

		edit = (Button) getActivity().findViewById(R.id.profile_edit);
		edit.setOnClickListener(this);

		share = (Button) getActivity().findViewById(R.id.profile_share);
		share.setOnClickListener(this);

		logout = (Button) getActivity().findViewById(R.id.logout_button1);
		logout.setOnClickListener(this);

		
	}

	
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		loadData(User.Id);
	}

	private void loadData(String id) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(URLs.VIEW_URL + id, new AsyncHttpResponseHandler() {

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
				mProgress.setMessage("Fetching Profile...");
				mProgress.show();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				// called when response HTTP status is "200 OK"
				try {
					JSONObject jsonResult = new JSONObject(new String(response));
					// System.out.println(new String(response));

					String msg = jsonResult.getString("message");

					if (msg.equals("success")) {

						JSONObject user = jsonResult
								.getJSONObject("user_details");
						System.out.println("Success JSON: " + user.toString());
						User.Id = user.getString("id");
						User.Name = user.getString("name");
						User.Address = user.getString("address");
						User.Email = user.getString("email");
						User.Phone = user.getString("phone");

						setData();

					} else {

						Toast.makeText(getActivity(), "Error!",
								Toast.LENGTH_SHORT).show();

					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] errorResponse, Throwable e) {
				// called when response HTTP status is "4XX" (eg. 401, 403, 404)
				Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT)
						.show();
			}

			@Override
			public void onRetry(int retryNo) {
				// called when request is retried
			}
		});
	}

	private void setData() {
		try {
			name.setText(User.Name);
			address.setText(User.Address);
			email.setText(User.Email);
			phone.setText(User.Phone);
		} catch (Exception e) {

		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.profile_share:
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT,
					"Code Warriors' Challenge - 2015");
			sendIntent.setType("text/plain");
			startActivity(sendIntent);
			break;
		case R.id.profile_edit:
			startActivity(new Intent(getActivity(), ProfileEditActivity.class));
			break;
		case R.id.logout_button1:
			Editor editor = pref.edit();
			editor.putString("user_id", "");
			editor.putBoolean("log_track", false);
			editor.commit();
			//
			Fragment fragment = new AccountFragment();
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
			break;

		default:
			break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_profileview,
				container, false);

		return rootView;
	}
}
