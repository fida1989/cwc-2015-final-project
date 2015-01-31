package com.mobioapp.klassify.fragments;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.mobioapp.klassify.ItemGalleryActivity;
import com.mobioapp.klassify.R;

import com.mobioapp.klassify.utils.Values;
import com.squareup.picasso.Picasso;

public class ItemViewFragment extends Fragment {

	private TextView mtvShare, mtvCall, mtvSms, mtvSellerContact,
			mtvSellerName, mtvSellerLocation, mtvItemName, mtvItemPrice,
			mtvItemId, mtvItemCompany, mtvItemType, mtvItemDescription;
	ImageView mtvSellerLocationShowOnMap, item_img;
	// SharedData shareData = SharedData.getInstance();
	SocialAuthAdapter adapter;

	public ItemViewFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mtvShare = (TextView) getActivity().findViewById(R.id.tvShare);
		mtvCall = (TextView) getActivity().findViewById(R.id.tvCall);
		mtvSms = (TextView) getActivity().findViewById(R.id.tvSms);
		mtvSellerContact = (TextView) getActivity().findViewById(
				R.id.sellerContact);
		mtvSellerName = (TextView) getActivity().findViewById(R.id.buyerName);
		mtvSellerLocation = (TextView) getActivity().findViewById(
				R.id.sellerLocation);

		mtvSellerLocationShowOnMap = (ImageView) getActivity().findViewById(
				R.id.sellerLocationShowOnMap);

		mtvItemName = (TextView) getActivity().findViewById(R.id.itemName);
		mtvItemPrice = (TextView) getActivity().findViewById(R.id.itemPrice);
		mtvItemDescription = (TextView) getActivity().findViewById(
				R.id.textViewProductDescription);

		mtvItemId = (TextView) getActivity().findViewById(
				R.id.textViewProductCode);
		mtvItemCompany = (TextView) getActivity().findViewById(
				R.id.textViewProductCompany);
		mtvItemType = (TextView) getActivity().findViewById(
				R.id.textViewProductType);

		item_img = (ImageView) getActivity().findViewById(R.id.itemview_img);

		item_img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity(),
						ItemGalleryActivity.class));
			}
		});

		mtvShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Creating the instance of PopupMenu
				PopupMenu popup = new PopupMenu(getActivity(), mtvShare);
				// Inflating the Popup using xml file
				popup.getMenuInflater().inflate(R.menu.share_pop,
						popup.getMenu());

				// registering popup with OnMenuItemClickListener
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem item) {
						// url_maps.get(name)+""

						if (item.getTitle().toString().contentEquals("Facebook")) {
							adapter.authorize(getActivity(), Provider.FACEBOOK);
						} else if (item.getTitle().toString().contentEquals("Twitter")) {
							adapter.authorize(getActivity(), Provider.TWITTER);
						} else if (item.getTitle().toString().contentEquals("Instagram")) {
							adapter.authorize(getActivity(), Provider.INSTAGRAM);
						} else if (item.getTitle().toString().contentEquals("SMS")) {
							String uri = "smsto:1234567890";
							Intent in = new Intent(Intent.ACTION_VIEW);
							in.setData(Uri.parse("sms"));
							in.setType("vnd.android-dir/mms-sms");
							in.putExtra("sms_body", "http://ideas.mobioapp.com/");
							startActivity(in);
							
						} else {

						}

						

						return true;
					}
				});

				popup.show(); // showing popup menu

			}
		});
		mtvCall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String uri = "tel:" + mtvSellerContact.getText().toString();
				Intent in = new Intent(Intent.ACTION_DIAL);
				in.setData(Uri.parse(uri));
				startActivity(in);

			}
		});

		mtvSms.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String uri = mtvSellerContact.getText().toString();
				Intent in = new Intent(Intent.ACTION_VIEW);
				in.setData(Uri.parse("sms"));
				in.setType("vnd.android-dir/mms-sms");
				in.putExtra(Intent.EXTRA_TEXT, "");
				in.putExtra("address", uri);
				startActivity(in);

			}
		});

		mtvSellerLocationShowOnMap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openMap();
			}
		});

		adapter = new SocialAuthAdapter(new ResponseListener());
	/*	adapter.addProvider(Provider.FACEBOOK, R.drawable.facebook);
		adapter.addProvider(Provider.TWITTER, R.drawable.twitter);
		adapter.addProvider(Provider.INSTAGRAM, R.drawable.instagram);*/

		setAdDetailsData();

	}

	public void setAdDetailsData() {

		mtvItemId.setText(Values.cur_item.getId());
		mtvItemName.setText(Values.cur_item.getTitle());
		mtvItemPrice.setText(Values.cur_item.getPrice());
		mtvItemType.setText(Values.cur_item.getType());
		mtvItemCompany.setText("");
		mtvItemDescription.setText(Values.cur_item.getDescription());
		mtvSellerName.setText(Values.cur_item.getOwner());
		mtvSellerLocation.setText(Values.cur_item.getLocation());
		mtvSellerContact.setText(Values.cur_item.getPhone());
		Picasso.with(getActivity()).load(Values.cur_item.getImage())
				.placeholder(R.drawable.ic_launcher)
				.error(R.drawable.ic_launcher).into(item_img);
	}

	private void openMap() {

		double latitude = 23.7939927;
		double longitude = 90.4042719;

		String uriBegin = "geo:" + latitude + "," + longitude;
		String query = latitude + "," + longitude + "("
				+ Values.cur_item.getLocation() + ")";
		String encodedQuery = Uri.encode(query);
		String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
		Uri uri = Uri.parse(uriString);
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}

	/**
	 * Listens Response from Library
	 * 
	 */

	private final class ResponseListener implements DialogListener {
		@Override
		public void onComplete(Bundle values) {
			// Variable to receive message status
			Log.d("Share-Menu", "Authentication Successful");

			// Get name of provider after authentication
			final String providerName = values
					.getString(SocialAuthAdapter.PROVIDER);

			Toast.makeText(getActivity(), providerName + " connected",
					Toast.LENGTH_SHORT).show();

			adapter.updateStatus("http://ideas.mobioap124p.com/",
					new MessageListener(), false);

		}

		@Override
		public void onError(SocialAuthError error) {
			error.printStackTrace();
			Log.d("Share-Menu", error.getMessage());
		}

		@Override
		public void onCancel() {
			Log.d("Share-Menu", "Authentication Cancelled");
		}

		@Override
		public void onBack() {
			Log.d("Share-Menu", "Dialog Closed by pressing Back Key");

		}
	}

	// To get status of message after authentication
	private final class MessageListener implements SocialAuthListener<Integer> {
		@Override
		public void onExecute(String provider, Integer t) {
			Integer status = t;
			if (status.intValue() == 200 || status.intValue() == 201
					|| status.intValue() == 204)
				Toast.makeText(getActivity(), "Message posted on " + provider,
						Toast.LENGTH_LONG).show();
			else
				Toast.makeText(getActivity(),
						"Message not posted on" + provider, Toast.LENGTH_LONG)
						.show();
		}

		@Override
		public void onError(SocialAuthError e) {

		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_itemview, container,
				false);

		return rootView;
	}
}
