package com.mobioapp.klassify;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mobioapp.klassify.models.CategoryNew;
import com.mobioapp.klassify.models.MyPost;
import com.mobioapp.klassify.models.User;
import com.mobioapp.klassify.utils.Database;
import com.mobioapp.klassify.utils.DetectConnection;
import com.mobioapp.klassify.utils.SharedData;
import com.mobioapp.klassify.utils.URLs;
import com.mobioapp.klassify.utils.Values;
import com.squareup.picasso.Picasso;

public class InstantAdPostAtivity extends BaseActivity {

	SharedData sharedData = SharedData.getInstance();
	private ImageView mImageView1, mImageView2, mImageView3;
	private TextView r1, r2, r3;
	private EditText edPrice, edDes, edCompany, edTitle;
	private static final int CAMERA_REQUEST = 1888;
	private static final int RESULT_LOAD_IMAGE = 1889;
	private String mImagePath_1 = "", mImagePath_2 = "", mImagePath_3 = "";
	private int mcheck_image;
	int position_new;
	TextView mBtnPost;
	List<String> spinnerSubCategoryArray;
	ProgressDialog progress;
	String cat_name, sub_cat_name, cat_id_2;
	Database database;
	ProgressDialog mProgress;
	private Spinner spinner1, spinner2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.instant_ad_post);
		database = new Database(this);

		spinner1 = (Spinner) findViewById(R.id.spinnerCategoryChoose);
		spinner2 = (Spinner) findViewById(R.id.spinnerSubCategoryChoose);

		
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
		
		
		mImageView1 = (ImageView) findViewById(R.id.take_image_1);
		mImageView2 = (ImageView) findViewById(R.id.take_image_2);
		mImageView3 = (ImageView) findViewById(R.id.take_image_3);

		edPrice = (EditText) findViewById(R.id.etProductPrice);
		edDes = (EditText) findViewById(R.id.etProductDescription);
		edCompany = (EditText) findViewById(R.id.etProductCompany);
		edTitle = (EditText) findViewById(R.id.etProductTitle);

		mProgress = new ProgressDialog(this);

		r1 = (TextView) findViewById(R.id.remove_1);
		r2 = (TextView) findViewById(R.id.remove_2);
		r3 = (TextView) findViewById(R.id.remove_3);

		r1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				r1.setVisibility(View.GONE);
				mImagePath_1 = "";
				mImageView1.setImageResource(R.drawable.add_image);
			}
		});

		r2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				r2.setVisibility(View.GONE);
				mImagePath_2 = "";
				mImageView2.setImageResource(R.drawable.add_image);
			}
		});

		r3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				r3.setVisibility(View.GONE);
				mImagePath_2 = "";
				mImageView3.setImageResource(R.drawable.add_image);
			}
		});

		mBtnPost = (TextView) findViewById(R.id.buttonAdPost);

		mBtnPost.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {

				if (edPrice.getText().toString().isEmpty()) {
					Toast toast = Toast.makeText(getApplicationContext(),
							"Please fill the price field!", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();

				} else if (edDes.getText().toString().isEmpty()) {
					Toast toast = Toast.makeText(getApplicationContext(),
							"Please fill the description field!",
							Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				} else if (edTitle.getText().toString().isEmpty()) {
					Toast toast = Toast.makeText(getApplicationContext(),
							"Please fill the title field!", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				} else {
					if(DetectConnection.checkInternetConnection(InstantAdPostAtivity.this)){
						doRegister();
					}else{
						Toast.makeText(getApplicationContext(),
								" No Internet!", Toast.LENGTH_SHORT)
								.show();

					}
					
				
				}

			}
		});

		mImageView1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				mcheck_image = 1;
				showImageChoosePopUp();
			}
		});
		mImageView2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				mcheck_image = 2;
				showImageChoosePopUp();
			}
		});
		mImageView3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				mcheck_image = 3;
				showImageChoosePopUp();
			}
		});

		System.out.println("si" + sharedData.getCategoryList());

		List<String> spinnerCategoryArray = new ArrayList<String>();

		for (int i = 0; i < Values.catList.size(); i++) {
			spinnerCategoryArray.add(Values.catList.get(i).getName());

		}

		addItemsOnSpinner1();

	}
	
	// add items into spinner dynamically
	public void addItemsOnSpinner1() {

		List<String> list = new ArrayList<String>();
		for (int i = 0; i < Values.cats.size(); i++) {
			list.add(Values.cats.get(i).name);
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(dataAdapter);
	}
	
	public void addItemsOnSpinner2(int p) {
        CategoryNew c = Values.cats.get(p);
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < c.sub_cats.size(); i++) {
			list.add(c.sub_cats.get(i).name);
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(dataAdapter);
	}

	public void showImageChoosePopUp() {
		final Dialog dialog = new Dialog(InstantAdPostAtivity.this);
		dialog.setContentView(R.layout.image_take_popup);
		dialog.setTitle("Image Choose");

		RadioGroup group = (RadioGroup) dialog.findViewById(R.id.radioGroup1);
		RadioButton takeFromCamera = (RadioButton) dialog
				.findViewById(R.id.radio0);
		RadioButton takeFromGallery = (RadioButton) dialog
				.findViewById(R.id.radio1);

		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub

				switch (checkedId) {
				case R.id.radio0:
					Intent cameraIntent = new Intent(
							android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
					startActivityForResult(cameraIntent, CAMERA_REQUEST);
					dialog.dismiss();
					break;
				case R.id.radio1:
					Intent i = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

					startActivityForResult(i, RESULT_LOAD_IMAGE);
					dialog.dismiss();
					break;

				default:
					break;
				}

			}
		});

		dialog.show();
	}

	@SuppressWarnings("deprecation")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
			Bitmap photo = (Bitmap) data.getExtras().get("data");

			String[] projection = { MediaStore.Images.Media.DATA };
			Cursor cursor = managedQuery(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
					null, null, null);
			int column_index_data = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToLast();

			if (mcheck_image == 1) {
				mImagePath_1 = cursor.getString(column_index_data);
				mImageView1.setImageBitmap(photo);
				r1.setVisibility(View.VISIBLE);
				mImageView2.setVisibility(View.VISIBLE);
				System.out.println("Image Path 1 " + mImagePath_1);
			} else if (mcheck_image == 2) {
				mImagePath_2 = cursor.getString(column_index_data);
				mImageView2.setImageBitmap(photo);
				r2.setVisibility(View.VISIBLE);
				mImageView3.setVisibility(View.VISIBLE);
				System.out.println("Image Path 2 " + mImagePath_2);
			} else if (mcheck_image == 3) {
				mImagePath_3 = cursor.getString(column_index_data);
				mImageView3.setImageBitmap(photo);
				r3.setVisibility(View.VISIBLE);
				System.out.println("Image Path 3 " + mImagePath_3);
			} else {

			}

		} else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);

			if (mcheck_image == 1) {
				mImagePath_1 = picturePath;
				r1.setVisibility(View.VISIBLE);
				//mImageView2.setVisibility(View.VISIBLE);
				mImageView1.setImageBitmap(BitmapFactory.decodeFile(mImagePath_1));
				//Picasso.with(InstantAdPostAtivity.this).load(new File(mImagePath_1)).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).into(mImageView1);
				System.out.println("Image Path 1 " + mImagePath_1);
			} else if (mcheck_image == 2) {
				mImagePath_2 = picturePath;
				r2.setVisibility(View.VISIBLE);
				//mImageView3.setVisibility(View.VISIBLE);
				mImageView2.setImageBitmap(BitmapFactory.decodeFile(mImagePath_2));
				Picasso.with(InstantAdPostAtivity.this).load(new File(mImagePath_2)).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).into(mImageView2);
				System.out.println("Image Path 2 " + mImagePath_2);
			} else if (mcheck_image == 3) {
				mImagePath_3 = picturePath;
				r3.setVisibility(View.VISIBLE);
				//mImageView3.setImageBitmap(BitmapFactory.decodeFile(mImagePath_3));
				Picasso.with(InstantAdPostAtivity.this).load(new File(mImagePath_3)).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).into(mImageView3);
				System.out.println("Image Path 3 " + mImagePath_3);
			} else {

			}

		}
	}

	private void doRegister() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();

		try {
			if (!mImagePath_1.contentEquals("")) {
				params.put("ad_image_1", new File(mImagePath_1));
			}

			if (!mImagePath_2.contentEquals("")) {
				params.put("ad_image_2", new File(mImagePath_2));
			}

			if (!mImagePath_3.contentEquals("")) {
				params.put("ad_image_3", new File(mImagePath_3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String cat1 = Values.cats.get(spinner1.getSelectedItemPosition()).id+"";
		String cat2 = Values.cats.get(spinner1.getSelectedItemPosition()).sub_cats.get(spinner2.getSelectedItemPosition()).id+"";

		params.put("cate_1", cat1);
		params.put("cate_2", cat2);
		params.put("title", edTitle.getText().toString());
		params.put("details", edCompany.getText().toString());
		params.put("price", edPrice.getText().toString());
		params.put("p_id", User.Id+"");
		
		try {
			database.open();
			database.addMyPost(new MyPost(String.valueOf(System
					.currentTimeMillis()), mImagePath_1, mImagePath_2,
					mImagePath_3, edPrice.getText().toString(), edTitle
							.getText().toString(), edDes.getText().toString(),
					cat1, cat2,
					edCompany.getText().toString(), ""));
			database.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		client.post(InstantAdPostAtivity.this,
				URLs.AD_POST_URL,
				params, new AsyncHttpResponseHandler() {

					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						super.onFinish();
						mProgress.dismiss();
					}

					@Override
					public void onStart() {
						// called before request is started
						mProgress = new ProgressDialog(
								InstantAdPostAtivity.this);
						mProgress.setMessage("Posting...");
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

							System.out.println("Message  " + msg);

							if (msg.equals("success")) {

								Toast.makeText(getApplicationContext(),
										" Successfull!", Toast.LENGTH_SHORT)
										.show();

								finish();

							}

							else {

								Toast.makeText(getApplicationContext(),
										" Failure!", Toast.LENGTH_SHORT).show();

							}

						} catch (Exception e) {

							Toast.makeText(getApplicationContext(), "Error!",
									Toast.LENGTH_SHORT).show();

						}

					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] errorResponse, Throwable e) {
						// called when response HTTP status is "4XX" (eg. 401,
						// 403, 404)
						Toast.makeText(InstantAdPostAtivity.this, "Error!",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onRetry(int retryNo) {
						// called when request is retried
					}
				});
	}
	//
}
